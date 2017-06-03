package com.adventuresquad.api;

import android.support.annotation.NonNull;

import com.adventuresquad.model.Squad;
import com.adventuresquad.model.User;
import com.adventuresquad.presenter.interfaces.SquadApiPresenter;
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

    public void retrieveUser(String userId, final UserApiPresenter callbackPresenter) {
        //Uses the user's id to retrieve a specific user
        DatabaseReference mUserRef = mUsersData.child(userId);

        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Marshall adventure into an adventure object
                User retrievedUser = dataSnapshot.getValue(User.class);
                callbackPresenter.onRetrieveCurrentUser(retrievedUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callbackPresenter.onRetrieveCurrentUserFail(databaseError.toException());
            }
        });
    }

    /**
     * Returns the current user to the callback
     * @param callback
     */
    public void retrieveCurrentUser(UserApiPresenter callback) {
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
}
