package com.adventuresquad.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.api.AuthApi;
import com.adventuresquad.interfaces.PresentableAuthActivity;
import com.adventuresquad.presenter.AuthPresenter;

/**
 * Activity for logging in to the application, or going to the registration page
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, PresentableAuthActivity {

    //UI items
    //TODO - rebind using Butterknife
    private EditText mEditEmail;
    private EditText mEditPassword;
    private Button mLoginButton;
    private Button mRegisterButton;

    //DefaultPresenter
    private AuthPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Set up UI code.
        mEditEmail = (EditText)findViewById(R.id.register_one_edit_email);
        mEditPassword = (EditText)findViewById(R.id.register_one_edit_password);
        mLoginButton = (Button)findViewById(R.id.login_button_login);
        mRegisterButton = (Button)findViewById(R.id.login_button_register);

        //Set on click listener for UI objects
        mLoginButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);

        //Set up presenter
        mPresenter = new AuthPresenter(this, new AuthApi());
    }

    /**
     * Handles any onClick events on this activity
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_login :
                mPresenter.login(mEditEmail.getText().toString(), mEditPassword.getText().toString());
                break;

            case R.id.login_button_register:
                //Start registration process
                mPresenter.startRegistration();
                break;
        }
    }

    /**
     * Starts goToRegister Activity
     */
    public void goToRegister() {
        Intent startRegisterActivity = new Intent();
        startRegisterActivity.setClass(getApplicationContext(), Register1Activity.class);
        startActivity(startRegisterActivity);
    }

    /**
     * Shows the loading icon, hides rest of the UI
     */
    public void showLoadingIcon() {

    }

    /**
     * Hides the screen-wide loading icon, shows the rest of the view
     */
    public void hideLoadingIcon() {

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
        //Go to main page, put any necessary extras
        Intent intentMainActivity = new Intent(this, MainActivity.class);
        startActivity(intentMainActivity);
    }

    @Override
    public void onLoginFail() {

    }

    @Override
    public void displayError(String errorMessage) {
        showToastMessage(errorMessage);
    }
}
