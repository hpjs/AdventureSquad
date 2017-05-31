package com.adventuresquad.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.api.AuthApi;
import com.adventuresquad.interfaces.PresentableProfileActivity;
import com.adventuresquad.presenter.ProfilePresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity implements PresentableProfileActivity {

    ProfilePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        mPresenter = new ProfilePresenter(new AuthApi(), this);
    }

    @OnClick(R.id.profile_fab_logout)
    public void logout() {
        mPresenter.logout();
    }

    @Override
    public void completeLogout() {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
        finish();
    }

    @Override
    public void displayMessage(String errorMessage) {
        showToastMessage(errorMessage);
    }

    /**
     * Shows a given string resource as a toast
     */
    public void showToastMessage(String string) {
        Toast.makeText(this, string,
                Toast.LENGTH_SHORT).show();
    }
}
