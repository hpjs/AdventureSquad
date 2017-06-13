package com.adventuresquad.presenter;

import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.api.interfaces.StoreDataRequest;
import com.adventuresquad.interfaces.PresentablePlanAdventureView;
import com.adventuresquad.model.Plan;
import com.adventuresquad.model.User;

/**
 * Presenter for the PlanAdventure activity
 * Created by Harrison on 2/06/2017.
 */
public class PlanAdventurePresenter {
    private PresentablePlanAdventureView mView;
    private PlanApi mPlanApi;
    private UserApi mUserApi;
    private SquadApi mSquadApi;

    private User mCurrentUser;
    private Plan mCurrentPlan;

    /**
     * @param view The view that this presenter is managing
     * @param adventureId The adventure that the plan is for (relates to)
     * @param adventureTitle
     * @param planApi A new planApi object to access the plans
     * @param userApi User API so this presenter can access current user
     * @param squadApi New squad API so this presenter can access the list of user's squads
     */
    public PlanAdventurePresenter(PresentablePlanAdventureView view, String adventureId, String adventureTitle, PlanApi planApi, UserApi userApi, SquadApi squadApi) {
        mView = view;
        mPlanApi = planApi;
        mUserApi = userApi;
        mSquadApi = squadApi;

        mCurrentPlan = new Plan();
        mCurrentPlan.setAdventureId(adventureId);
        mCurrentPlan.setPlanTitle(adventureTitle);

        //Retrieve list of squads for list
    }

    /**
     * Used when user wants to adventure with a particular squad
     * @param squadId
     */
    public void addSquadToPlan(String squadId) {
        if (squadId != null && !squadId.isEmpty()) {
            //Set squad ID correctly
            mCurrentPlan.setSquadId(squadId);
            //Move view on to next page
            mView.onAddSquadToPlan();
        } else {
            //User does not have a squad ID, need to create their personal squad for adventuring
            //ERROR - issue because there's no personal squad ID, should have been made in in user registration
            mView.displayMessage("Error - your user wasn't set up properly with a personal squad");
        }
    }

    public void addDateToPlan(long date) {
        //Adds the date to the plan
        mCurrentPlan.setBookingDate(date);
    }

    /**
     * Creates a plan in DB using the local Plan object
     * Flow: createPlan() -> onCompletePlanCreation() -> addPlanToSquad() -> onAddPlanToSquad()
     */
    public void createPlan() {
        mView.showLoadingIcon();
        //Creates a plan & adds it to the
        mPlanApi.createPlan(mCurrentPlan, new StoreDataRequest<Plan>(){
            //Plan creation is complete
            @Override
            public void onStoreData(Plan data) {
                onCompletePlanCreation(data);
            }

            //Couldn't create plan or assign it to squad
            @Override
            public void onStoreDataFail(Exception e) {
                mView.displayMessage("Couldn't create plan.");
            }
        });
    }

    public void onCompletePlanCreation(Plan plan) {
        mView.hideLoadingIcon();
        mCurrentPlan = plan;
        mView.completePlanCreation();
    }
}
