package com.adventuresquad.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.adventuresquad.R;

/**
 * Helper class to properly initialise the navbar on every page
 * Created by Harrison on 6/06/2017.
 */
public class NavbarHelper {

    //Holds a reference to the activity
    Activity mActivity;
    //Holds the current nav button ID so it won't re-navigate to the same section
    int mCurrentNavButton;

    public NavbarHelper(Activity activity, int currentNavButton){
        mActivity = activity;
        mCurrentNavButton = currentNavButton;
        initialiseNavbar();
    }

    //region NAVBAR CODE
    private void initialiseNavbar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                mActivity.findViewById(R.id.bottom_navigation);
        //Set correct item to be selected
        bottomNavigationView.setSelectedItemId(mCurrentNavButton);
        //Set up click listener
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId != mCurrentNavButton) {
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
                                    navigateProfile();
                                    break;
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * Navigate to the add adventure page
     */
    public void navigateAddAdventure() {
        Intent intent = new Intent(mActivity, AddAdventureActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    /**
     * Navigate to the add adventure page
     */
    public void navigateSquads() {
        Intent intent = new Intent(mActivity, SquadsActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    /**
     * Navigate to the profile page
     */
    public void navigateHome() {
        Intent intent = new Intent(mActivity, MainActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    /**
     * Navigate to the profile page
     */
    public void navigateMyTrips() {
        Intent intent = new Intent(mActivity, MyTripsActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    /**
     * Navigate to the profile page
     */
    public void navigateProfile() {
        Intent intent = new Intent(mActivity, ProfileActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }
    //endregion

}
