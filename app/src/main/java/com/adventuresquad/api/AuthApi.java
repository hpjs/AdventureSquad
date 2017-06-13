package com.adventuresquad.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.adventuresquad.api.interfaces.ActionRequest;
import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.api.interfaces.StoreDataRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Harrison on 8/05/2017.
 * API to deal with all authentication and login
 */
public class AuthApi {

    private FirebaseAuth mAuth;
    //private FirebaseUser mCurrentUser; //This is not really needed because you can get current user at any time anyway
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String DEBUG_AUTH = "auth";

    public AuthApi() {
        //initialiseAuthService();
    }

    /**
     * Initialises the AuthApi object with a listener for various state changes, including log-ins
     * NOTE: Possible problem coming here after registration.
     */
    public void initialiseAuthService () {
        mAuth = FirebaseAuth.getInstance();

        //Initialise authlistener
        //NOTE - this would only be needed if there are certain actions
        //that need to be taken from a global point of view within the app
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //setCurrentUser(firebaseAuth.getCurrentUser());
                if (getUser() != null) {
                    // User is signed in
                    Log.d(DEBUG_AUTH, "onAuthStateChanged:signed_in:" + getUser().getUid());
                } else {
                    // User is signed out
                    Log.d(DEBUG_AUTH, "onAuthStateChanged:signed_out");
                }
            }
        };

        mAuth.addAuthStateListener(mAuthListener);
    }

    public void deInitialiseAuthService() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * Registers a given email/password into the firebase auth system.
     * @param email New user's email
     * @param password New user's password
     */
    public void registerUser(String email, String password,
                                    final StoreDataRequest<String> callback) {

        Log.d(AuthApi.DEBUG_AUTH, "Attempting to register user");
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            callback.onStoreData(getUser().getUid());
                        } else {
                            callback.onStoreDataFail(task.getException());
                        }
                    }
                });
    }

    /**
     * Attempts to log a user in using email & password
     */
    public void emailPasswordLogin(String email, String password, final RetrieveDataRequest<String> callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(DEBUG_AUTH,"signInWithEmail:onComplete");
                            callback.onRetrieveData(getUser().getUid());
                        } else {
                            Log.d(DEBUG_AUTH,"signInWithEmail:failed", task.getException());
                            callback.onRetrieveDataFail(task.getException());
                        }
                    }
                });
    }

    public void signOut(ActionRequest callback) {
        mAuth.signOut();
        callback.onActionComplete();
    }

    /**
     * Checks if the user is logged in or not (i.e. getting current user is null or not)
     * @return
     */
    public boolean checkUserLoggedIn() {
        return getUser() != null;
    }

    /**
     * Gets the current Auth user object
     */
    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

}