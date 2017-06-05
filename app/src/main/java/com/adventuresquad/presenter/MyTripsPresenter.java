package com.adventuresquad.presenter;

import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.interfaces.PresentablePlanListView;
import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.interfaces.RetrieveImageUriRequest;
import com.adventuresquad.model.Plan;
import com.adventuresquad.model.User;
import com.adventuresquad.presenter.interfaces.PlanApiListPresenter;
import com.adventuresquad.presenter.interfaces.StorageApiPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for mytrips list of trips / plans
 * Created by Harrison on 4/06/2017.
 */
public class MyTripsPresenter implements StorageApiPresenter, PlanApiListPresenter {

    //Dependencies
    private PresentablePlanListView mView;
    private StorageApi mStorageApi;
    private PlanApi mPlanApi;
    private UserApi mUserApi;
    private SquadApi mSquadApi;

    //Data
    private List<Plan> mPlanList = new ArrayList<>();
    private User mCurrentUser;

    public MyTripsPresenter(PresentablePlanListView view,
                            PlanApi planApi,
                            StorageApi storageApi,
                            UserApi userApi,
                            SquadApi squadApi) {
        mView = view;
        mStorageApi = storageApi;
        mPlanApi = planApi;
        mUserApi = userApi;
        mSquadApi = squadApi;
    }

    /**
     * Begins process to retrieve a list of the user's plans.
     * Callback method: onRetrievePlans
     */
    public void retrievePlans() {
        mView.displayMessage("Refreshing Plan list...");

        //Gets the current user object
        mUserApi.retrieveCurrentUser(new UserApi.RetrieveUserListener() {
            @Override
            public void onGetUser(User user) { //User was successfully retrieved
                mCurrentUser = user;
                retrieveUserPlanIds(); //Get user's squad plans
            }

            @Override
            public void onGetUserFail(Exception e) {
                mView.displayMessage("User error: " + e.toString());
            }
        });
    }

    /**
     * Uses the user's personal squad object to retrieve a list of plan IDs from the squad db
     */
    public void retrieveUserPlanIds() {
        String userSquadId = mCurrentUser.getUserSquadId();
        mSquadApi.getPlanList(userSquadId, new SquadApi.RetrievePlanListListener() {
            @Override
            public void onGetPlanList(List<String> planIdList) {
                //Successfully retrieved list of plan IDs from user's squad
                //Now use plan API to retrieve them
                retrieveUserPlans(planIdList);
            }

            @Override
            public void onGetPlanListFail(Exception e) {
                mView.displayMessage("User plans error: " + e.toString());
            }
        });
    }

    /*
     * Uses a list of planIds to retrieve a list of plans from the database
     */
    public void retrieveUserPlans(List<String> planIds) {
        mPlanApi.getPlanList(planIds, this);
    }

    /**
     * Called when any plan in the list is received
     * @param plan
     */
    @Override
    public void onRetrieveUserPlan(Plan plan) {
        mPlanList.add(plan);
        //mView.updatePlanList(mPlanList);
        mView.addPlanToList(plan);
    }

    @Override
    public void onRetrieveUserPlanFail(Exception e) {
        mView.displayMessage("Could not retrieve all of your plans.");
    }

    /**
     * Retrieves an image URI for a particular adventure
     * @param adventureId The adventure to retrieve the image URL for
     * @param callback The method to call when the URL is retrieved successfully (useful for lists)
     */
    public void retrieveAdventureImageUri(String adventureId, RetrieveImageUriRequest callback) {
        mStorageApi.retrieveAdventureImageUri(adventureId, callback);
    }
}