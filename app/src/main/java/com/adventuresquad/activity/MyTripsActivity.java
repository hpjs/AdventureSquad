package com.adventuresquad.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.adventuresquad.R;
import com.adventuresquad.adapter.ItemClickSupport;
import com.adventuresquad.adapter.PlansAdapter;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.interfaces.PresentablePlanListView;
import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.model.Plan;
import com.adventuresquad.presenter.MyTripsPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTripsActivity extends AppCompatActivity implements PresentablePlanListView, ItemClickSupport.OnItemClickListener {

    MyTripsPresenter mPresenter;
    private PlansAdapter mPlansAdapter;

    //Views
    @BindView(R.id.my_trips_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.my_trips_fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);

        //Bind views
        ButterKnife.bind(this);

        //Set up presenter
        mPresenter = new MyTripsPresenter(this, new PlanApi(), new StorageApi(), new UserApi(), new SquadApi());

        //Set up recycler view adapter & manager
        mPlansAdapter = new PlansAdapter(this, mPresenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mPlansAdapter);

        //Set up click listener for individual list items in the recycler view
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(this);

        //Initialise navbar
        initialiseNavbar();
    }

    /**
     * Gets the updated list of
     */
    @OnClick(R.id.my_trips_fab)
    public void refreshList() {
        mPresenter.retrievePlans();
    }

    /**
     * Called when an update has happened on the back end data
     * @param planList The updated list of plans for the recycler view
     */
    @Override
    public void updatePlanList(List<Plan> planList) {
        mPlansAdapter.setPlanList(planList);
        mPlansAdapter.notifyDataSetChanged();
    }

    @Override
    public void addPlanToList(Plan plan) {
        mPlansAdapter.addItem(plan);
    }

    /**
     * Called when a specific plan is selected
     * @param recyclerView
     * @param position
     * @param v
     */
    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        //Get plan that the clicked item corresponds to
        Plan selectedPlan = mPlansAdapter.getListItem(position);

        //Create intent to go to adventure detail
//        Intent adventureDetail = new Intent(this, PlanDetailActivity.class);
//        adventureDetail.putExtra(AdventureDetailActivity.ADVENTURE_DETAIL_ID,
//                selectedAdventure.getAdventureId());
//        startActivity(adventureDetail);

        displayMessage("Clicked plan: " + selectedPlan.getPlanTitle());
    }

    @Override
    public void displayMessage(String errorMessage) {

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
