package com.adventuresquad.presenter;

import com.adventuresquad.activity.LoginActivity;

/**
 * DefaultPresenter class for Login activity'
 * Provides an interface between data and activity logic
 * Created by Harrison on 11/05/2017.
 */
public class LoginPresenter {
    private LoginActivity mActivity;

    public LoginPresenter(LoginActivity activity) {
        mActivity = activity;
    }

    /**
     * Performs a login by taking the email and password from the edit text fields
     */
    public void login(String email, String password) {
        //Perform a small amount of validation if necessary and show loading icon
        mActivity.showLoadingIcon();

        //TODO - Disabled Auth while getting login working

//        AuthApi.emailPasswordLogin(email, password, new OnCompleteListener() {
//            @Override
//            public void onComplete(@NonNull Task task) {
//                //Check if login was valid
//                //Show error message if not valid
//                if (!task.isSuccessful()) {
//                    Log.w(AuthApi.DEBUG_LOGIN, "signInWithEmail:failed", task.getException());
//                    mActivity.showToastMessage(R.string.login_auth_failed);
//                    //TODO - Add UI code to show the error message (show red TextView with error)
//                }
//                else {
                    mActivity.hideLoadingIcon();
                    mActivity.loginSuccess();
//                }
//            }
//        });
    }

    /**
     * Kicks off registration logic if any necessary
     */
    public void startRegistration() {
        //TODO - check if any logic is necessary to go here
        mActivity.goToRegister();
    }
}
