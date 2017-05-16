package com.adventuresquad.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.adapter.AdventureFeedAdapter;
import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity class for the main adventure feed
 */
public class MainActivity extends AppCompatActivity /*implements View.OnClickListener*/ {

    private MainPresenter mPresenter;

    private RecyclerView.LayoutManager mLayoutManager;

    //View bindings
    @BindView(R.id.activity_main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_fab)
    FloatingActionButton mFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //Set up recycler view adapter & manager etc
        AdventureFeedAdapter mAdventureFeedAdapter = new AdventureFeedAdapter(this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdventureFeedAdapter);


        //Set up presenter
        mPresenter = new MainPresenter(this, new AdventureApi());

        //Set up presenter dependency in adapter
        mAdventureFeedAdapter.setPresenter(mPresenter);

        //Test methods to insert sample adventures into database
        mPresenter.storeSampleData();
        //mPresenter.setLocalSampleData();

        //Get the data from Firebase
        //Moved to FAB on click
        //mPresenter.getData();

    }

    @OnClick(R.id.activity_main_fab)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_fab:
                mPresenter.getData();
                break;
        }
    }

    /**
     * Shows a given string resource as a toast
     */
    public void showToastMessage(int stringResourceId) {
        Toast.makeText(this, stringResourceId,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows a given string resource as a toast
     */
    public void showToastMessage(String string) {
        Toast.makeText(this, string,
                Toast.LENGTH_SHORT).show();
    }
}
