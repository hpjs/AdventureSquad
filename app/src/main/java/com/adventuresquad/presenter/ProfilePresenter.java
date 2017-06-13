package com.adventuresquad.presenter;

import com.adventuresquad.api.AuthApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.api.interfaces.ActionRequest;
import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.interfaces.PresentableLogoutView;
import com.adventuresquad.model.User;

/**
 * Presenter for the ProfileActivity class
 * Manages interactions between the Profile activity and the APIs, as well as business logic
 * Created by Harrison on 30/05/2017.
 */
public class ProfilePresenter {
    private PresentableLogoutView mView;
    private AuthApi mAuthApi;
    private UserApi mUserApi;
    private User mCurrentUser;

    public ProfilePresenter(PresentableLogoutView view, AuthApi authApi, UserApi userApi) {
        mAuthApi = authApi;
        mUserApi = userApi;
        mView = view;
        //TODO - check if initialiseAuthService is really needed
        authApi.initialiseAuthService();

    }

    public void logout() {
        //Call the relevant API methods and do other stuff that needs to happen to log out

        mAuthApi.signOut(new ActionRequest() {
            @Override
            public void onActionComplete() {
                mView.completeLogout();
            }

            @Override
            public void onActionFail(Exception e) {
                mView.displayMessage(e.toString());
            }
        });
    }

    /**
     * Starts to retrieve the current user for 'my profile'
     */
    public void retrieveCurrentUser() {
        mView.showLoadingIcon();
        mUserApi.retrieveCurrentUser(new RetrieveDataRequest<User>() {
            @Override
            public void onRetrieveData(User data) {
                mCurrentUser = data;
                mView.hideLoadingIcon();
                mView.displayItem(data);
            }

            @Override
            public void onRetrieveDataFail(Exception e) {
                mView.displayMessage("Could not retrieve profile.");
            }
        });
    }
}
