package com.adventuresquad.presenter;

import com.adventuresquad.api.AuthApi;
import com.adventuresquad.interfaces.PresentableLoginView;
import com.adventuresquad.presenter.interfaces.LoginApiPresenter;

/**
 * DefaultPresenter class for Login activity'
 * Provides an interface between data and activity logic
 * Created by Harrison on 11/05/2017.
 */
public class LoginPresenter implements LoginApiPresenter {
    private PresentableLoginView mActivity; //Shouldn't know what the activity is doing
    private AuthApi mApi;

    public LoginPresenter(PresentableLoginView activity, AuthApi api) {
        mActivity = activity;
        mApi = api;
        //TODO - check if initialiseAuthService is really needed
        mApi.initialiseAuthService();
        checkLoggedIn();
    }

    /**
     *
     */
    public void checkLoggedIn() {
        if (mApi.checkUserLoggedIn()) {
            mActivity.onLoginSuccess();
        } else {
            //User was not already logged in, do nothing.
        }
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
