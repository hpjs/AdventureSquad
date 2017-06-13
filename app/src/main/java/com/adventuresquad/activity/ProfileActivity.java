package com.adventuresquad.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.api.AuthApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.interfaces.PresentableProfileView;
import com.adventuresquad.model.User;
import com.adventuresquad.presenter.ProfilePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity implements PresentableProfileView {

    ProfilePresenter mPresenter;

    @BindView(R.id.profile_name)
    TextView mProfileName;

    @BindView(R.id.profile_text)
    TextView mProfileText;
    @BindView(R.id.profile_content)
    RelativeLayout mContent;
    @BindView(R.id.profile_progress_bar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.plan_toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        //Create presenter
        mPresenter = new ProfilePresenter(this, new AuthApi(), new UserApi());

        //Set up navbar
        new NavbarHelper(this, R.id.navigation_profile);

        //Kick off data retrieval on presenter
        mPresenter.retrieveCurrentUser();
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
    public void displayProfile(User user) {
        //Put adventure contents into view (like a RecyclerView view bind)
        //Load image
        mProfileName.setText(user.getUserName());
        mProfileText.setText(getString(R.string.profile_no_text));

        //Set map view...

    }

    @Override
    public void displayMessage(String errorMessage) {
        showToastMessage(errorMessage);
    }

    @Override
    public void showLoadingIcon() {
        mContent.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIcon() {
        mProgressBar.setVisibility(View.GONE);
        mContent.setVisibility(View.VISIBLE);
    }

    /**
     * Shows a given string resource as a toast
     */
    public void showToastMessage(String string) {
        Toast.makeText(this, string,
                Toast.LENGTH_SHORT).show();
    }

}
