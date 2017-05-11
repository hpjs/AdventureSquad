package com.adventuresquad.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adventuresquad.MainActivity;
import com.adventuresquad.R;
import com.adventuresquad.api.AuthApi;
import com.adventuresquad.presenter.LoginPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Activity for logging in to the application, or going to the registration page
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //TODO - remove these fields once confirmed not needed
    //private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    //private FirebaseUser mCurrentUser;

    //UI items
    private EditText mEditEmail;
    private EditText mEditPassword;
    private Button mLoginButton;
    private Button mRegisterButton;

    //Presenter
    private LoginPresenter mPresenter;

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
        mPresenter = new LoginPresenter(this);

        //TODO - check that this is the right place to do this, or if there is a better way to do it
    }

    /**
     * Handles any onClick events on this activity
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_login :
                mPresenter.login();
                break;

            case R.id.login_button_register:
                //Start registration process
                goToRegister();
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
    public void showToastMessage(int stringResourceId) {
        Toast.makeText(this, stringResourceId,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Performs actions for login success
     */
    public void loginSuccess() {
        //Go to main page, put any necessary extras
        Intent intentMainActivity = new Intent(this, MainActivity.class);
        startActivity(intentMainActivity);
    }


}
