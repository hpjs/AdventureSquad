package com.adventuresquad.presenter;

import android.net.Uri;
import android.util.Log;

import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.interfaces.PresentableAdventureListView;
import com.adventuresquad.model.Adventure;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity Presenter class to read data from API and present data to main activity
 * Created by Harrison on 11/05/2017.
 */
public class MainPresenter {

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
     * Then when finished displays the actual adventure
     * @param advList The list of adventure objects that need to be worked upon
     */
    public void retrieveAdventureImageUris(List<Adventure> advList) {
        //Start requests to retrieve image uri's
        //When retrieved, they can be passed to the View to be added to the list dynamically
        for (final Adventure adventure : advList) {
            mApiStore.retrieveAdventureImageUri(adventure.getAdventureId(), new RetrieveDataRequest<Uri>() {
                @Override
                public void onRetrieveData(Uri data) {
                    adventure.setAdventureImageUri(data.toString());
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
    public void onRetrieveAdventure(Adventure adventure) {
        mActivity.hideLoadingIcon();
        mAdventureList.add(adventure);
        //mView.updatePlanList(mPlanList);
        mActivity.addAdventureToList(adventure);
    }
}
