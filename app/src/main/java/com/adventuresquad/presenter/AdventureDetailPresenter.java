package com.adventuresquad.presenter;

import android.net.Uri;

import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.interfaces.PresentableAdventureView;
import com.adventuresquad.model.Adventure;

/**
 * Created by Harrison on 22/05/2017.
 */
public class AdventureDetailPresenter {
    private PresentableAdventureView mActivity;
    private AdventureApi mApi;
    private StorageApi mApiStore;
    private Adventure mAdventure;

    public AdventureDetailPresenter(PresentableAdventureView activity, AdventureApi api, StorageApi store) {
        mActivity = activity;
        mApi = api;
        mApiStore = store;
    }

    /**
     * Retrieves the specified adventure from the model (database)
     * @param adventureId
     */
    public void retrieveAdventure(String adventureId) {
        mApi.getAdventure(adventureId, new RetrieveDataRequest<Adventure>() {
            @Override
            public void onRetrieveData(Adventure data) {
                onRetrieveAdventure(data);
            }

            @Override
            public void onRetrieveDataFail(Exception e) {

            }
        });
    }

    public void onRetrieveAdventure(final Adventure adventure) {
        mAdventure = adventure;
        //Get image request
        mApiStore.retrieveAdventureImageUri(adventure.getAdventureId(), new RetrieveDataRequest<Uri>() {
            @Override
            public void onRetrieveData(Uri data) {
                adventure.setAdventureImageUri(data.toString());
                mActivity.displayAdventure(adventure);
            }

            @Override
            public void onRetrieveDataFail(Exception e) {
                mActivity.displayMessage("Couldn't load adventure!");
            }
        });
    }

    /**
     * Called when button is clicked
     * Provides details for plan presenter to initialise properly
     */
    public void createPlan() {
        mActivity.startCreatePlan(mAdventure.getAdventureId(), mAdventure.getAdventureTitle());
    }
}
