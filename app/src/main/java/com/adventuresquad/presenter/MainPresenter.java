package com.adventuresquad.presenter;

import android.net.Uri;
import android.util.Log;

import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.interfaces.PresentableAdventureListView;
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
    private PresentableAdventureListView mActivity;
    private AdventureApi mAdventureApi;
    private StorageApi mApiStore;
    //Data Fields
    private List<Adventure> mAdventureList = new ArrayList<>();
    //Debugging
    private static final String DEBUG_MAIN_PRESENTER = "debug_main_presenter";

    /**
     * CONSTRUCTOR
     * @param activity The activity this presenter should be attached to
     * @param adventureApi      The adventureApi instance that this presenter should call to get data
     */
    public MainPresenter(PresentableAdventureListView activity, AdventureApi adventureApi, StorageApi store) {
        //Dependency injections
        mActivity = activity;
        mAdventureApi = adventureApi;
        mApiStore = store;
        //Begin retrieval of the list
        retrieveAdventureList();
    }

    /**
     * Begins process to retrieve a list of adventures FROM THE DATABASE.
     * Callback method: retrieveAdventureImageUris
     * @return
     */
    public void retrieveAdventureList() {
        mActivity.showLoadingIcon();
        //Request the list of adventures
        mAdventureApi.getAdventureList(new RetrieveDataRequest<List<Adventure>>() {
            @Override
            public void onRetrieveData(List<Adventure> data) {
                retrieveAdventureImageUris(data);
            }

            @Override
            public void onRetrieveDataFail(Exception e) {
                Log.d(DEBUG_MAIN_PRESENTER, e.toString());
                mActivity.displayMessage(e.toString());
            }
        });
    }

    /**
     * Defines logic that has to happen after a list of adventures has been retrieved
     * E.g. downloading image URLs
     * @param advList The list of adventure objects that need to be worked upon
     */
    public void retrieveAdventureImageUris(List<Adventure> advList) {
        //Start requests to retrieve image uri's
        //When retrieved, they can be passed to the View to be added to the list dynamically
        for (final Adventure adventure : advList) {
            mApiStore.retrieveAdventureImageUri(adventure.getAdventureId(),
                    new RetrieveDataRequest<Uri>() {
                @Override
                public void onRetrieveData(Uri data) {
                    adventure.setAdventureImageUri(data.toString()); //TODO - check if the 'final' affects setting this
                    onRetrieveAdventure(adventure);
                }

                @Override
                public void onRetrieveDataFail(Exception e) {
                    Log.d(DEBUG_MAIN_PRESENTER, "Failed to retrieve an image URI.");
                    onRetrieveAdventure(adventure);
                }
            });
        }
    }

    /**
     * Called when any plan in the list is received
     * @param adventure the adventure that was just retrieved from the database
     */
    @Override
    public void onRetrieveAdventure(Adventure adventure) {
        mActivity.hideLoadingIcon();
        mAdventureList.add(adventure);
        //mView.updatePlanList(mPlanList);
        mActivity.addAdventureToList(adventure);
    }

    //region UNUSED CODE

//    /**
//     * Takes list and passes it back up to activity, formatted as necessary
//     *
//     * @param adventureList The list of adventures that was retrieved by the API
//     */
//    @Override
//    public void retrieveAdventureImageUris(List<Adventure> adventureList) {
//        mAdventureList = adventureList;
//        mActivity.retrieveAdventureImageUris(mAdventureList);
//    }

    /**
     * Retrieves an individual adventure based on it's id
     */
    public void retrieveAdventure(String adventureId) {
        //mAdventureApi.getAdventure(this, adventureId);
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
     * Retrieves an image URI for a particular adventure
     */
    public void retrieveAdventureImageUri(String adventureId, RetrieveDataRequest<Uri> callback) {

        mApiStore.retrieveAdventureImageUri(adventureId, callback);
    }

    /**
     * Takes error and passes it back up to activity to deal with it
     *
     * @param e
     */
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

        mAdventureApi.putAdventureList(list);
    }

    //endregion
}
