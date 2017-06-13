package com.adventuresquad.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.api.AuthApi;
import com.adventuresquad.interfaces.PresentableLoginView;
import com.adventuresquad.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity for logging in to the application, or going to the registration page
 */
public class LoginActivity extends AppCompatActivity implements PresentableLoginView {

    //UI items
    @BindView(R.id.login_edit_email)
    EditText mEditEmail;
    @BindView(R.id.login_edit_password)
    EditText mEditPassword;
    @BindView(R.id.login_button_login)
    Button mLoginButton;
    @BindView(R.id.login_button_register)
    Button mRegisterButton;

    //DefaultPresenter
    private LoginPresenter mPresenter;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Set up UI code.
        ButterKnife.bind(this);

        //Set up mPresenter
        AuthApi api = new AuthApi();
        mPresenter = new LoginPresenter(this, api);
    }

    /**
     * Handles any onClick events on this activity
     * @param v
     */
    @OnClick({R.id.login_button_login, R.id.login_button_register})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_login :
                mPresenter.login(mEditEmail.getText().toString(), mEditPassword.getText().toString());
                break;

            case R.id.login_button_register:
                //Start registration process
                startRegistration();
                break;
        }
    }

    /**
     * Starts startRegistration Activity
     */
    public void startRegistration() {
        Intent startRegisterActivity = new Intent();
        startRegisterActivity.setClass(getApplicationContext(), Register1Activity.class);
        startActivity(startRegisterActivity);
    }

    /**
     * Shows the loading icon, hides rest of the UI
     */
    @Override
    public void showLoadingIcon() {
        mProgressDialog = ProgressDialog.show(this, "",
                getResources().getString(R.string.login_loading), true);
    }

    /**
     * Hides the screen-wide loading icon, shows the rest of the view
     */
    @Override
    public void hideLoadingIcon() {
        mProgressDialog.hide();
    }

    /**
     * Shows a given string resource as a toast
     */
    public void showToastMessage(String text) {
        Toast.makeText(this, text,
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onLoginSuccess() {
        showToastMessage("Login successful");
        //Go to main page, put any necessary extras
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFail() {
        showToastMessage(getString(R.string.login_auth_failed));
    }

    @Override
    public void displayMessage(String errorMessage) {
        showToastMessage(errorMessage);
    }
}
