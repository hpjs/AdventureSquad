package com.adventuresquad.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.adventuresquad.presenter.interfaces.LoginApiPresenter;
import com.adventuresquad.presenter.interfaces.LogoutApiPresenter;
import com.adventuresquad.presenter.interfaces.RegisterApiPresenter;
import com.google.android.gms.auth.api.credentials.PasswordSpecification;
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

    public static final String DEBUG_AUTH = "auth";

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
                                    final RegisterApiPresenter callback) {
        Log.d(AuthApi.DEBUG_AUTH, "Attempting to register user");
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //TODO - return the users' generated UID
                            callback.onRegisterSuccess(getUser().getUid(), getUser().getEmail());
                        } else {
                            callback.onRegisterFail(task.getException());
                        }
                    }
                });
    }

    /**
     * Checks if the user is logged in or not (i.e. getting current user is null or not)
     * @return
     */
    public boolean checkUserLoggedIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            //setCurrentUser(user);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Attempts to log a user in using email & password
     * Should pass in an anonymous inner class with logic for successful/not successful login
     * @return Whether the email/pw were validated (i.e. login was 'started' or not)
     */
    public void emailPasswordLogin(final LoginApiPresenter callback, String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(DEBUG_AUTH,"signInWithEmail:onComplete");
                            callback.onLoginSuccess(getUser().getUid());
                        } else {
                            Log.d(DEBUG_AUTH,"signInWithEmail:failed", task.getException());
                            callback.onLoginFail(task.getException());
                        }
                    }
                });
    }

    public void signOut(LogoutApiPresenter callback) {
        mAuth.signOut();
        callback.onLogoutSuccess();
    }

    /**
     * Handles user creation with database
     */
    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

//    public FirebaseUser getCurrentUser() {
//        return mCurrentUser;
//    }
//
//    public void setCurrentUser(FirebaseUser mCurrentUser) {
//        mCurrentUser = mCurrentUser;
//    }
}