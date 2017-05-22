package com.adventuresquad.presenter;

import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.interfaces.PresentableAdventureActivity;
import com.adventuresquad.model.Adventure;

import java.util.List;

/**
 * Created by Harrison on 22/05/2017.
 */
public class AdventureDetailPresenter implements AdventureApiPresenter {
    private PresentableAdventureActivity mActivity;
    private AdventureApi mApi;

    public AdventureDetailPresenter(PresentableAdventureActivity activity, AdventureApi api) {
        mActivity = activity;
        mApi = api;
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
        mActivity.displayError(e.toString());
    }

    /**
     * Retrieves the specified adventure from the model (database)
     * @param adventureId
     */
    public void retrieveAdventure(String adventureId) {
        mApi.getAdventure(this, adventureId);
    }
}
