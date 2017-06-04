package com.adventuresquad.presenter.interfaces;

import com.adventuresquad.model.Plan;
import com.google.firebase.database.DatabaseException;

import java.util.List;

/**
 * Allows interaction for a list of Plans presenter to retrieve from the database
 * Created by Harrison on 4/06/2017.
 */
public interface PlanApiListPresenter {

    /**
     * Callback method when the list of a user's personal plans is retrieved
     * @param userPlans The list of user's plans to go on adventures
     */
    //public void onRetrieveUserPlans(List<Plan> userPlans);

    //public void onRetrieveUserPlansFail(Exception e);

    /**
     * Called when a single plan is retrieved & should be added to the list of plans
     * @param plan
     */
    public void onRetrieveUserPlan(Plan plan);

    public void onRetrieveUserPlanFail(Exception e);
}
