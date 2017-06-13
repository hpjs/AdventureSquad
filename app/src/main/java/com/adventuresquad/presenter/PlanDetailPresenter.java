package com.adventuresquad.presenter;

import android.net.Uri;

import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.interfaces.PresentablePlanDetailView;
import com.adventuresquad.model.Adventure;
import com.adventuresquad.model.Plan;
import com.adventuresquad.model.Squad;

/**
 * Presents the details of a plan to a plan details view
 * Created by Harrison on 13/06/2017.
 */
public class PlanDetailPresenter {

    //View details
    private PresentablePlanDetailView mView;
    private String mPlanId;
    private Plan mPlan;
    private Squad mPlanSquad;
    private Adventure mPlanAdventure;

    //API details
    private PlanApi mPlanApi;
    private SquadApi mSquadApi;
    private AdventureApi mAdventureApi;
    private StorageApi mStorageApi;

    public PlanDetailPresenter(PresentablePlanDetailView view,
                               String planId,
                               PlanApi planApi,
                               SquadApi squadApi,
                               AdventureApi adventureApi,
                               StorageApi storageApi) {
        mView = view;
        mPlanId = planId;
        //APIs
        mPlanApi = planApi;
        mSquadApi = squadApi;
        mAdventureApi = adventureApi;
        mStorageApi = storageApi;

        //Kick off plan retrieval
        retrievePlan();
    }

    /**
     * Retrieves the plan for the view. When complete, displays and starts other retrievals. 
     */
    private void retrievePlan() {
        mView.showLoadingIcon();
        //Begins to retrieve the given plan ID
        mPlanApi.retrievePlan(mPlanId, new RetrieveDataRequest<Plan>() {
            @Override
            public void onRetrieveData(Plan data) {
                mPlan = data;
                mView.hideLoadingIcon();
                mView.displayPlan(data);
                retrieveSquad(data.getSquadId());
                retrieveAdventure(data.getAdventureId());
                retrievePlanImageUrl(data);
            }
            
            @Override
            public void onRetrieveDataFail(Exception e) {
                mView.displayMessage("Could not display plan!");
            }
        });
    }

    /**
     * retrieves the squad that this plan is related to
     */
    private void retrieveSquad(String squadId) {
        mSquadApi.retrieveSquad(squadId, new RetrieveDataRequest<Squad>() {
            @Override
            public void onRetrieveData(Squad data) {
                if (data.getSquadName() == null || data.getSquadName().isEmpty()) {
                    data.setSquadName("Yourself");
                }

                mView.displaySquad(data);
            }

            @Override
            public void onRetrieveDataFail(Exception e) {
                //TODO - implement display error on view
            }
        });
    }

    private void retrieveAdventure(String adventureId) {
        mAdventureApi.getAdventure(adventureId, new RetrieveDataRequest<Adventure>() {
            @Override
            public void onRetrieveData(Adventure data) {
                mView.displayAdventureDetail(data);
            }

            @Override
            public void onRetrieveDataFail(Exception e) {
                //TODO - implement display error on view
            }
        });
    }

    private void retrievePlanImageUrl(Plan plan) {
        String adventureId = plan.getAdventureId();
        mStorageApi.retrieveAdventureImageUri(adventureId, new RetrieveDataRequest<Uri>() {
            @Override
            public void onRetrieveData(Uri data) { //Image URL retrieved, display it
                mView.displayPlanImage(data.toString());
            }

            @Override
            public void onRetrieveDataFail(Exception e) { //Couldn't get image.

            }
        });
    }
}
