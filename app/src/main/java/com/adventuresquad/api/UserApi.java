package com.adventuresquad.api;

import android.support.annotation.NonNull;

import com.adventuresquad.model.Squad;
import com.adventuresquad.model.User;
import com.adventuresquad.presenter.interfaces.SquadApiPresenter;
import com.adventuresquad.presenter.interfaces.UserApiPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
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

    public void addUser(User user, final UserApiPresenter callback) {
        DatabaseReference mNewUserRef = mUsersData.push();
        //This will use different methods to set the data, as we don't want a unique UUID created here
        //Instead want to get the UUID from the user object
        user.setUserId(mNewUserRef.getKey());
        //prepare callback method for when this task is complete
        //Set the actual data
        mNewUserRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
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

}
