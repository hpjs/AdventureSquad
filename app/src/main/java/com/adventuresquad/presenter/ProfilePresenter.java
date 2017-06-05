package com.adventuresquad.presenter;

import com.adventuresquad.R;
import com.adventuresquad.api.AuthApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.interfaces.PresentableProfileView;
import com.adventuresquad.model.User;
import com.adventuresquad.presenter.interfaces.LogoutApiPresenter;
import com.adventuresquad.presenter.interfaces.UserApiPresenter;

/**
 * Presenter for the ProfileActivity class
 * Manages interactions between the Profile activity and the APIs, as well as business logic
 * Created by Harrison on 30/05/2017.
 */
public class ProfilePresenter implements LogoutApiPresenter, UserApiPresenter {
    private PresentableProfileView mActivity;
    private AuthApi mAuthApi;
    private UserApi mUserApi;
    private User mCurrentUser;

    public ProfilePresenter(PresentableProfileView activity, AuthApi authApi, UserApi userApi) {
        mAuthApi = authApi;
        mUserApi = userApi;
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

    /**
     * Starts to retrieve the current user for 'my profile'
     */
    public void retrieveCurrentUser() {
        mUserApi.retrieveCurrentUser(this);
    }

    /**
     * User retrieved, notify activity
     * @param user
     */
    @Override
    public void onRetrieveCurrentUser(User user) {
        mCurrentUser = user;
        mActivity.displayProfile(user);
    }

    /**
     * Current user retrieval failed
     * @param e
     */
    @Override
    public void onRetrieveCurrentUserFail(Exception e) {
        mActivity.displayMessage("Could not retrieve profile.");
    }

    //region Unused UserApi methods

    @Override
    public void onAddUser() {

    }

    @Override
    public void onAddUserFail(Exception e) {

    }

    @Override
    public void onUpdateUserSquad() {

    }

    @Override
    public void onUpdateUserSquadFail() {

    }
    //endregion
}
