package com.adventuresquad.presenter;

import com.adventuresquad.api.AuthApi;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.api.interfaces.StoreDataRequest;
import com.adventuresquad.interfaces.PresentableRegisterView;
import com.adventuresquad.model.User;

/**
 * Presenter for registration. Handles user auth creation, and other additional user info.
 * Created by Harrison on 11/05/2017.
 */
public class RegisterPresenter {
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

    public void register(String fullName, String email, String pass1, String pass2) {
        mActivity.showLoadingIcon();
        mUser = new User();

        //Validate name

        if (stringValid(fullName)) {
            mUser.setUserName(fullName);
        } else {
            mActivity.validationFail(PresentableRegisterView.ValidationError.NO_NAME);
            return;
        }

        //Validate email
        if (stringValid(email)) {
            mUser.setUserEmail(email);
        } else {
            mActivity.validationFail(PresentableRegisterView.ValidationError.NO_EMAIL);
            return;
        }

        //Validate passwords
        if (!pass1.equals(pass2)) {
            //Pass did not match
            mActivity.validationFail(PresentableRegisterView.ValidationError.PASSWORD_MISMATCH);
            return;
        }

        //Actually create the account with auth api
        mAuthApi.registerUser(email, pass1, new StoreDataRequest<String>() {
            @Override
            public void onStoreData(String data) {
                //Account created, add user object under database
                addUser(data);
            }

            @Override
            public void onStoreDataFail(Exception e) {
                mActivity.hideLoadingIcon();
                mUser = null;
                mActivity.validationFail(PresentableRegisterView.ValidationError.REGISTER_FAIL);
            }
        });
    }

    /**
     * Checks if a string is not empty/null
     * @param fullName
     * @return
     */
    private boolean stringValid(String string) {
        return string != null && !string.isEmpty();
    }

    /**
     * Adds a single user to the database using their auth user UID
     */
    private void addUser(String userId) {
        //Update user object with new details
        mUser.setUserId(userId);
        //Add the user object to the database
        mUserApi.addUser(mUser, new StoreDataRequest<User>() {
            @Override
            public void onStoreData(User data) {
                createUserPersonalSquad();
            }

            @Override
            public void onStoreDataFail(Exception e) {
                mActivity.hideLoadingIcon();
                mActivity.validationFail(PresentableRegisterView.ValidationError.REGISTER_FAIL);
                mUser = null;
            }
        });
    }

    private void createUserPersonalSquad() {
        //User adding to database complete.
        //Add personal squad to this user so they can make plans
        mSquadApi.createPersonalSquad(mUser.getUserId(), mUserApi, new StoreDataRequest<String>() {
            @Override
            public void onStoreData(String data) { //Store success, holds the userId that was stored to
                mActivity.hideLoadingIcon();
                mActivity.registrationComplete();
            }

            @Override
            public void onStoreDataFail(Exception e) {
                mUser = null;
                mActivity.validationFail(PresentableRegisterView.ValidationError.REGISTER_FAIL);
            }
        });
    }
}
