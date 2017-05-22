package com.adventuresquad.presenter;

import com.adventuresquad.api.AuthApi;
import com.adventuresquad.interfaces.PresentableLoginActivity;

/**
 * DefaultPresenter class for Login activity'
 * Provides an interface between data and activity logic
 * Created by Harrison on 11/05/2017.
 */
public class LoginPresenter implements LoginApiPresenter {
    private PresentableLoginActivity mActivity; //Shouldn't know what the activity is doing
    private AuthApi mApi;

    public LoginPresenter(PresentableLoginActivity activity, AuthApi api) {
        mActivity = activity;
        mApi = api;
    }

    /**
     * Performs a login by taking the email and password from the edit text fields
     */
    public void login(String email, String password) {
        //Perform a small amount of validation if necessary and show loading icon
        //TODO - do validation if necessary (check if email format etc)

        if (!email.isEmpty() && !email.isEmpty()) {
            mApi.emailPasswordLogin(this, email, password);
        }
    }

    @Override
    public void onLoginSuccess() {
        mActivity.onLoginSuccess();
    }

    @Override
    public void onLoginFail() {

    }
}
