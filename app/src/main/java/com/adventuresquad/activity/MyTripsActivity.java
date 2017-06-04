package com.adventuresquad.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.adventuresquad.R;
import com.adventuresquad.interfaces.PresentablePlanListView;
import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.presenter.MyTripsPresenter;

public class MyTripsActivity extends AppCompatActivity implements PresentablePlanListView {

    MyTripsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);

        mPresenter = new MyTripsPresenter(this, new PlanApi(), new StorageApi());

        initialiseNavbar();
    }


    //region NAVBAR CODE
    private void initialiseNavbar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        //Set correct item to be selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_myTrips);
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
                                //DO nothing, already here
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
    public void navigateProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
    //endregion
}
