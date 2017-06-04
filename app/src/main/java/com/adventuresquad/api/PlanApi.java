package com.adventuresquad.api;

import android.support.annotation.NonNull;

import com.adventuresquad.model.Plan;
import com.adventuresquad.model.User;
import com.adventuresquad.presenter.interfaces.PlanApiListPresenter;
import com.adventuresquad.presenter.interfaces.PlanApiPresenter;
import com.adventuresquad.presenter.interfaces.UserApiPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
    public void createPlan(final Plan plan, final PlanApiPresenter callback) {
        DatabaseReference mNewPlanRef = mPlansData.push();
        final String planId = mNewPlanRef.getKey();
        plan.setPlanId(planId);
        //prepare callback method for when this task is complete
        //Set the actual data
        mNewPlanRef.setValue(plan).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onCompletePlanCreation(plan);
                } else {
                    callback.onCreatePlanFail(task.getException());
                }
            }
        });
    }

    /**
     * Simple callback interface for retrieving a plan easily
     */
    private interface RetrievePlanListener {
        public void onRetrievePlan(Plan plan);
        public void onRetrievePlanFail(Exception e);
    }

    /**
     * Retrieves a plan from the Firebase database
     * @param planId The ID of the specific plan to retrieve
     * @param listener The listener to refer back to when operation complete
     */
    public void retrievePlan(String planId, final RetrievePlanListener listener) {
        DatabaseReference mPlanRef = mPlansData.child(planId);

        mPlanRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Marshall adventure into an adventure object
                Plan retrievedPlan = dataSnapshot.getValue(Plan.class);
                listener.onRetrievePlan(retrievedPlan);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onRetrievePlanFail(databaseError.toException());
            }
        });
    }

    /**
     * Retrieves a given list of adventures from the online database
     * @param planIds The list of specific plan objects to get from the database
     * @param callbackPresenter The presenter to call back methods on when complete
     */
    public void getPlanList(List<String> planIds, final PlanApiListPresenter callbackPresenter) {
        //Loop over the list of IDs to get each plan in succession
        for (String planId : planIds) {
            //When each one is complete, call back to the class to update the list with another entry
            //NOTE: This could potentially retrieve list objects out of order.
            retrievePlan(planId, new RetrievePlanListener() {
                @Override
                public void onRetrievePlan(Plan plan) {
                    callbackPresenter.onRetrieveUserPlan(plan);
                }

                @Override
                public void onRetrievePlanFail(Exception e) {
                    callbackPresenter.onRetrieveUserPlanFail(e);
                }
            });

        }
    }

    //Interfaces for other API classes to access this one
    public interface retrieveSquadPlansListener {
        public void onRetrieveSquadPlans();

        public void onRetrieveSquadPlansFail();
    }

}
