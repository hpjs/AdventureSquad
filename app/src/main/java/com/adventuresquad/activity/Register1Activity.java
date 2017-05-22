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
import com.adventuresquad.interfaces.PresentableRegisterActivity;
import com.adventuresquad.presenter.RegisterPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Register1Activity extends AppCompatActivity implements PresentableRegisterActivity {

    //UI objects
    @BindView(R.id.register_one_edit_email)
    EditText mEditEmail;
    @BindView(R.id.register_one_edit_password)
    EditText mEditPassword;
    @BindView(R.id.register_one_edit_password2)
    EditText mEditPassword2;
    @BindView(R.id.register_one_button_next)
    Button mButtonNext;

    //DefaultPresenter
    private RegisterPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_1);

        ButterKnife.bind(this);

        //Create a new presenter object for this layout
        mPresenter = new RegisterPresenter(this, new AuthApi());
    }


    @OnClick(R.id.register_one_button_next)
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
    public void showToastMessage(String message) {
        Toast.makeText(Register1Activity.this, message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRegisterFail() {
        showToastMessage("registration failed");
    }

    @Override
    public void validationFail() {
        //TODO - implement error text display
        //getString(R.id.registration_password_mismatch)
        showToastMessage("validation failed");
    }

    @Override
    public void displayError(String errorMessage) {

    }
}
