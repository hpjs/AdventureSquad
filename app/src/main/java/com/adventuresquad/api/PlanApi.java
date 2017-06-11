package com.adventuresquad.api;

import android.support.annotation.NonNull;

import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.api.interfaces.StoreDataRequest;
import com.adventuresquad.model.Plan;
import com.adventuresquad.presenter.interfaces.PlanApiPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Interacts with Google FireBase directly
 * Created by Harrison on 2/06/2017.
 */
public class PlanApi {

    public static final String PLANS_LIST = "plans";

    private FirebaseDatabase mDatabaseInstance;
    private DatabaseReference mPlansData;

    public PlanApi() {
        mDatabaseInstance = FirebaseDatabase.getInstance();
        mPlansData = mDatabaseInstance.getReference(PLANS_LIST);
    }

    /**
     * Creates a plan in the database and also assigns to the correct squad
     * @param plan
     * @param callback
     */
    public void createPlan(final Plan plan, final StoreDataRequest<Plan> callback) {
        DatabaseReference mNewPlanRef = mPlansData.push();
        final String planId = mNewPlanRef.getKey();
        plan.setPlanId(planId);
        //prepare callback method for when this task is complete
        //Set the actual data
        mNewPlanRef.setValue(plan).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                //Plan creation was successful, now add this plan to the squad
                    addPlanToSquad(plan, new StoreDataRequest<Plan>() {
                        @Override
                        public void onStoreData(Plan data) {
                            callback.onStoreData(data);
                        }
                        @Override
                        public void onStoreDataFail(Exception e) {
                            callback.onStoreDataFail(e);
                        }
                    });
                } else {
                    callback.onStoreDataFail(task.getException());
                }
            }
        });
    }

    /**
     * Adds a created plan to the squad listed in the plan
     * @param plan
     * @param callback
     */
    private void addPlanToSquad(final Plan plan, final StoreDataRequest<Plan> callback) {
        //Gets the correct reference to the squad plan section
        DatabaseReference mSquadPlanRef = mDatabaseInstance.getReference("squads/" + plan.getSquadId() + "/squadPlans/" + plan.getPlanId());
        mSquadPlanRef.setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onStoreData(plan);
                } else {
                    callback.onStoreDataFail(task.getException());
                }
            }
        });

    }

    /**
     * Retrieves a plan from the Firebase database
     * @param planId The ID of the specific plan to retrieve
     * @param callback The callback to refer back to when operation complete
     */
    public void retrievePlan(String planId, final RetrieveDataRequest<Plan> callback) {
        DatabaseReference mPlanRef = mPlansData.child(planId);

        mPlanRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Marshall adventure into an adventure object
                Plan retrievedPlan = dataSnapshot.getValue(Plan.class);
                callback.onRetrieveData(retrievedPlan);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onRetrieveDataFail(databaseError.toException());
            }
        });
    }

    /**
     * Retrieves a given list of adventures from the online database
     * @param planIds The list of specific plan objects to get from the database
     * @param callback The request to return to when task complete
     */
    public void getPlanList(List<String> planIds, final RetrieveDataRequest<Plan> callback) {
        //Loop over the list of IDs to get each plan in succession
        for (String planId : planIds) {
            //When each one is complete, call back to the class to update the list with another entry
            //NOTE: This could potentially retrieve list objects out of order.
            retrievePlan(planId, new RetrieveDataRequest<Plan>() {
                @Override
                public void onRetrieveData(Plan plan) {
                    callback.onRetrieveData(plan);
                }

                @Override
                public void onRetrieveDataFail(Exception e) {
                    callback.onRetrieveDataFail(e);
                }
            });

        }
    }
}
