package com.adventuresquad.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adventuresquad.R;
import com.adventuresquad.api.GlideApp;
import com.adventuresquad.api.GlideRequest;
import com.adventuresquad.api.GlideRequests;
import com.adventuresquad.model.Adventure;
import com.adventuresquad.presenter.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for the main Adventure feed page recycler view
 * Created by Harrison on 13/05/2017.
 */
public class AdventureFeedAdapter extends RecyclerView.Adapter<AdventureFeedAdapter.AdventureViewHolder> {

    private MainPresenter mPresenter;
    private Context mActivityContext;
    private List<Adventure> mAdventureList;
    final private GlideRequests mGlideRequests;
    private GlideRequest<Drawable> fullRequest;

    public static String DEBUG_ADVENTURE_LIST = "adventure_list";

    /**
     * View holder class for recycler view
     */
    public class AdventureViewHolder extends RecyclerView.ViewHolder {

        //View objects here
        public TextView mTitle, mDistance;
        public ProgressBar mMatch;
        public ImageView mImage;
        public AdventureViewHolder(View view) {
            super(view);
            //Set up the view classes with view.findViewById here
            mTitle = (TextView) view.findViewById(R.id.item_adventure_title);
            mDistance = (TextView) view.findViewById(R.id.item_adventure_distance);
            mMatch = (ProgressBar) view.findViewById(R.id.item_adventure_match);
            mImage = (ImageView) view.findViewById(R.id.item_adventure_image);
        }

    }
    /**
     * Constructor
     */
    public AdventureFeedAdapter(Context activityContext, MainPresenter presenter) {
        mActivityContext = activityContext;
        mAdventureList = new ArrayList<>();
        //TODO - come back and make this less nasty later (should go through Activity class ideally)
        mPresenter = presenter;

        //Extra glide stuff
        mGlideRequests = GlideApp.with(activityContext); //Once set, object ref can't be changed
        //Request 'blueprint' for a full image
        //onBindViewHolder will use this to make it's image load request
        fullRequest = mGlideRequests
                .asDrawable()
                .centerCrop()
                .placeholder(R.color.colorPrimary);
    }

    /**
     * Inflates a single Adventure card item
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public AdventureFeedAdapter.AdventureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item_adventure, parent, false);

        return new AdventureViewHolder(itemView);
    }


    public void setAdventureList(List<Adventure> adventureList) {
        this.mAdventureList = adventureList;
    }

    /**
     * Adds an item dynamically to the recycler view list (instead of the whole list at once)
     * @param adventure
     */
    public void addItem(Adventure adventure) {
        //This code was working over in PlansAdapter, so I used it here as well
        mAdventureList.add(adventure);
        int newIndex = mAdventureList.indexOf(adventure);
        notifyItemInserted(newIndex);
    }

    /**
     * Handles wind-down of image request as well as
     * @param holder
     */
    @Override
    public void onViewRecycled(AdventureViewHolder holder) {
        //Clears the download request for this particular list item
        //Note - does this also clear the image??
        Log.d(DEBUG_ADVENTURE_LIST, "Recycling view.");
        mGlideRequests.clear(holder.mImage);
    }

    /**
     * Called once the view holder has been initialised and 'bound' properly,
     * to start populating a card with details of a particular adventure
     * Needs to be as fast as possible
     * @param holder   The view holder object to use
     * @param position The position in the list of the item
     */
    @Override
    public void onBindViewHolder(final AdventureFeedAdapter.AdventureViewHolder holder, int position) {
        //Get correct adventure item
        final Adventure adventure = getListItem(position);

        //Note that it's using the activity context, as defined in constructor
        fullRequest
                .load(adventure.getAdventureImageUri())
                .into(holder.mImage);


//        GlideApp
//                .with(mActivityContext)
//                .load(adventure.getAdventureImageUri())
//                .placeholder(R.color.colorPrimary)
//                .error(R.drawable.ic_broken_image_black_24dp)
//                .into(holder.mImage);

        //Populate view with text
        holder.mTitle.setText(adventure.getAdventureTitle());
        //TODO - Set values correctly (e.g. actual 'match' amount) - probably do this in presenter
        // and put into adventure object?
        holder.mMatch.setMax(10);
        holder.mMatch.setProgress(4);
        //TODO - Calculate the distance itself
        holder.mDistance.setText(R.string.placeholder_distance);
    }

    /**
     * Gets the count of how many items are in the recyclerview list
     */
    @Override
    public int getItemCount() {
        return mAdventureList.size();
    }

    /**
     * Returns an item at a specific position in the recycler view list
     * @param position
     * @return
     */
    public Adventure getListItem(int position) {
        return mAdventureList.get(position);
    }

}
