package com.adventuresquad.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.adventuresquad.presenter.LoginApiPresenter;
import com.adventuresquad.presenter.RegisterApiPresenter;
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
    private FirebaseUser mCurrentUser;
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
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                setCurrentUser(firebaseAuth.getCurrentUser());
                if (getCurrentUser() != null) {
                    // User is signed in
                    Log.d(DEBUG_AUTH, "onAuthStateChanged:signed_in:" + getCurrentUser().getUid());
                } else {
                    // User is signed out
                    Log.d(DEBUG_AUTH, "onAuthStateChanged:signed_out");
                }
                // ...
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
     * @param email
     * @param password
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
                            callback.onRegisterSuccess();
                        } else {
                            callback.onRegisterFail();
                        }
                    }
                });
    }

    /* HS 10/05/2017 register currently throws this erro:
    05-10 23:45:53.302 24710-24710/com.adventuresquad D/login: Attempting to register user
05-10 23:45:57.767 24710-24710/com.adventuresquad E/AndroidRuntime: FATAL EXCEPTION: main
                                                                    Process: com.adventuresquad, PID: 24710
                                                                    java.lang.NullPointerException: Attempt to invoke virtual method 'com.google.android.gms.tasks.Task com.google.firebase.auth.FirebaseAuth.createUserWithEmailAndPassword(java.lang.String, java.lang.String)' on a null object reference
                                                                        at com.adventuresquad.api.AuthApi.registerUser(AuthApi.java:70)
                                                                        at com.adventuresquad.Activity.Register1Activity.register(Register1Activity.java:55)
                                                                        at com.adventuresquad.Activity.Register1Activity.onClick(Register1Activity.java:47)
                                                                        at android.view.View.performClick(View.java:5226)
                                                                        at android.view.View$PerformClick.run(View.java:21266)
                                                                        at android.os.Handler.handleCallback(Handler.java:739)
                                                                        at android.os.Handler.dispatchMessage(Handler.java:95)
                                                                        at android.os.Looper.loop(Looper.java:168)
                                                                        at android.app.ActivityThread.main(ActivityThread.java:5845)
                                                                        at java.lang.reflect.Method.invoke(Native Method)
                                                                        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:797)
                                                                        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:687)


     */

    /**
     * Checks if the user is logged in or not (i.e. getting current user is null or not)
     * @return
     */
    public boolean checkUserLoggedIn() {
        try {
            setCurrentUser(mAuth.getCurrentUser());
            return true;
        } catch (Exception e) {
            //Couldn't get currently logged in user
        }
        return false;
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
                            callback.onLoginSuccess();
                        } else {
                            Log.d(DEBUG_AUTH,"signInWithEmail:failed", task.getException());
                            callback.onLoginFail();
                        }
                    }
                });
    }

    /**
     * Handles user creation with database
     */
    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public FirebaseUser getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(FirebaseUser mCurrentUser) {
        mCurrentUser = mCurrentUser;
    }
}