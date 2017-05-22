package com.adventuresquad.presenter;

import android.util.Log;

import com.adventuresquad.activity.MainActivity;
import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.model.Adventure;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Presenter class to read data from API and present data to main activity
 * Created by Harrison on 11/05/2017.
 */
public class MainPresenter implements AdventureApiPresenter {

    //Dependencies
    private MainActivity mActivity;
    private AdventureApi mApi;
    //Data Fields
    private List<Adventure> mAdventureList = new ArrayList<>();
    //Debugging
    private static final String DEBUG_MAIN_PRESENTER = "debug_main_presenter";

    /**
     * CONSTRUCTOR
     *
     * @param activity The activity this presenter should be attached to
     * @param api      The api instance that this presenter should call to get data
     */
    public MainPresenter(MainActivity activity, AdventureApi api) {
        //Dependency injections
        mActivity = activity;
        mApi = api;
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
     * Creates sample data, puts into database
     */
    public void storeSampleData() {
        List<Adventure> list = new ArrayList<>();
        Adventure adventure = new Adventure("Firebase Entry 1", 111, 222);
        list.add(adventure);
        adventure = new Adventure("Firebase Entry 2", 222, 222);
        list.add(adventure);
        adventure = new Adventure("Firebase Entry 3", 333, 333);
        list.add(adventure);
        adventure = new Adventure("Firebase Entry 4", 444, 444);
        list.add(adventure);
        adventure = new Adventure("Firebase Entry 5", 555, 555);
        list.add(adventure);

        mApi.putAdventureList(list);
    }

    /**
     * Begins process to retrieve a list of adventures FROM THE DATABASE.
     * Callback method: onRetrieveAdventureList
     * @return
     */
    public void retrieveAdventureList() {
        mApi.getAdventureList(this);
    }

    /**
     * Retrieves an individual adventure based on it's id
     */
    public void retrieveAdventure(String adventureId) {
        mApi.getAdventure(this, adventureId);
        //TODO - pass adventure back up
    }

    /**
     * Returns the locally-held list of adventures that was retrieved by the Api (on demand)
     * @return
     */
    public List<Adventure> getAdventureList() {
        return mAdventureList;
    }

    //Adventure API presenter callback methods

    /**
     * Returns a specific adventure, may not be used here (maybe add to the current list)
     *
     * @param adventure The adventure that was retrieved by the API
     */
    @Override
    public void onRetrieveAdventure(Adventure adventure) {
        mAdventureList.add(adventure);
    }

    /**
     * Takes list and passes it back up to activity, formatted as necessary
     *
     * @param adventureList The list of adventures that was retrieved by the API
     */
    @Override
    public void onRetrieveAdventureList(List<Adventure> adventureList) {
        mAdventureList = adventureList;
        mActivity.onRetrieveAdventureList(mAdventureList);
    }

    /**
     * Takes error and passes it back up to activity to deal with it
     *
     * @param e
     */
    @Override
    public void onRetrieveError(Exception e) {
        Log.d(DEBUG_MAIN_PRESENTER, e.toString());
        mActivity.displayError(e.toString());
    }
}
