package com.adventuresquad.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.adventuresquad.R;

public class AddAdventureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adventure);


        initialiseNavbar();
    }

    //region Navbar code
    private void initialiseNavbar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        //Set correct item to be selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_create);
        //Set up click listener
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_create:
                                //navigateAddAdventure();
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
