package com.adventuresquad.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.api.interfaces.StoreDataRequest;
import com.adventuresquad.model.Plan;
import com.adventuresquad.model.Squad;
import com.adventuresquad.model.User;
import com.adventuresquad.presenter.interfaces.SquadApiPresenter;
import com.adventuresquad.presenter.interfaces.UserApiPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows access to FireBase operations regarding squads
 * Created by Harrison on 2/06/2017.
 */
public class SquadApi {
    public static final String PLANS_LIST = "squads";
    public static final String DEBUG_SQUAD_API = "squadapi";

    private FirebaseDatabase mDatabaseInstance;
    private DatabaseReference mSquadsData;

    public SquadApi() {
        mDatabaseInstance = FirebaseDatabase.getInstance();
        mSquadsData = mDatabaseInstance.getReference(PLANS_LIST);
    }

    /**
     * Creates a squad and populates with a user, then adds that squad to the user's list of squads
     * @param squad A new squad object (without a user)
     * @param userId The ID of the user that is creating the squad
     * @param userApi The user API object to use to add the squad to a user
     * @param createSquadCallback Object to notify when upload is complete
     */
    public void createSquad(final Squad squad, final String userId, final UserApi userApi, final StoreDataRequest<Squad> createSquadCallback) {
        //Set up new squad in DB
        DatabaseReference mNewSquadRef = mSquadsData.push();
        squad.setSquadId(mNewSquadRef.getKey());

        //Add the given user to the squad
        squad.addSquadUser(userId);

        //prepare callback method for when this task is complete
        //Set the actual data
        mNewSquadRef.setValue(squad).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Was able to create the new squad, now populate User with squad ID
                    userApi.addSquadToUser(squad.getSquadId(), userId, new StoreDataRequest<String>() {
                        @Override
                        public void onStoreData(String data) {
                            //Adding squad to user was successful, notify original caller
                            createSquadCallback.onStoreData(squad);
                        }

                        @Override
                        public void onStoreDataFail(Exception e) {
                            //Couldn't add squad to user's list of squads, notify original caller
                            createSquadCallback.onStoreDataFail(e);
                        }
                    });
                } else { //Couldn't create individual squad.
                    createSquadCallback.onStoreDataFail(task.getException());
                }
            }
        });
    }

    /**
     * Creates a new squad in the database for a user
     * @param userId The user that this squad is for
     * @param userApi The user API object to update the user
     * @param callback The object to call when creation is successful, holds the UserId
     */
    public void createPersonalSquad(final String userId, final UserApi userApi, final StoreDataRequest<String> callback) {
        //Set up new squad object with the user ID in it
        Squad newUserSquad = new Squad();
        newUserSquad.addSquadUser(userId);

        //Push to database, get new unique SquadId
        DatabaseReference newSquadRef = mSquadsData.push();
        final String newSquadId = newSquadRef.getKey();

        //Add UID to squad object before posting to database
        newUserSquad.setSquadId(newSquadId);
        newSquadRef.setValue(newUserSquad).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //then update the user database with the new personal squad
                    userApi.updateUserSquad(userId, newSquadId, callback);
                } else {
                    callback.onStoreDataFail(task.getException());
                }
            }
        });
    }

    /**
     * Adds a given plan to a Squad (the one that is referred to in the plan)
     * TODO - refactor to use new FireBase list & linking structure
     * @param plan The plan (with populated IDs)
     */
    public void addPlanToSquad(Plan plan, final SquadApiPresenter callback) {
        //Get IDs from the plan object
        final String squadId = plan.getSquadId();
        final String newPlanId = plan.getPlanId();

        //Retrieve list of plans from the database
        retrievePlanList(squadId, new RetrieveDataRequest<List<String>>() {

            //Plan list was retrieved successfully, add plan to list and push back to squad
            @Override
            public void onRetrieveData(List<String> planIdList) {
                //Add plan to list and store
                if (planIdList == null) {
                    //List was empty
                    planIdList = new ArrayList<String>();
                }
                planIdList.add(newPlanId);
                storePlanList(squadId, planIdList, new StorePlanListListener() {
                    @Override
                    public void onStorePlanList(List<String> planIdList) {
                        //Updated plan list was complete, finsih up
                        callback.onAddPlanToSquad();
                    }

                    @Override
                    public void onStorePlanListFail(Exception e) {
                        //Fail
                    }
                });
            }

            //List was not successfully pulled, create a new one and push it
            @Override
            public void onRetrieveDataFail(Exception e) {
                List<String> newList = new ArrayList<String>();
                newList.add(newPlanId);
                //Store the new list on that squad
                storePlanList(squadId, newList, new StorePlanListListener() {
                    @Override
                    public void onStorePlanList(List<String> planIdList) {
                        //Updated plan list was complete, finsih up
                        callback.onAddPlanToSquad();
                    }

                    @Override
                    public void onStorePlanListFail(Exception e) {
                        //Fail
                        //TODO - implement fail safe code here
                    }
                });
            }
        });
    }

    /**
     * Gets a list of all the of the plan IDs of a particular squad
     * @param squadId
     */
    public void retrievePlanList(String squadId, final RetrieveDataRequest<List<String>> callback) {
        //Uses the user's id to retrieve a specific user
        DatabaseReference mUserRef = mSquadsData.child(squadId + "/squadPlans");
        //Retrieve the plan list and return to callback when complete
        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Set up data type to read a list of string from Firebase
                GenericTypeIndicator<List<String>> stringList
                        = new GenericTypeIndicator<List<String>>() {};

                List<String> squadPlans = dataSnapshot.getValue(stringList);
                callback.onRetrieveData(squadPlans);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onRetrieveDataFail(databaseError.toException());
            }
        });
    }

    /**
     * Takes a list of IDs and loads a squad for each one (loads out of order currently)
     * @param userSquadList
     * @param callback Returns multiple single squad items as part of it's request.
     */
    public void retrieveSquadList(List<String> userSquadList, RetrieveDataRequest<Squad> callback) {
        for (String squadId : userSquadList) {
            retrieveSquad(squadId, callback);
        }
    }

    /**
     * Retrieves a single squad from the database and notifies the callback
     * @param squadId The squad to load
     * @param callback The object to notify when complete or failed
     */
    public void retrieveSquad(String squadId, final RetrieveDataRequest<Squad> callback) {
        DatabaseReference squadRef = mSquadsData.child(squadId);

        squadRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Marshall adventure into an adventure object
                Squad retrievedSquad = dataSnapshot.getValue(Squad.class);
                callback.onRetrieveData(retrievedSquad);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onRetrieveDataFail(databaseError.toException());
            }
        });
    }

    /**
     * Provides a simple callback interface for storing a list of plan strings
     */
    public interface StorePlanListListener {
        public void onStorePlanList(List<String> planIdList);
        public void onStorePlanListFail(Exception e);
    }

    /**
     *
     * @param squadId
     * @param planIds
     * @param listener
     */
    public void storePlanList(String squadId, final List<String> planIds, final StorePlanListListener listener) {
        DatabaseReference planListRef = mSquadsData.child(squadId + "/squadPlans");

        planListRef.setValue(planIds).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    listener.onStorePlanList(planIds);
                } else {
                    listener.onStorePlanListFail(task.getException());
                }
            }
        });
    }
}
