package com.adventuresquad.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.adventuresquad.model.Plan;
import com.adventuresquad.model.Squad;
import com.adventuresquad.presenter.interfaces.PersonalSquadApiPresenter;
import com.adventuresquad.presenter.interfaces.PlanApiPresenter;
import com.adventuresquad.presenter.interfaces.SquadApiPresenter;
import com.adventuresquad.presenter.interfaces.UserApiPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
     * Adds the plan to the plan's squad id
     * @param plan The plan (with populated IDs)
     */
    public void addPlanToSquad(Plan plan, SquadApiPresenter callback) {
        //if (plan.getSquadId() != null && !plan.getSquadId().isEmpty()) {        }
        //TODO - not sure how to add to an existing list of objects - use push() ?
        callback.onAddPlanToSquad();
    }
}
