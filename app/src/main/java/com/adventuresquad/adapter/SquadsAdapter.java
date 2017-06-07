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
import com.adventuresquad.model.Squad;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView adapter for the squads list
 * Created by Harrison on 7/06/2017.
 */
public class SquadsAdapter extends RecyclerView.Adapter<SquadsAdapter.SquadViewHolder> {

    private List<Squad> mSquadList;
    final private GlideRequests mGlideRequests;
    private GlideRequest<Drawable> fullRequest;

    public class SquadViewHolder extends RecyclerView.ViewHolder {
        TextView mSquadName, mSquadDetails;
        ImageView mSquadImage;

        public SquadViewHolder(View view) {
            super(view);
            //Set up the view classes with view.findViewById here
            mSquadName = (TextView) view.findViewById(R.id.squad_name);
            mSquadDetails = (TextView) view.findViewById(R.id.squad_details);
            mSquadImage = (ImageView) view.findViewById(R.id.squad_image);
        }
    }

    /**
     * CONSTRUCTOR
     * @param activityContext The context of the activity that holds the recyclerview
     */
    public SquadsAdapter(Context activityContext) {
        mSquadList = new ArrayList<>();

//        addSampleSquad();
//        addSampleSquad();

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

    /**
     * SAMPLE SQUAD
     */
    private void addSampleSquad() {
        Squad sample = new Squad();
        sample.setSquadName("Sample squad");
        sample.setSquadId("asdfasdfasdf");
        mSquadList.add(sample);
    }

    /**
     * Adds an item dynamically to the recycler view
     * @param squad The squad to add to the list
     */
    public void addItem(Squad squad) {
        mSquadList.add(squad);
        int newIndex = mSquadList.indexOf(squad);
        notifyItemInserted(newIndex);
    }

    public void clearData() {
        int listSize = mSquadList.size();
        mSquadList.clear(); //clear list
        notifyItemRangeChanged(0, listSize); //update view
    }

    @Override
    public SquadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_squad_item, parent, false);

        return new SquadViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SquadViewHolder holder, int position) {
        //Get current squad item
        Squad squad = getListItem(position);

        //Load image
        //TODO - add proper images to Squads
        /*fullRequest
                .load(squad.getSquadImageUrl())
                .into(holder.mImage);*/
            //Hide loading icon on complete?

        //Populate view with text
        holder.mSquadName.setText(squad.getSquadName());
        if (squad.getSquadUsers() != null) {
            holder.mSquadDetails.setText(squad.getSquadUsers().size() + " members");
        }
        if (squad.getSquadPlans() != null) {
            holder.mSquadDetails.setText(", " + squad.getSquadPlans().size() + " plans");
        }
    }

    @Override
    public int getItemCount() { return mSquadList.size(); }

    /**
     * Returns an item at a specific position in the recycler view list
     * @param position The position
     * @return The item at the position
     */
    private Squad getListItem(int position) {
        return mSquadList.get(position);
    }

}
