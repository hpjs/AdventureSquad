package com.adventuresquad.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adventuresquad.R;
import com.adventuresquad.model.Adventure;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for the main Adventure feed page recycler view
 * Created by Harrison on 13/05/2017.
 */
public class AdventureFeedAdapter extends RecyclerView.Adapter<AdventureFeedAdapter.AdventureViewHolder> {

    private Context mContext;
    private List<Adventure> mAdventureList;

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
    public AdventureFeedAdapter(Context context) {
        mContext = context;
        mAdventureList = new ArrayList<>();
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
     * Called once the view holder has been initialised and 'bound' properly,
     * to start populating a card with details of a particular adventure
     * @param holder   The view holder object to use
     * @param position The position in the list of the item
     */
    @Override
    public void onBindViewHolder(AdventureFeedAdapter.AdventureViewHolder holder, int position) {
        //Get correct adventure item
        Adventure adventure = getListItem(position);

        //Populate view with text
        holder.mImage.setImageResource(R.drawable.adventure_placeholder);
        holder.mTitle.setText(adventure.getAdventureTitle());
        //TODO - Set values correctly (e.g. actual 'match' amount) - probably do this in presenter
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
