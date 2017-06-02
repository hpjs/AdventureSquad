package com.adventuresquad.presenter;

import com.adventuresquad.api.AuthApi;
import com.adventuresquad.interfaces.PresentableProfileView;
import com.adventuresquad.presenter.interfaces.LogoutApiPresenter;

/**
 * Presenter for the ProfileActivity class
 * Manages interactions between the Profile activity and the APIs, as well as business logic
 * Created by Harrison on 30/05/2017.
 */
public class ProfilePresenter implements LogoutApiPresenter {
    private PresentableProfileView mActivity;
    private AuthApi mAuthApi;

    public ProfilePresenter(AuthApi authApi, PresentableProfileView activity) {
        mAuthApi = authApi;
        mActivity = activity;
        //TODO - check if initialiseAuthService is really needed
        authApi.initialiseAuthService();
    }

    @Override
    public void logout() {
        //Call the relevant API methods and do other stuff that needs to happen to log out
        mAuthApi.signOut(this);
    }

    @Override
    public void onLogoutSuccess() {
        mActivity.completeLogout();
    }

    @Override
    public void onLogoutFail(Exception exception) {
        mActivity.displayMessage(exception.toString());
    }

}
