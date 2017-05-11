package com.adventuresquad.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.activity.Register1Activity;
import com.adventuresquad.api.AuthApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Harrison on 11/05/2017.
 */

public class Register1Presenter {
    private Register1Activity mActivity;

    public Register1Presenter(Register1Activity activity) {
        mActivity = activity;
    }

    public void register(String email, String pass1, String pass2) {
        mActivity.showLoadingIcon();

        //Check that passwords match
        if (!pass1.equals(pass2)) {
            //Pass did not match
            mActivity.hideLoadingIcon();
            mActivity.showToastMessage(R.string.registration_password_mismatch);
            return;
        }

        //Actually create the account
        AuthApi.registerUser(email, pass1, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.d(AuthApi.DEBUG_LOGIN, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            mActivity.showToastMessage(R.string.registration_auth_failed);
                            //use exception details here:
                            Log.w(AuthApi.DEBUG_LOGIN, task.getException());
                        }
                        else {
                            mActivity.goToNextPage();
                        }
                    }
                });
    }
}
