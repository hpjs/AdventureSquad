package com.adventuresquad.presenter;

import android.net.Uri;

import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.interfaces.PresentablePlanListView;
import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.StorageApi;
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
public class MyTripsPresenter implements StorageApiPresenter {

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
     * Progresses to retrieveUserPlanIds
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
        //Retrieves a plan list from a squad
        mSquadApi.retrievePlanList(userSquadId, new RetrieveDataRequest<List<String>>() {
            @Override
            public void onRetrieveData(List<String> planIdList) {
                //Successfully retrieved list of plan IDs from user's squad
                //Now use plan API to retrieve them one by one
                if (planIdList != null && planIdList.size() > 0) {
                    retrieveUserPlans(planIdList);
                } else {
                    //User doesn't have any plans
                    mView.displayMessage("You don't have any plans to display!");
                }
            }

            @Override
            public void onRetrieveDataFail(Exception e) {
                mView.displayMessage("User plans error: " + e.toString());
            }
        });
    }

    /**
     * Uses a list of planIds to retrieve a set of individual plans from the database
     * Note: Retrieves multiple plans asynchronously (independent of each other)
     */
    private void retrieveUserPlans(List<String> planIds) {
        mPlanApi.getPlanList(planIds, new RetrieveDataRequest<Plan>() {
            @Override
            public void onRetrieveData(Plan data) {
                //Single plan object retrieved, get the image download URL for this plan
                retrievePlanImageUrl(data);
            }

            @Override
            public void onRetrieveDataFail(Exception e) {
                mView.displayMessage("Could not retrieve all of your plans.");
            }
        });
    }

    /**
     * Retrieves the image URL for the particular plan and then displays it
     * @param plan The plan to retrieve the image for
     */
    private void retrievePlanImageUrl(final Plan plan) {
        //TODO - change this to get a plan image instead (later)
        String adventureId = plan.getAdventureId();
        mStorageApi.retrieveAdventureImageUri(adventureId, new RetrieveDataRequest<Uri>() {
            @Override
            public void onRetrieveData(Uri data) { //URL retrieved, set to plan and display it
                plan.setPlanImageUrl(data.toString());
                displayPlan(plan);
            }

            @Override
            public void onRetrieveDataFail(Exception e) { //Couldn't get image.

            }
        });
    }

    /**
     * Called when a plan is fully populated and ready to be displayed on the view
     * @param plan
     */
    public void displayPlan(Plan plan) {
        mPlanList.add(plan);
        mView.addPlanToList(plan);
    }
}
