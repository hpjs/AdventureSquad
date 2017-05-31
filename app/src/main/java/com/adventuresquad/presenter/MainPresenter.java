package com.adventuresquad.presenter;

import android.net.Uri;
import android.util.Log;

import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.interfaces.RetrieveImageUriRequest;
import com.adventuresquad.interfaces.PresentableAdventureListActivity;
import com.adventuresquad.model.Adventure;
import com.adventuresquad.presenter.interfaces.AdventureApiPresenter;
import com.adventuresquad.presenter.interfaces.StorageApiPresenter;

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
        retrieveAdventureList();
        //TODO - do we need to check authentication again here? E.g. going from logout -> main without finish()
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
        mActivity.displayMessage("Refreshing adventure list...");
        mApi.getAdventureList(this);
    }

    /**
     * Retrieves an individual adventure based on it's id
     */
    public void retrieveAdventure(String adventureId) {
        mApi.getAdventure(this, adventureId);
        //TODO - pass adventure back up
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
        mActivity.displayMessage(e.toString());
    }

    //TODO - remove this method (testing method only)
    public void addSampleImage(Uri imagePath, Adventure adventure) {
        mApiStore.storeAdventureImage(imagePath, adventure);
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

    /**
     * Retrieves an image URI for a particular adventure
     */
    public void retrieveAdventureImageUri(String adventureId, RetrieveImageUriRequest callback) {

        mApiStore.retrieveAdventureImageUri(adventureId, callback);
    }

    /**
     * Called when a specific adventure image uri is retrieved
     * CURRENTLY NOT NEEDED, as you pass in a callback as part of the retrieve method
     * @param uri
     */
    @Override
    public void onRetrieveAdventureImageUri(String uri) {
        //NOTE: Needs to know where to give this back to
        //Only way to do this would be to store a mapping of callbacks to the relevant adventure ID
        //This is probably not really safe or smart. Therefore
    }
}
