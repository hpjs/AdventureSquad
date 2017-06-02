package com.adventuresquad.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.plan_toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        mPresenter = new ProfilePresenter(new AuthApi(), this);

        initialiseNavbar();
    }

    private void initialiseNavbar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        //Set correct item to be selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        //Set up click listener
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_create:
                                break;
                            case R.id.navigation_squads:
                                break;
                            case R.id.navigation_home:
                                navigateHome();
                                break;
                            case R.id.navigation_myTrips:
                                break;
                            case R.id.navigation_profile:
                                //Do nothing, already here
                                break;
                        }
                        return true;
                    }
                });
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

    /**
     * Navigate to the profile page
     */
    public void navigateHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
