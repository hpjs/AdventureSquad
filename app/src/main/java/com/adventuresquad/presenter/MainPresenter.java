package com.adventuresquad.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.adventuresquad.R;
import com.adventuresquad.activity.MainActivity;
import com.adventuresquad.adapter.AdventureFeedAdapter;
import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.model.Adventure;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Presenter class to read data from API and present data to main activity
 * Created by Harrison on 11/05/2017.
 */
public class MainPresenter {

    private static final String DEBUG_MAIN_PRESENTER = "debug_main_presenter";
    private MainActivity mActivity;
    //Example list for the time being
    private List<Adventure> mAdventureList = new ArrayList<>();
    private AdventureFeedAdapter mAdventureAdapter;
    private AdventureApi mApi;

    public MainPresenter(MainActivity activity, AdventureFeedAdapter adventureAdapter, AdventureApi api) {
        //Dependency injections
        mActivity = activity;
        mAdventureAdapter = adventureAdapter;
        mApi = api;
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
        mApi.testReadData(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Stuff to do when data is recieved
                mActivity.showToastMessage(dataSnapshot.toString());

                //Attempts to retrieve the data as a list of Adventures
                try {
                    mAdventureList = (List<Adventure>) dataSnapshot.getValue();
                } catch (Exception e) {
                    Log.d(DEBUG_MAIN_PRESENTER, "Failed to cast to list of exceptions");
                }
                //Update adapter data
                mAdventureAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Show error message on UI 'failed to read data' or something
            }
        });
    }

    /**
     * Inserts sample data into local list to test main feed
     */
    public void setLocalSampleData() {
        Adventure adventure = new Adventure("Title 1", 111, 222);
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
     * Inserts sample data into Firebase
     */
    public void setSampleData() {

        List<Adventure> list = new ArrayList<>();

        Adventure adventure = new Adventure("Firebase Entry 1", 111, 222);
        //mAdventureList.add(adventure);
        list.add(adventure);

        adventure = new Adventure("Firebase Entry 2", 222, 222);
        list.add(adventure);

        adventure = new Adventure("Firebase Entry 3", 333, 333);
        list.add(adventure);

        adventure = new Adventure("Firebase Entry 4", 444, 444);
        list.add(adventure);

        adventure = new Adventure("Firebase Entry 5", 555, 555);
        list.add(adventure);

        mApi.putAdventures(list);
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

    /**
     * Gets a list of adventures from the database, formats for recycler view etc
     * Stores in the local list of adventures
     *
     * @return
     */
    public void getAdventures() {
        mApi.getAdventures(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Attempts to get an adventure and put it in the list
                mAdventureList.add(dataSnapshot.getValue(Adventure.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mActivity.showToastMessage(R.string.read_database_error);
            }
        });
    }
}
