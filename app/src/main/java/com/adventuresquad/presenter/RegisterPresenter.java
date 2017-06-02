package com.adventuresquad.presenter;

import com.adventuresquad.api.AuthApi;
import com.adventuresquad.interfaces.PresentableRegisterView;
import com.adventuresquad.presenter.interfaces.RegisterApiPresenter;

/**
 * Created by Harrison on 11/05/2017.
 */
public class RegisterPresenter implements RegisterApiPresenter {
    private PresentableRegisterView mActivity;
    private AuthApi mApi;

    public RegisterPresenter(PresentableRegisterView activity, AuthApi api) {
        mActivity = activity;
        mApi = api;
    }

    public void register(String email, String pass1, String pass2) {
        //Check that passwords match
        if (!pass1.equals(pass2)) {
            //Pass did not match
            mActivity.validationFail();
            return;
        }

        //Actually create the account
        mApi.registerUser(email, pass1, this);
    }

    @Override
    public void onRegisterSuccess() {
        mActivity.onRegisterSuccess();
    }

    @Override
    public void onRegisterFail() {
        mActivity.displayMessage("Registration failed");
    }
}
