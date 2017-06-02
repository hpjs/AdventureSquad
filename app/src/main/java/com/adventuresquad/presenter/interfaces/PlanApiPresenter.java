package com.adventuresquad.presenter.interfaces;

/**
 * Interface to allow presenter interaction with Api classes
 * Created by Harrison on 2/06/2017.
 */
public interface PlanApiPresenter {

    /**
     * Used to ask the API for a list of squads
     */
    public void retrieveSquads();

    /**
     * Called when API has successfully retrieved squads
     */
    public void onRetrieveSquads();

    /**
     * Called when API failed to get squads for some reason
     */
    public void onRetrieveSquadsFail(Exception e);


    /**
     * Called when...?
     */
    //public void createPlan();

    /**
     * Called when a plan is successfully made
     */
    public void onCreatePlan();

    /**
     * Called when a plan could not be made
     * @param e The exception that occurred
     */
    void onCreatePlanFail(Exception e);
}
