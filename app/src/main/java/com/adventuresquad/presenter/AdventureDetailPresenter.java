package com.adventuresquad.presenter;

import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.interfaces.PresentableAdventureView;
import com.adventuresquad.interfaces.RetrieveImageUriRequest;
import com.adventuresquad.model.Adventure;
import com.adventuresquad.presenter.interfaces.AdventureApiPresenter;
import com.adventuresquad.presenter.interfaces.StorageApiPresenter;

import java.util.List;

/**
 * Created by Harrison on 22/05/2017.
 */
public class AdventureDetailPresenter implements AdventureApiPresenter, StorageApiPresenter {
    private PresentableAdventureView mActivity;
    private AdventureApi mApi;
    private StorageApi mApiStore;
    private Adventure mAdventure;

    public AdventureDetailPresenter(PresentableAdventureView activity, AdventureApi api, StorageApi store) {
        mActivity = activity;
        mApi = api;
        mApiStore = store;
    }

    @Override
    public void onCreateAdventure(String adventureId) {

    }

    @Override
    public void onCreateAdventureError(Exception e) {

    }

    @Override
    public void onRetrieveAdventure(Adventure adventure) {
        mAdventure = adventure;
        mActivity.displayAdventure(adventure);
    }

    @Override
    public void onRetrieveAdventureList(List<Adventure> adventureList) {

    }

    @Override
    public void onRetrieveError(Exception e) {
        mActivity.displayMessage(e.toString());
    }

    /**
     * Retrieves the specified adventure from the model (database)
     * @param adventureId
     */
    public void retrieveAdventure(String adventureId) {
        mApi.getAdventure(this, adventureId);
    }

    @Override
    public void retrieveAdventureImageUri(String adventureId, RetrieveImageUriRequest callback) {
        mApiStore.retrieveAdventureImageUri(adventureId, callback);
    }

    /**
     * CURRENTLY NOT NEEDED, as retrieveAdventureImageUri contains a callback in it's methods
     * @param uri
     */
    @Override
    public void onRetrieveAdventureImageUri(String uri) {

    }

    /**
     * Called when button is clicked
     * Provides details to plan basically
     */
    public void createPlan() {
        mActivity.startCreatePlan(mAdventure.getAdventureId(), mAdventure.getAdventureTitle());
    }
}
