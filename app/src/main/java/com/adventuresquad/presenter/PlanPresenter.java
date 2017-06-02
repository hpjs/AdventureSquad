package com.adventuresquad.presenter;

import com.adventuresquad.api.PlanApi;
import com.adventuresquad.interfaces.PresentablePlanView;
import com.adventuresquad.model.Plan;
import com.adventuresquad.presenter.interfaces.PlanApiPresenter;

/**
 * Presenter for the PlanAdventure activity
 * Created by Harrison on 2/06/2017.
 */
public class PlanPresenter implements PlanApiPresenter {
    private PresentablePlanView mView;
    private PlanApi mApi;

    private Plan mCurrentPlan;
//    private

    /**
     *
     * @param api A new api object to access the plans
     * @param view The view that this presenter is managing
     * @param adventureId The adventure that the plan is for (relates to)
     */
    public PlanPresenter(PlanApi api, PresentablePlanView view, String adventureId) {
        mApi = api;
        mView = view;

        mCurrentPlan = new Plan();
        //TODO - set the ADventure ID for the plan
        mCurrentPlan.setAdventureId(adventureId);
    }

    @Override
    public void retrieveSquads() {    }

    @Override
    public void onRetrieveSquads() {    }

    @Override
    public void onRetrieveSquadsFail(Exception e) {    }

    /**
     * Called from activity when user continued after first fragment without selecting a squad
     */
    public void noSquadSelected() {
        //Move view on to date selection if possible
        //TODO - ask Guil if controlling view should be in the presenter or in the activity
        //create new squad with user in it if not already existing

    }

    /**
     *
     * @param squadId
     */
    public void squadSelected(String squadId) {

    }

    public void createPlan() {
        //TODO - Show loading icon on view
        //Start creation with the API
        mApi.putPlan(mCurrentPlan, this);
    }

    @Override
    public void onCreatePlan() {
        //TODO - hide loading icon
        mView.completePlanCreation();
    }

    @Override
    public void onCreatePlanFail(Exception e) {
        mView.displayMessage(e.toString());
    }
}
