package com.adventuresquad.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adventuresquad.R;
import com.adventuresquad.api.GlideApp;
import com.adventuresquad.api.GlideRequest;
import com.adventuresquad.api.GlideRequests;
import com.adventuresquad.model.Plan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Adapter class for a list of plan objects
 * Created by Harrison on 4/06/2017.
 */
public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.PlansViewHolder>{

    private List<Plan> mPlanList;
    final private GlideRequests mGlideRequests;
    private GlideRequest<Drawable> fullRequest;

    public class PlansViewHolder extends RecyclerView.ViewHolder {
        //View objects here
        public TextView mTitle, mDate;
        public ImageView mImage;

        public PlansViewHolder(View view) {
            super(view);
            //Set up the view classes with view.findViewById here
            mTitle = (TextView) view.findViewById(R.id.item_trip_title);
            mDate = (TextView) view.findViewById(R.id.item_trip_date);
            mImage = (ImageView) view.findViewById(R.id.item_trip_image);
        }
    }

    /**
     * Constructor
     */
    public PlansAdapter(Context activityContext) {
        mPlanList = new ArrayList<>();

        //Extra glide stuff
        mGlideRequests = GlideApp.with(activityContext); //Once set, object ref can't be changed
        //Request 'blueprint' for a full image
        //onBindViewHolder will use this to make it's image load request
        fullRequest = mGlideRequests
                .asDrawable()
                .centerCrop()
                .placeholder(R.color.colorPrimary)
                .error(R.drawable.ic_broken_image_black_24dp);
    }

    @Override
    public PlansViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trip, parent, false);

        return new PlansViewHolder(itemView);
    }

    /**
     * Sets the entire plan list and notifies a data set change
     * @param newList
     */
    public void setPlanList(List<Plan> newList) {
        mPlanList = newList;
    }

    /**
     * Adds an item dynamically to the recycler view
     * @param plan The plan to add to the list
     */
    public void addItem(Plan plan) {
        mPlanList.add(plan);
        //NOTE - add could potentially cause synchronous problems
        // if also updated at same time from somewhere else.
        //Checking the index here will increase overhead but should help avoid a threading issue.
        int newIndex = mPlanList.indexOf(plan);
        notifyItemInserted(newIndex);
    }

    public void clearData() {
        int listSize = mPlanList.size();
        mPlanList.clear(); //clear list
        notifyItemRangeChanged(0, listSize); //update view
    }

    @Override
    public void onBindViewHolder(final PlansViewHolder holder, int position) {
        //Get correct adventure item
        Plan plan = getListItem(position);

        //Load image
        fullRequest
            .load(plan.getPlanImageUrl())
            .into(holder.mImage);
            //Hide loading icon on complete?

        //Populate view with text
        holder.mTitle.setText(plan.getPlanTitle());
        Date date = new Date(plan.getBookingDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEEEEEE, d MMMMMMMMMM yyyy", Locale.ENGLISH);
        holder.mDate.setText(dateFormat.format(date));
    }

    @Override
    public int getItemCount() {
        return mPlanList.size();
    }

    /**
     * Returns an item at a specific position in the recycler view list
     * @param position
     * @return
     */
    public Plan getListItem(int position) {
        return mPlanList.get(position);
    }

}
