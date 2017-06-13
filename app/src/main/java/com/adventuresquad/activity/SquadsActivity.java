package com.adventuresquad.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.adapter.ItemClickSupport;
import com.adventuresquad.adapter.PlansAdapter;
import com.adventuresquad.adapter.SquadsAdapter;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.interfaces.Presentable;
import com.adventuresquad.interfaces.PresentableListView;
import com.adventuresquad.model.Squad;
import com.adventuresquad.presenter.SquadsPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SquadsActivity extends AppCompatActivity implements PresentableListView<Squad>, ItemClickSupport.OnItemClickListener {
    //Views
    @BindView(R.id.squads_recycler_view)
    RecyclerView mSquadsRecyclerView;
    @BindView(R.id.squads_progress_bar)
    ProgressBar mProgressBar;

    //Other classes
    private SquadsAdapter mAdapter;
    SquadsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squads);

        //Bind views
        ButterKnife.bind(this);

        //Set up recycler view adapter & manager
        mAdapter = new SquadsAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mSquadsRecyclerView.setLayoutManager(layoutManager);
        mSquadsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSquadsRecyclerView.setAdapter(mAdapter);

        //Set up click listener for individual list items in the recycler view
        ItemClickSupport.addTo(mSquadsRecyclerView).setOnItemClickListener(this);

        //Initialise navbar
        new NavbarHelper(this, R.id.navigation_squads);

        //Set up presenter with dependencies
        mPresenter = new SquadsPresenter(this, new SquadApi(), new UserApi(), new StorageApi());
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        //TODO - implement click handling
    }

    @Override
    public void addListItem(Squad item) {
        mAdapter.addItem(item);
    }

    @Override
    public void updateList(List<Squad> itemList) {
        //mAdapter.updateList
    }

    @Override
    public void displayMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIcon() {
        mSquadsRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIcon() {
        mProgressBar.setVisibility(View.GONE);
        mSquadsRecyclerView.setVisibility(View.VISIBLE);
    }
}
