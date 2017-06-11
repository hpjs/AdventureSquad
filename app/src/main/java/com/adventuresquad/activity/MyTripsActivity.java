package com.adventuresquad.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.adventuresquad.R;
import com.adventuresquad.adapter.ItemClickSupport;
import com.adventuresquad.adapter.PlansAdapter;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.interfaces.PresentableListView;
import com.adventuresquad.interfaces.PresentablePlanListView;
import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.model.Plan;
import com.adventuresquad.presenter.MyTripsPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyTripsActivity extends AppCompatActivity implements PresentableListView<Plan>, ItemClickSupport.OnItemClickListener {

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


        //Set up recycler view adapter & manager
        mPlansAdapter = new PlansAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mPlansAdapter);

        //Set up click listener for individual list items in the recycler view
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(this);

        //Initialise navbar
        new NavbarHelper(this, R.id.navigation_myTrips);

        //Set up presenter
        mPresenter = new MyTripsPresenter(this, new PlanApi(), new StorageApi(), new UserApi(), new SquadApi());

        //Pull from database
        mPresenter.retrievePlans();
    }

    /**
     * Gets the updated list of
     */
    @OnClick(R.id.my_trips_fab)
    public void refreshList() {
        mPlansAdapter.clearData();
        mPresenter.retrievePlans();
    }

    /**
     * Called when an update has happened on the back end data
     * @param planList The updated list of plans for the recycler view
     */
    @Override
    public void updateList(List<Plan> planList) {
        mPlansAdapter.setPlanList(planList);
        mPlansAdapter.notifyDataSetChanged();
    }

    @Override
    public void addListItem(Plan plan) {
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
}
