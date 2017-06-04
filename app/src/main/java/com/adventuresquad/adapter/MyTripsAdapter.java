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
import com.adventuresquad.interfaces.RetrieveImageUriRequest;
import com.adventuresquad.model.Plan;
import com.adventuresquad.presenter.MyTripsPresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Harrison on 4/06/2017.
 */
public class MyTripsAdapter extends RecyclerView.Adapter<MyTripsAdapter.TripViewHolder>{

    private MyTripsPresenter mPresenter;
    private Context mContext;
    private List<Plan> mPlanList;

    public class TripViewHolder extends RecyclerView.ViewHolder {
        //View objects here
        public TextView mTitle, mDate;
        public ImageView mImage;

        public TripViewHolder(View view) {
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
    public MyTripsAdapter(Context context, MyTripsPresenter presenter) {
        mContext = context;
        mPlanList = new ArrayList<>();
        mPresenter = presenter;
    }


    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_my_trips_item, parent, false);

        return new TripViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TripViewHolder holder, int position) {
        //Get correct adventure item
        Plan plan = getListItem(position);

        //holder.mImage.setImageResource(R.drawable.adventure_placeholder_small);
        //Load image
        final Context imageContext = holder.mImage.getContext();

        mPresenter.retrieveAdventureImageUri(plan.getAdventureId(), new RetrieveImageUriRequest() {
            @Override
            public void onRetrieveImageUri(Uri uri) {
                GlideApp
                        .with(mContext)
                        .load(uri)
                        .placeholder(R.color.colorPrimary)
                        .error(R.drawable.ic_broken_image_black_24dp)
                        .fitCenter()
                        .into(holder.mImage);
                //Hide loading icon?
            }

            @Override
            public void onRetrieveImageUriFail(Exception e) {

            }
        });

        //Populate view with text
        holder.mTitle.setText(plan.getPlanTitle());
        Date date = new Date(plan.getBookingDate());
        holder.mDate.setText(date.toString());
    }

    @Override
    public int getItemCount() {
        return 0;
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
