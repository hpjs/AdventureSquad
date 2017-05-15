package com.adventuresquad.presenter;

import android.support.v7.widget.RecyclerView;

import com.adventuresquad.activity.MainActivity;
import com.adventuresquad.adapter.AdventureFeedAdapter;
import com.adventuresquad.model.Adventure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harrison on 11/05/2017.
 */
public class MainPresenter {

    private MainActivity mActivity;
    //Example list for the time being
    private List<Adventure> mAdventureList = new ArrayList<>();
    private AdventureFeedAdapter mAdventureAdapter;

    public MainPresenter(MainActivity activity) {

        mActivity = activity;

        mAdventureAdapter = new AdventureFeedAdapter(mActivity, this);
    }

    /**
     * Sets the local adapter to the recyler view in the activity class
     *
     * @param view
     */
    public void setAdapter(RecyclerView view) {
        view.setAdapter(mAdventureAdapter);
    }

    /**
     * Returns the number of items in the list of items (mainly used by
     *
     * @return
     */
    public List<Adventure> getAdventureList() {
        return mAdventureList;
    }

    //Gets a list of adventures from the database, passes to the adapter (?), and updates the UI
    public void getData() {
        //TEMPORARY CODE to get local data
        insertLocalSampleData();
        mAdventureAdapter.notifyDataSetChanged();

        //TODO - potentially put this as an async task (if not already)

        /*
        new AdventureApi().testReadData(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Stuff to do when data is recieved
                mAdventureAdapter.setData();

                //Update adapter data
                mAdventureAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Show error message on UI 'failed to read data' or something
            }
        });*/

    }

    /**
     * Inserts sample data into local list to test main feed
     */
    public void insertLocalSampleData() {
        Adventure adventure = new Adventure("Title 1", 111, 222);
        mAdventureList.add(adventure);

        adventure = new Adventure("Title 1", 111, 111);
        mAdventureList.add(adventure);

        adventure = new Adventure("Title 2", 222, 222);
        mAdventureList.add(adventure);

        adventure = new Adventure("Title 3", 333, 333);
        mAdventureList.add(adventure);

        adventure = new Adventure("Title 4", 444, 444);
        mAdventureList.add(adventure);

        adventure = new Adventure("Title 5", 555, 555);
        mAdventureList.add(adventure);

    }

    /**
     * Gets a particular adventure at a certain position
     *
     * @param position
     * @return
     */
    public Adventure getAdventure(int position) {
        return mAdventureList.get(position);
    }
}
