package com.adventuresquad;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Adapter class for the main Adventure feed page recycler view
 * Created by Harrison on 13/05/2017.
 */
public class AdventureFeedAdapter extends RecyclerView.Adapter<AdventureFeedAdapter.AdventureViewHolder> {


    /**
     * View holder class for
     */
    public class AdventureViewHolder extends RecyclerView.ViewHolder {
        //View classes here

        public AdventureViewHolder(View view) {
            super(view);
            //Set up the view classes with view.findViewById here
        }
    }

    /**
     * Inflates a single Adventure card item
     *
     * @param parent
     * @param viewType
     * @return
     */
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
    public void onBindViewHolder(AdventureFeedAdapter.AdventureViewHolder holder, int position) {

        //Get item of this position

        //Set view with text & details from the item


    }

    /**
     * Gets the count of everything in the list.
     */
    @Override
    public int getItemCount() {
        //TODO - implement this by reading info from Firebase
        return 0;
    }
}
