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
import com.adventuresquad.presenter.MainPresenter;

import java.util.List;

/**
 * Adapter class for the main Adventure feed page recycler view
 * Created by Harrison on 13/05/2017.
 */
public class AdventureFeedAdapter extends RecyclerView.Adapter<AdventureFeedAdapter.AdventureViewHolder> {

    private MainPresenter mPresenter;
    private Context mContext;
    private List<Adventure> mAdventureList;

    //Currently holding a reference back to the Presenter class in order to request data etc

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
    }

    /**
     * Sets up the presenter dependency
     */
    public void setPresenter(MainPresenter presenter) {
        this.mPresenter = presenter;
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

    /**
     * Called once the view holder has been initialised and 'bound' properly,
     * to start populating with details of a particular list item at
     *
     * @param holder   The view holder object to use
     * @param position The position in the list of the item
     */
    @Override
    public void onBindViewHolder(AdventureFeedAdapter.AdventureViewHolder holder, int position) {
        //TODO
        //Get item of this position

        //Set view with text & details from the item

        //If this class does not contain the actual data, then it needs to know where to get it from
        //Therefore, make a reference to the data class (either Api class or Presenter)
        //in order to get the actual data when binding

        //Note: Adapter should (ideally) only talk to the activity
        onPopulateViewHolder(holder, mAdventureList.get(position));

    }

    public void onPopulateViewHolder(AdventureViewHolder holder, Adventure adventure) {
        holder.mImage.setImageResource(R.drawable.adventure_placeholder);
        holder.mTitle.setText(adventure.getAdventureTitle());
        //TODO - Change these values to actually mean something (e.g. actual 'matchiness')
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
        return mPresenter.getAdventureList().size();
    }
}
