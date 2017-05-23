package com.adventuresquad.presenter;

import android.content.res.Resources;
import android.media.Image;
import android.net.Uri;
import android.util.Log;

import com.adventuresquad.R;
import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.interfaces.PresentableAdventureListActivity;
import com.adventuresquad.model.Adventure;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity Presenter class to read data from API and present data to main activity
 * Created by Harrison on 11/05/2017.
 */
public class MainPresenter implements AdventureApiPresenter, StorageApiPresenter {

    //Dependencies
    private PresentableAdventureListActivity mActivity;
    private AdventureApi mApi;
    private StorageApi mApiStore;
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
    public MainPresenter(PresentableAdventureListActivity activity, AdventureApi api, StorageApi store) {
        //Dependency injections
        mActivity = activity;
        mApi = api;
        mApiStore = store;
    }

    /**
     * Creates sample data, puts into database
     */
    public void storeSampleData(String sampleText) {

        List<Adventure> list = new ArrayList<>();
        Adventure adventure = new Adventure("FireBase Entry 1", sampleText, 111, 111);
        list.add(adventure);

        adventure = new Adventure("Firebase Entry 2", sampleText, 222, 222);
        list.add(adventure);

        adventure = new Adventure("Firebase Entry 3", sampleText, 333, 333);
        list.add(adventure);

        adventure = new Adventure("Firebase Entry 4", sampleText, 444, 444);
        list.add(adventure);

        adventure = new Adventure("Firebase Entry 5", sampleText, 555, 555);
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

    @Override
    public void onCreateAdventure(String adventureId) {
        //Adventure created successfully, pass back to the activity
        // so it can add a picture to the adventure as well
    }

    @Override
    public void onCreateAdventureError(Exception e) {
        //Fail - could not
    }

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

    //TODO - remove this method (testing method only)
    public void addSampleImage(Uri imagePath, Adventure adventure) {
        mApiStore.storeAdventureImage(imagePath, adventure, "adventureimage.jpg");
    }

    //TODO - Test method - remove later

    /**
     * Loops over current list of adventures and adds images to them
     * @param imagePath
     */
    public void addSampleImages(Uri imagePath) {
        for (Adventure adventure : mAdventureList) {
            addSampleImage(imagePath, adventure);
        }
    }
}
