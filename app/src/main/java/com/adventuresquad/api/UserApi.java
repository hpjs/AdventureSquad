package com.adventuresquad.api;

import android.support.annotation.NonNull;

import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.api.interfaces.StoreDataRequest;
import com.adventuresquad.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * User API to retrieve and store user info on FireBase.
 * Created by Harrison on 3/06/2017.
 */
public class UserApi {
    private static final String USERS_LIST = "users";

    private FirebaseDatabase mDatabaseInstance;
    private DatabaseReference mUsersData;

    public UserApi() {
        mDatabaseInstance = FirebaseDatabase.getInstance();
        mUsersData = mDatabaseInstance.getReference(USERS_LIST);
    }

    /**
     * Adds the given user to the database, using the user's ID as a reference
     * @param user User object (WITH UID POPULATED)
     * @param callback The object to return to when complete
     */
    public void addUser(final User user, final StoreDataRequest<User> callback) {
        //Confirm that user id is not null / empty
        String userId = user.getUserId();
        if (userId != null && userId.isEmpty()) {
            callback.onStoreDataFail(new Exception("User's userId cannot be empty."));
            return;
        }

        DatabaseReference mNewUserRef = mUsersData.child(user.getUserId());
        mNewUserRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onStoreData(user);
                } else {
                    callback.onStoreDataFail(task.getException());
                }
            }
        });
    }

    /**
     * Retrieves the current user by first getting the user's id,
     * then retrieving user object from the database
     * @param callback The presenter to return to on completion
     */
    public void retrieveCurrentUser(final RetrieveDataRequest<User> callback) {
        //Retrieves the current user from firebase auth
        try {
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            retrieveUser(currentUserId, callback);
        } catch (NullPointerException exception) {
            callback.onRetrieveDataFail(exception);
        }
    }

    /**
     * Retrieves a single user from the database
     * @param userId The ID of the user to retrieve
     * @param callback The object to notify when user is successfully retrieved
     */
    private void retrieveUser(String userId, final RetrieveDataRequest<User> callback) {
        //Uses the user's id to retrieve a specific user
        DatabaseReference mUserRef = mUsersData.child(userId);

        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Marshall adventure into an adventure object
                User retrievedUser = dataSnapshot.getValue(User.class);
                callback.onRetrieveData(retrievedUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onRetrieveDataFail(databaseError.toException());
            }
        });
    }

    /**
     * Updates the ID of a user's personal squad (for personal adventure plans)
     * @param userId The user object to update
     * @param newSquadId The new personal squad object for the user
     */
    void updateUserSquad(final String userId, String newSquadId, final StoreDataRequest<String> callback) {
        DatabaseReference userRef = mUsersData.child(userId + "/userSquadId");
        userRef.setValue(newSquadId).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onStoreData(userId);
                } else {
                    callback.onStoreDataFail(task.getException());
                }
            }
        });
    }

    /**
     * Posts a squad list to a user object in the database
     *
     */
    private void updateUserSquadList(String userId, final List<String> userSquadList, final StoreDataRequest<List<String>> callback) {
        DatabaseReference squadListRef = mUsersData.child(userId + "/userSquadList");
        //Does the storing
        squadListRef.setValue(userSquadList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onStoreData(userSquadList);
                } else {
                    callback.onStoreDataFail(task.getException());
                }
            }
        });
    }

    /**
     * Adds a single squad to a user object
     * @param squadId The squad to add to the user
     * @param userId The user that the squad is to be added to
     * @param callback The class that will be notified on completion
     */
    void addSquadToUser(final String squadId, final String userId, final StoreDataRequest<String> callback) {
        //Adds a squad ID to the user's list of squads.
        DatabaseReference userSquadRef = mUsersData.child(userId + "/userSquadList/" + squadId);
        //
        userSquadRef.setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onStoreData(squadId);
                } else {
                    callback.onStoreDataFail(task.getException());
                }
            }
        }); //The squad is now stored on that user.

    }
}
