package com.adventuresquad.presenter;

import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.interfaces.PresentablePlanView;
import com.adventuresquad.model.Plan;
import com.adventuresquad.model.User;
import com.adventuresquad.presenter.interfaces.PlanApiPresenter;
import com.adventuresquad.presenter.interfaces.SquadApiPresenter;
import com.adventuresquad.presenter.interfaces.UserApiPresenter;

/**
 * Presenter for the PlanAdventure activity
 * Created by Harrison on 2/06/2017.
 */
public class PlanPresenter implements PlanApiPresenter, UserApiPresenter, SquadApiPresenter {
    private PresentablePlanView mView;
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
    public PlanPresenter(PresentablePlanView view, String adventureId, String adventureTitle, PlanApi planApi, UserApi userApi, SquadApi squadApi) {
        mView = view;
        mPlanApi = planApi;
        mUserApi = userApi;
        mSquadApi = squadApi;

        mCurrentPlan = new Plan();
        mCurrentPlan.setAdventureId(adventureId);
        mCurrentPlan.setPlanTitle(adventureTitle);

        //Retrieve list of squads for list
        mUserApi.retrieveCurrentUser(this);
    }

    /**
     * Retrieves current user and adds their personal squad to the plan
     * @param user
     */
    @Override
    public void onRetrieveCurrentUser(User user) {
        //Local user was retrieved successfully, continue with making a plan for them
        mCurrentUser = user;
        //mView.hideLoadingIcon
    }

    /**
     * User does not want to adenture with a squad
     */
    public void addPersonalSquadToPlan() {
        //TODO - ask Guil if controlling view should be in the presenter or in the activityString userSquadId = user.getUserSquadId();
        String userSquadId = mCurrentUser.getUserSquadId();
        if (userSquadId != null && !userSquadId.isEmpty()) {
            //Set squad ID correctly
            mCurrentPlan.setSquadId(userSquadId);
            //Move view on to next page
            mView.onAddSquadToPlan();
        } else {
            //User does not have a squad ID, need to create their personal squad for adventuring
            //ERROR - issue because there's no personal squad ID, should have been made in in user registration
            mView.displayMessage("Error - your user wasn't set up properly with a personal squad");
        }
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
        //TODO - Show loading icon on view
        //Start creation with the API
        mPlanApi.createPlan(mCurrentPlan, this);
    }

    @Override
    public void onCompletePlanCreation(Plan plan) {
        //TODO - hide loading icon
        mCurrentPlan = plan;
        mSquadApi.addPlanToSquad(plan, this);
    }

    @Override
    public void onAddPlanToSquad() {
        //FINALLY the flow is finished,
        //mCurrentPlan = null;

        mView.completePlanCreation();
    }

    @Override
    public void onCreatePlanFail(Exception e) {
        mView.displayMessage(e.toString());
    }

    /**
     * Current user was retrieved from the database
     * @param exception The exception of this particular failure
     */
    @Override
    public void onRetrieveCurrentUserFail(Exception exception) {

    }

    @Override
    public void onUpdateUserSquad() {

    }

    @Override
    public void onUpdateUserSquadFail() {

    }

    @Override
    public void onAddUser() {

    }

    @Override
    public void onAddUserFail(Exception e) {

    }

    @Override
    public void createSquad() {

    }

    @Override
    public void onCreateSquad() {

    }

    @Override
    public void onCreateSquadFail(Exception e) {

    }

    @Override
    public void retrieveSquads() {

    }

    @Override
    public void onRetrieveSquads() {

    }
}
