package com.adventuresquad.presenter;

import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.interfaces.PresentableAdventureActivity;
import com.adventuresquad.interfaces.RetrieveImageUriRequest;
import com.adventuresquad.model.Adventure;
import com.adventuresquad.presenter.interfaces.AdventureApiPresenter;
import com.adventuresquad.presenter.interfaces.StorageApiPresenter;

import java.util.List;

/**
 * Created by Harrison on 22/05/2017.
 */
public class AdventureDetailPresenter implements AdventureApiPresenter, StorageApiPresenter {
    private PresentableAdventureActivity mActivity;
    private AdventureApi mApi;
    private StorageApi mApiStore;

    public AdventureDetailPresenter(PresentableAdventureActivity activity, AdventureApi api, StorageApi store) {
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
        mActivity.onRetrieveAdventure(adventure);
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
}
