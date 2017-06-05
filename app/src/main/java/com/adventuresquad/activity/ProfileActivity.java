package com.adventuresquad.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.plan_toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        mPresenter = new ProfilePresenter(this, new AuthApi(), new UserApi());

        initialiseNavbar();

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

    /**
     * Shows a given string resource as a toast
     */
    public void showToastMessage(String string) {
        Toast.makeText(this, string,
                Toast.LENGTH_SHORT).show();
    }

    //region Navbar code
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
                                navigateAddAdventure();
                                break;
                            case R.id.navigation_squads:
                                navigateSquads();
                                break;
                            case R.id.navigation_home:
                                navigateHome();
                                break;
                            case R.id.navigation_myTrips:
                                navigateMyTrips();
                                break;
                            case R.id.navigation_profile:
                                //navigateProfile();
                                break;
                        }
                        return true;
                    }
                });
    }

    /**
     * Navigate to the add adventure page
     */
    public void navigateAddAdventure() {
        Intent intent = new Intent(this, AddAdventureActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Navigate to the add adventure page
     */
    public void navigateSquads() {
        Intent intent = new Intent(this, SquadsActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Navigate to the profile page
     */
    public void navigateHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Navigate to the profile page
     */
    public void navigateMyTrips() {
        Intent intent = new Intent(this, MyTripsActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Navigate to the profile page
     */
    public void navigateProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
    //endregion
}
