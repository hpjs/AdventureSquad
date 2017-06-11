package com.adventuresquad.presenter.interfaces;

import com.adventuresquad.model.Plan;

/**
 * Interface to allow presenter interaction with Api classes
 * Created by Harrison on 2/06/2017.
 */
public interface PlanApiPresenter {

    /**
     * Called when...?
     */
    //public void startCreatePlan();

    /**
     * Called when a plan is successfully made
     * @param plan the plan that was created (with complete Ids and everything)
     */
    public void onCompletePlanCreation(Plan plan);

}
