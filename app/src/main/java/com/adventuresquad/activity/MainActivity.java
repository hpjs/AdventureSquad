package com.adventuresquad.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.adventuresquad.R;
import com.adventuresquad.adapter.AdventureFeedAdapter;
import com.adventuresquad.presenter.MainPresenter;

/**
 * Activity class for the main adventure feed
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler_view);

        //Set up recycler view adapter & manager etc
        AdventureFeedAdapter mAdventureFeedAdapter = new AdventureFeedAdapter(this, mPresenter);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdventureFeedAdapter);
        mPresenter.setAdapter(mRecyclerView);

        //Set up presenter
        mPresenter = new MainPresenter(this, mAdventureFeedAdapter);
        mPresenter.getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
