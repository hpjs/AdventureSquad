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
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.interfaces.PresentableRegisterView;
import com.adventuresquad.presenter.RegisterPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Register1Activity extends AppCompatActivity implements PresentableRegisterView {

    //UI objects
    @BindView(R.id.register_one_name)
    EditText mEditName;
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
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_1);

        ButterKnife.bind(this);

        //Create a new mPresenter object for this layout
        mPresenter = new RegisterPresenter(this, new AuthApi(), new UserApi(), new SquadApi());
    }


    @OnClick(R.id.register_one_button_next)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_one_button_next:
                mPresenter.register(
                        mEditName.getText().toString(),
                        mEditEmail.getText().toString(),
                        mEditPassword.getText().toString(),
                        mEditPassword2.getText().toString() );
                break;
        }
    }

    /**
     * Shows the loading icon, hides rest of the UI
     */
    @Override
    public void showLoadingIcon() {
        mProgressDialog = ProgressDialog.show(this, "",
                getResources().getString(R.string.register_loading), true);
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
    public void showToastMessage(String message) {
        Toast.makeText(Register1Activity.this, message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registrationComplete() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegisterFail() {
        showToastMessage("registration failed");
    }

    @Override
    public void validationFail(PresentableRegisterView.ValidationError reason) {
        switch (reason) {
            case PASSWORD_MISMATCH:
                displayMessage(getResources().getString(R.string.register_error_password_mismatch));
                break;
            case NO_EMAIL:
                displayMessage(getResources().getString(R.string.register_error_no_email));
                break;
            case NO_NAME:
                displayMessage(getResources().getString(R.string.register_error_no_name));
                break;
            case REGISTER_FAIL:
                displayMessage(getResources().getString(R.string.register_error_fail));
                break;
            default:
                displayMessage(getResources().getString(R.string.register_error_unknown));
                break;
        }
    }

    @Override
    public void displayMessage(String errorMessage) {
        showToastMessage(errorMessage);
    }
}
