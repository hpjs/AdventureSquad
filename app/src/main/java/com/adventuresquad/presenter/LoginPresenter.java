package com.adventuresquad.presenter;

import com.adventuresquad.api.AuthApi;
import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.interfaces.PresentableLoginView;

/**
 * DefaultPresenter class for Login activity'
 * Provides an interface between data and activity logic
 * Created by Harrison on 11/05/2017.
 */
public class LoginPresenter {
    private PresentableLoginView mView; //Shouldn't know what the activity is doing
    private AuthApi mAuthApi;

    public LoginPresenter(PresentableLoginView view, AuthApi authApi) {
        mView = view;
        mAuthApi = authApi;
        mAuthApi.initialiseAuthService();
        checkLoggedIn();
    }

    /**
     * Checks if the user is logged in - if they are, progress to full app
     */
    private void checkLoggedIn() {
        if (mAuthApi.checkUserLoggedIn()) {
            mView.onLoginSuccess();
        }
    }

    /**
     * Performs a login by taking the email and password from the edit text fields
     */
    public void login(String email, String password) {
        //Perform a small amount of validation if necessary and show loading icon
        mView.showLoadingIcon();
        if (!email.isEmpty() && !email.isEmpty()) {
            mAuthApi.emailPasswordLogin(email, password, new RetrieveDataRequest<String>() {
                @Override
                public void onRetrieveData(String data) {
                    //Logged in successfully
                    mView.onLoginSuccess();
                    mView.hideLoadingIcon();
                }

                @Override
                public void onRetrieveDataFail(Exception e) {
                    mView.displayMessage("Login failed.");
                }
            });
        }
    }
}
