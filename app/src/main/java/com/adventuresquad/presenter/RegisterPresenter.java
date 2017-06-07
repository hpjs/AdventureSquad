package com.adventuresquad.presenter;

import com.adventuresquad.api.AuthApi;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.api.interfaces.StoreDataRequest;
import com.adventuresquad.interfaces.PresentableRegisterView;
import com.adventuresquad.model.User;
import com.adventuresquad.presenter.interfaces.RegisterApiPresenter;
import com.adventuresquad.presenter.interfaces.UserApiPresenter;

/**
 * Presenter for registration. Handles user auth creation, and other additional user info.
 * Created by Harrison on 11/05/2017.
 */
public class RegisterPresenter implements RegisterApiPresenter, UserApiPresenter {
    private PresentableRegisterView mActivity;
    private AuthApi mAuthApi;
    private UserApi mUserApi;
    private SquadApi mSquadApi;
    private User mUser;

    public RegisterPresenter(PresentableRegisterView activity, AuthApi authApi, UserApi userApi, SquadApi squadApi) {
        mActivity = activity;
        mAuthApi = authApi;
        mUserApi = userApi;
        mSquadApi = squadApi;
    }

    public void register(String email, String pass1, String pass2) {
        //Check that passwords match
        if (!pass1.equals(pass2)) {
            //Pass did not match
            mActivity.validationFail();
        } else {
            //Actually create the account
            mAuthApi.registerUser(email, pass1, this);
        }
    }

    @Override
    public void onRegisterSuccess(String userId, String userEmail) {
        //User auth rego was successful, create the rest of the user
        addUser(userId, userEmail);
    }

    @Override
    public void onRegisterFail(Exception exception) {
        mActivity.displayMessage("Registration failed - " + exception.toString());
    }

    /**
     * Adds a single user to the database using their auth user UID
     */
    public void addUser(String userId, String userEmail) {
        //Create a new user object
        mUser = new User();
        mUser.setUserId(userId);
        mUser.setUserEmail(userEmail);
        //Add the user object to the database
        mUserApi.addUser(mUser, this);
    }

    @Override
    public void onAddUser() {
        //User adding to database complete.
        //Add personal squad to this user so they can make plans
        mSquadApi.createPersonalSquad(mUser.getUserId(), mUserApi, new StoreDataRequest<String>() {
            @Override
            public void onStoreData(String data) { //Store success, holds the userId that was stored to
                onUpdateUserSquad();
            }

            @Override
            public void onStoreDataFail(Exception e) {
                mActivity.displayMessage("User creation failed - " + e.toString());
            }
        });
    }

    @Override
    public void onAddUserFail(Exception e) {
        mActivity.displayMessage("User creation failed - " + e.toString());
        //TODO - check if this is the right place to put this
        mUser = null;
    }

    @Override
    public void onUpdateUserSquad() {
        mActivity.registrationComplete();
    }

    @Override
    public void onRetrieveCurrentUser(User user) {

    }

    @Override
    public void onRetrieveCurrentUserFail(Exception e) {

    }

    @Override
    public void onUpdateUserSquadFail() {

    }
}
