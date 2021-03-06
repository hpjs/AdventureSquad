package com.adventuresquad.api;

import android.support.annotation.NonNull;

import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.api.interfaces.StoreDataRequest;
import com.adventuresquad.model.Squad;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
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
     * Gets a list of all the of the plan IDs of a particular squad
     * @param squadId
     */
    public void retrievePlanList(String squadId, final RetrieveDataRequest<List<String>> callback) {
        //Uses the user's id to retrieve a specific user
        DatabaseReference userRef = mSquadsData.child(squadId + "/squadPlans");
        //Retrieve the plan list and return to callback when complete
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Set up data type to read a list of string from Firebase
                //This is because it can't marshal straight into a collection properly
                GenericTypeIndicator<HashMap<String, Boolean>> stringList
                        = new GenericTypeIndicator<HashMap<String, Boolean>>() {};
                //Retrieve data from snapshot
                HashMap<String, Boolean> squadPlans = dataSnapshot.getValue(stringList);
                //Convert to list of IDs and start retrieving them
                callback.onRetrieveData(ListHelper.toList(squadPlans));
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
        //List<Task> taskList = new LinkedList<>();
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
     * Retrieves a list of all user Ids that are in a particular squad
     * @param squadId The squad to retrieve users for
     * @param callback Where to return the data to once the retrieve task is complete
     */
    public void retrieveSquadUsers(String squadId, final RetrieveDataRequest<List<String>> callback) {
        //Set up database ref
        DatabaseReference squadRef = mSquadsData.child(squadId + "/squadUsers");
        //Retrieve data from the db
        squadRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Create generic type to more easily retrieve the data
                GenericTypeIndicator<HashMap<String, Boolean>> idList
                        = new GenericTypeIndicator<HashMap<String, Boolean>>() {};

                //Retrieve data from snapshot
                HashMap<String, Boolean> squadUsers = dataSnapshot.getValue(idList);

                //Convert to list of IDs and start retrieving them
                callback.onRetrieveData(ListHelper.toList(squadUsers));
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
