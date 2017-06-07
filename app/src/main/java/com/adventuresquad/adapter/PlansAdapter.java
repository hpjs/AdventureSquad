package com.adventuresquad.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adventuresquad.R;
import com.adventuresquad.api.GlideApp;
import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.model.Plan;
import com.adventuresquad.presenter.MyTripsPresenter;

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

    private MyTripsPresenter mPresenter;
    private Context mContext;
    private List<Plan> mPlanList;

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
    public PlansAdapter(Context context, MyTripsPresenter presenter) {
        mContext = context;
        mPlanList = new ArrayList<>();
//        mPlanList.add(newTestPlan());
//        mPlanList.add(newTestPlan());
//        notifyDataSetChanged();
        mPresenter = presenter;
    }

    public Plan newTestPlan() {
        //Test example with some test values from the database
        Plan p = new Plan();
        p.setPlanTitle("Test plan");
        p.setAdventureId("-KkkNhRwWpeZPnVhmR2f");
        p.setSquadId("-KlldxAhPIx9Vl6GluRh");
        p.setBookingDate(0);
        return p;
    }


    @Override
    public PlansViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_my_trips_item, parent, false);

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

    @Override
    public void onBindViewHolder(final PlansViewHolder holder, int position) {
        //Get correct adventure item
        Plan plan = getListItem(position);

        //holder.mImage.setImageResource(R.drawable.adventure_placeholder_small);
        //Load image
        final Context imageContext = holder.mImage.getContext();

        mPresenter.retrieveAdventureImageUri(plan.getAdventureId(), new RetrieveDataRequest<Uri>() {
            @Override
            public void onRetrieveData(Uri uri) {
                GlideApp
                        .with(mContext)
                        .load(uri)
                        .placeholder(R.color.colorPrimary)
                        .error(R.drawable.ic_broken_image_black_24dp)
                        .into(holder.mImage);
                //Hide loading icon?
            }

            @Override
            public void onRetrieveDataFail(Exception e) {
                //Couldn't retrieve URL
            }
        });

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
