package com.adventuresquad.presenter.interfaces;

/**
 * Provides an API for
 * Created by Harrison on 2/06/2017.
 */
public interface SquadApiPresenter {

    public void createSquad();

    /**
     * Called when a squad is successfully created
     */
    public void onCreateSquad();

    /**
     * Called when a squad has not been successfully created
     * @param e
     */
    public void onCreateSquadFail(Exception e);

    /**
     * Used to ask the API for a list of squads
     */
    public void retrieveSquads();

    /**
     * Called when API has successfully retrieved squads
     */
    public void onRetrieveSquads();


}
