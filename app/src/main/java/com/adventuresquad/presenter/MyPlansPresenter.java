package com.adventuresquad.presenter;

import android.net.Uri;

import com.adventuresquad.activity.MyPlansActivity;
import com.adventuresquad.activity.MyPlansFragment;
import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.model.Plan;
import com.adventuresquad.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Presents a list of plans to the view
 * Created by Harrison on 11/06/2017.
 */
public class MyPlansPresenter {

    private MyPlansFragment mView;
    private StorageApi mStorageApi;
    private PlanApi mPlanApi;
    private UserApi mUserApi;
    private SquadApi mSquadApi;

    //Data
    //Needs to be package-private to override in sub classes
    User mCurrentUser;

    public MyPlansPresenter() {}

    public MyPlansPresenter(MyPlansFragment view,
                            PlanApi planApi,
                            StorageApi storageApi,
                            UserApi userApi,
                            SquadApi squadApi) {
        mView = view;

        //Start retrieving list items as necessary
        mStorageApi = storageApi;
        mPlanApi = planApi;
        mUserApi = userApi;
        mSquadApi = squadApi;

        retrievePlans();
    }

    /**
     * Begins process to retrieve a list of the user's plans.
     * Progresses to retrieveUserPlanIds
     */
    public void retrievePlans() {
        mView.showLoadingIcon();

        //Gets the current user object
        mUserApi.retrieveCurrentUser(new RetrieveDataRequest<User>(){
            @Override
            public void onRetrieveData(User data) { //User was successfully retrieved
                mCurrentUser = data;
                retrieveUserPlanIds(); //Get user's squad plans
            }

            @Override
            public void onRetrieveDataFail(Exception e) {
                mView.displayMessage("User error: " + e.toString());
            }
        });
    }

    /**
     * Uses the user's personal squad object to retrieve a list of plan IDs from the squad db
     */
    public void retrieveUserPlanIds() {
        mPlanApi.retrieveUserPlanList(mCurrentUser.getUserId(), new RetrieveDataRequest<Plan>() {
            @Override
            public void onRetrieveData(Plan data) {
                if (filterMatch(data)) {
                    mView.hideLoadingIcon();
                    retrievePlanImageUrl(data);
                }
            }

            @Override
            public void onRetrieveDataFail(Exception e) {
                //User doesn't have any plans
                mView.hideLoadingIcon();
                mView.displayMessage(e.getMessage());
            }
        });
    }

    /**
     * Checks if a plan matches a given filter condition (for displaying different lists of plans)
     * Should be overridden in child classes
     * @param plan The plan to check the filter against
     * @return Whether the given plan passes through the filter or not
     */
    public boolean filterMatch(Plan plan) {
        return true;
    }

    /**
     * Retrieves the image URL for the particular plan and then displays it
     * @param plan The plan to retrieve the image for
     */
    private void retrievePlanImageUrl(final Plan plan) {
        //TODO - change this to get a plan image instead of adventure URL (later)
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
        //mPlanList.add(plan);
        mView.addListItem(plan);
    }
}
