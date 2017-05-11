package com.adventuresquad.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.presenter.Register1Presenter;

public class Register1Activity extends AppCompatActivity implements View.OnClickListener {

    //UI objects
    private EditText mEditEmail;
    private EditText mEditPassword;
    private EditText mEditPassword2;
    private Button mButtonNext;

    //DefaultPresenter
    private Register1Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_1);

        //Set up the layout
        mEditEmail = (EditText)findViewById(R.id.register_one_edit_email);
        mEditPassword = (EditText)findViewById(R.id.register_one_edit_password);
        mEditPassword2 = (EditText)findViewById(R.id.register_one_edit_password2);
        mButtonNext = (Button)findViewById(R.id.register_one_button_next);

        //Set listeners
        mButtonNext.setOnClickListener(this);

        //Create a new presenter object for this layout
        mPresenter = new Register1Presenter(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_one_button_next:
                mPresenter.register(
                        mEditEmail.getText().toString(),
                        mEditPassword.getText().toString(),
                        mEditPassword2.getText().toString() );
                break;
        }
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
        Toast.makeText(Register1Activity.this, stringResourceId,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows an error message under the login section
     */
    public void showErrorMessage(String message) {
        //TODO - fill in this method
    }

    /**
     * Progresses to the next registration page
     */
    public void goToNextPage() {

    }
}
