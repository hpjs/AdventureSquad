package com.adventuresquad.presenter;

import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.interfaces.PresentablePlanView;
import com.adventuresquad.model.Plan;
import com.adventuresquad.model.Squad;
import com.adventuresquad.model.User;
import com.adventuresquad.presenter.interfaces.PlanApiPresenter;
import com.adventuresquad.presenter.interfaces.UserApiPresenter;

/**
 * Presenter for the PlanAdventure activity
 * Created by Harrison on 2/06/2017.
 */
public class PlanPresenter implements PlanApiPresenter, UserApiPresenter {
    private PresentablePlanView mView;
    private PlanApi mApi;
    private UserApi mUserApi;
    private SquadApi mSquadApi;

    private Plan mCurrentPlan;
//    private

    /**
     * @param api A new api object to access the plans
     * @param view The view that this presenter is managing
     * @param adventureId The adventure that the plan is for (relates to)
     * @param userApi
     * @param squadApi
     */
    public PlanPresenter(PlanApi api, PresentablePlanView view, String adventureId, UserApi userApi, SquadApi squadApi) {
        mApi = api;
        mView = view;
        mUserApi = userApi;
        mSquadApi = squadApi;

        mCurrentPlan = new Plan();
        mCurrentPlan.setAdventureId(adventureId);
    }

    /**
     * User does not want to
     */
    public void noSquadSelected() {
        //Move view on to date selection if possible
        //TODO - ask Guil if controlling view should be in the presenter or in the activity
        //create new squad with user in it if not already existing


        //Get current user object from the database.
        mUserApi.retrieveCurrentUser(this);

        //SQUAD / PLAN LOGIC CONTINUES IN 'onRetrieveCurrentUser'
        //TODO - should potentially convert this to use anon inner class instead
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

    /**
     * Current user was retrieved from the database
     * @param user
     */
    @Override
    public void onRetrieveCurrentUser(User user) {
        String userSquadId = user.getUserSquadId();
        if (userSquadId != null && !userSquadId.isEmpty()) {
            //
            //Create plan

            //Continue as normal
        } else {
            //User does not have a squad ID, need to create their personal squad for adventuring
            //ERROR - issue because there's no personal squad ID, should have been made in in user registration

            mSquadApi.createPersonalSquad(userId);
        }
        //Check if it has a squad ID
        //If not, then create a new personal squad.
        //Update the user with the newly created personal squad ID

        //Update (LOCAL!) plan with user's personal squad ID
    }

    /**
     * Current user was retrieved from the database
     * @param exception The exception of this particular failure
     */
    @Override
    public void onRetrieveCurrentUserFail(Exception exception) {

    }

    @Override
    public void onAddUser() {

    }

    @Override
    public void onAddUserFail(Exception e) {

    }
}
