package com.adventuresquad.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.activity.LoginActivity;
import com.adventuresquad.api.AuthApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Harrison on 11/05/2017.
 */

/**
 * Presenter class for Login activity'
 * Provides an interface between login api and login activity
 */
public class LoginPresenter {
    private LoginActivity mActivity;

    public LoginPresenter(LoginActivity activity) {
        mActivity = activity;
    }

    /**
     * Performs a login by taking the email and password from the edit text fields
     */
    private void login(String email, String password) {
        //Perform a small amount of validation if necessary and show loading icon

        mActivity.showLoadingIcon();


        AuthApi.emailPasswordLogin(email, password, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                //Check if login was valid
                //Show error message if not valid
                if (!task.isSuccessful()) {
                    Log.w(AuthApi.DEBUG_LOGIN, "signInWithEmail:failed", task.getException());
                    mActivity.showToastMessage(R.string.login_auth_failed);
                    //TODO - Add UI code to show the error message (show red TextView with error)
                }
                else {
                    mActivity.hideLoadingIcon();
                    mActivity.loginSuccess();
                }
            }
        });
    }

}
