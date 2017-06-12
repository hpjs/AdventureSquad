package com.adventuresquad.api;

import android.support.annotation.NonNull;

import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.api.interfaces.StoreDataRequest;
import com.adventuresquad.model.Plan;
import com.adventuresquad.model.Squad;
import com.adventuresquad.presenter.interfaces.PlanApiPresenter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
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
                    addPlanToSquad(plan, callback);
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
        DatabaseReference mSquadPlanRef = mDatabaseInstance.getReference("squads/" + plan.getSquadId() + "/userSquadPlans/" + plan.getPlanId());
        mSquadPlanRef.setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //callback.onStoreData(plan);
                    //TODO - replace this 'new SquadApi' section with a better call
                    addPlanToSquadUsers(plan, new SquadApi(), callback);
                } else {
                    callback.onStoreDataFail(task.getException());
                }
            }
        });

    }

    /**
     * Retrieves a list of squad users and adds a plan to all of them
     * @param plan The plan object to add to the users
     * @param squadApi The squad API to use to retrieve the users
     * @param callback The object to return to when the task is complete
     */
    private void addPlanToSquadUsers(final Plan plan, SquadApi squadApi, final StoreDataRequest<Plan> callback) {
        List<String> squadUsers = new ArrayList<>();
        squadApi.retrieveSquadUsers(plan.getSquadId(), new RetrieveDataRequest<List<String>>() {
            @Override
            public void onRetrieveData(List<String> data) {
                //Set up a list of storage tasks to listen for when it's complete
                List<Task<Void>> addPlanTasks = new ArrayList<>();

                //Then go through each user, adding a plan to each one.
                for (String userId : data) {
                    addPlanTasks.add(addPlanToUser(plan.getPlanId(), userId));
                }

                //Listens for when the plan is stored on all users (or fails)
                Tasks.whenAll(addPlanTasks).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onStoreData(plan);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onStoreDataFail(e);
                    }
                });
            }

            @Override
            public void onRetrieveDataFail(Exception e) { callback.onStoreDataFail(e); }
        });
    }

    /**
     * Stores a single plan on the user
     * @param planId
     * @param userId
     * @return
     */
    private Task<Void> addPlanToUser(String planId, String userId) {
        DatabaseReference userPlansRef = mDatabaseInstance.getReference("users/" + userId + "/squadPlans/" + planId);
        return userPlansRef.setValue(true);
    }

    /**
     * Retrieves a list of a user's plans (no filtering of data applied)
     * @param userId
     * @param callback
     */
    public void retrieveUserPlanList(String userId, final RetrieveDataRequest<Plan> callback) {
        DatabaseReference userPlansRef = mDatabaseInstance.getReference("users/" + userId + "/userSquadPlans");
        userPlansRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Set up generic type to properly marshal FireBase list of plan IDs
                GenericTypeIndicator<HashMap<String, Boolean>> stringList
                        = new GenericTypeIndicator<HashMap<String, Boolean>>() {};
                //Retrieve plans from snapshot
                //List<Task<Plan>> retrievePlanTasks = new ArrayList<>();
                HashMap<String, Boolean> squadPlans = dataSnapshot.getValue(stringList);
                for (String planId : ListHelper.toList(squadPlans)) {
                    retrievePlan(planId, callback);
                }
                //callback.onRetrieveData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //TODO - replace other instances of retrievePlan with this one
    /**
     * Retrieves a given plan as a retrieval Task to complete
     * @param planId The string ID of the plan to retrieve
     * @return A task that will be completed when the Plan is retrieved successfully
     */
    public Task<Plan> retrievePlanAsTask(String planId) {
        final TaskCompletionSource<Plan> dbSource = new TaskCompletionSource<>();
        Task dbTask = dbSource.getTask();

        DatabaseReference planRef = mPlansData.child(planId);
        planRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dbSource.setResult(dataSnapshot.getValue(Plan.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dbSource.setException(databaseError.toException());
            }
        });

        return dbTask;
    }

    /**
     * Retrieves a plan from the Firebase database
     * @param planId The ID of the specific plan to retrieve
     * @param callback The callback to refer back to when operation complete
     */
    public void retrievePlan(String planId, final RetrieveDataRequest<Plan> callback) {
        DatabaseReference planRef = mPlansData.child(planId);

        planRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
     * Retrieves a given list of adventure plans from the online database
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
