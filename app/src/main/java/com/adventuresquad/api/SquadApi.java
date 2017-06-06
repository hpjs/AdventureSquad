package com.adventuresquad.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.adventuresquad.model.Plan;
import com.adventuresquad.model.Squad;
import com.adventuresquad.presenter.interfaces.PersonalSquadApiPresenter;
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
     * Creates a squad with no users
     * @param squad
     * @param callback
     */
    public void createEmptySquad(Squad squad, final SquadApiPresenter callback) {
        DatabaseReference mNewSquadRef = mSquadsData.push();
        squad.setSquadId(mNewSquadRef.getKey());
        //prepare callback method for when this task is complete
        //Set the actual data
        mNewSquadRef.setValue(squad).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onCreateSquad();
                } else {
                    callback.onCreateSquadFail(task.getException());
                }
            }
        });
    }

    /**
     * Creates a new squad in the database for a user
     * @param userId The user that this squad is for
     * @param userApi The user API object to update the user
     * @param callback
     */
    public void createPersonalSquad(final String userId, final UserApi userApi, final PersonalSquadApiPresenter callback) {
        //Set up new squad object with the user ID in it
        Squad newUserSquad = new Squad();
        ArrayList<String> userList = new ArrayList<>();
        userList.add(userId);
        newUserSquad.setSquadUsers(userList);

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
                    try {
                        userApi.updateUserSquad(userId, newSquadId, (UserApiPresenter) callback);
                    } catch(ClassCastException exception) {
                        Log.d(DEBUG_SQUAD_API, "Couldn't cast 'callback' from PersonalSquadApiPresenter to UserApiPresenter!");
                        Log.d(DEBUG_SQUAD_API, "Make sure the presenter implements both interfaces!!");
                    }
                    //TODO - Add 'callback.onCreateSquad()' here??

                } else {
                    callback.onCreatePersonalSquadFail(task.getException());
                }
            }
        });
    }

    /**
     * Adds a given plan to a Squad (the one that is referred to in the plan)
     * @param plan The plan (with populated IDs)
     */
    public void addPlanToSquad(Plan plan, final SquadApiPresenter callback) {
        //Get IDs as necessary
        final String squadId = plan.getSquadId();
        final String newPlanId = plan.getPlanId();
        //Get the plan list
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
