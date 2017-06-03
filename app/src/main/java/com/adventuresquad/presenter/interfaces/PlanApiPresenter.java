package com.adventuresquad.presenter.interfaces;

/**
 * Interface to allow presenter interaction with Api classes
 * Created by Harrison on 2/06/2017.
 */
public interface PlanApiPresenter {

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

    void onRetrieveCurrentUserFail(Exception exception);
}
