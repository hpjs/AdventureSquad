package com.adventuresquad.api;

import android.support.annotation.NonNull;

import com.adventuresquad.api.interfaces.StoreDataRequest;
import com.adventuresquad.model.User;
import com.adventuresquad.presenter.interfaces.UserApiPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * User API
 * Created by Harrison on 3/06/2017.
 */
public class UserApi {
    public static final String USERS_LIST = "users";

    private FirebaseDatabase mDatabaseInstance;
    private DatabaseReference mUsersData;

    public UserApi() {
        mDatabaseInstance = FirebaseDatabase.getInstance();
        mUsersData = mDatabaseInstance.getReference(USERS_LIST);
    }

    /**
     * Adds the given user to the database, using the user's ID as a reference
     * @param user
     * @param callback
     */
    public void addUser(User user, final UserApiPresenter callback) {
        //Confirm that user id is not null / empty
        String userId = user.getUserId();
        if (userId != null && userId.isEmpty()) {
            callback.onAddUserFail(new Exception("User's userId cannot be empty."));
            return;
        }

        DatabaseReference mNewUserRef = mUsersData.child(user.getUserId());

        //This will use different methods to set the data, as we don't want a unique UUID created here
        //Instead want to get the UUID from the user object
        mNewUserRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onAddUser();
                } else {
                    callback.onAddUserFail(task.getException());
                }
            }
        });
    }

    /**
     * Provides a simple interface to retrieve a single user and callback
     * TODO - move this interface out into it's own file
     */
    public interface RetrieveUserListener {

        public void onGetUser(User user);
        public void onGetUserFail(Exception e);
    }
    public void retrieveUser(String userId, final RetrieveUserListener callback) {
        //Uses the user's id to retrieve a specific user
        DatabaseReference mUserRef = mUsersData.child(userId);

        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Marshall adventure into an adventure object
                User retrievedUser = dataSnapshot.getValue(User.class);
                callback.onGetUser(retrievedUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onGetUserFail(databaseError.toException());
            }
        });
    }

    /**
     * Retrieves the current user by first getting the user's id,
     * then retrieving user object from the database
     * @param callback The presenter to return to on completion
     */
    public void retrieveCurrentUser(final UserApiPresenter callback) {
        //Retrieves the current user from firebase auth
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        retrieveUser(currentUserId, new RetrieveUserListener() {
            @Override
            public void onGetUser(User user) {
                callback.onRetrieveCurrentUser(user);
            }

            @Override
            public void onGetUserFail(Exception e) {
                callback.onRetrieveCurrentUserFail(e);
            }
        });
    }

    /**
     * Returns the current user to the callback
     * @param callback The user listener to notify when complete
     */
    public void retrieveCurrentUser(RetrieveUserListener callback) {
        //Retrieves the current user from firebase auth
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        retrieveUser(currentUserId, callback);
    }

    /**
     * Updates the ID of the squad on a given user object
     * @param userId
     * @param newSquadId
     */
    public void updateUserSquad(String userId, String newSquadId, final UserApiPresenter callback) {
        //TODO - debug and check if this actually gets/sets the correct value
        DatabaseReference userRef = mUsersData.child(userId + "/userSquadId");

        userRef.setValue(newSquadId).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onUpdateUserSquad();
                } else {
                    callback.onUpdateUserSquadFail();
                }
            }
        });
    }

    /**
     * Updates the list of a user's squads
     *
     */
    public void updateUserSquadList(String userId, final List<String> userSquadList, final StoreDataRequest<List<String>> callback) {
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


    public void addSquadToUser(final String squadId, final String userId, final StoreDataRequest<String> callback) {
        //Adds a squad ID to the user's list of squads.
        //TODO - finish this to add a user to the squad
        DatabaseReference userSquadsRef = mUsersData.child(userId + "/userSquadList");

        //Retreive the user's current list of squads, then add to it and push back the updated list
        //TODO - this isn't very thread / application safe. Consider a way to just push to the list without downloading the list first.
        userSquadsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //List of squads retrieved, add to the list and store it again
                try { //Try to cast data to list
                    List<String> userSquadList = (ArrayList<String>) dataSnapshot.getValue();

                    if (userSquadList == null) {
                        //List doesn't exist, have to make it first
                        userSquadList = new ArrayList<String>();
                    }
                    userSquadList.add(squadId);
                    //Store the updated list
                    updateUserSquadList(userId, userSquadList, new StoreDataRequest<List<String>>() {
                        @Override
                        public void onStoreData(List<String> data) {
                            callback.onStoreData(squadId);
                        }

                        @Override
                        public void onStoreDataFail(Exception e) {
                            callback.onStoreDataFail(e);
                        }
                    });
                }
                catch(ClassCastException exception) {
                    callback.onStoreDataFail(exception);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Failed to retrieve existing list of User ID,
            }
        });

    }
}
