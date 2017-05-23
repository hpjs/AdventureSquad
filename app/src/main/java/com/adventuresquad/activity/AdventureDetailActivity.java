package com.adventuresquad.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.adventuresquad.R;
import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.interfaces.PresentableAdventureActivity;
import com.adventuresquad.model.Adventure;
import com.adventuresquad.presenter.AdventureDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdventureDetailActivity extends AppCompatActivity implements PresentableAdventureActivity {

    //Views
    @BindView(R.id.adventure_detail_fab)
    FloatingActionButton mFab;
    @BindView(R.id.adventure_detail_image)
    ImageView mImage;
    @BindView(R.id.adventure_detail_title)
    TextView mTitle;
    @BindView(R.id.adventure_detail_info)
    TextView mInfo;
    //@BindView(R.id.adventure_detail_mapview)
    //private MapView mapView;

    //Presenter
    private AdventureDetailPresenter mPresenter;

    //Constants
    public static final String ADVENTURE_DETAIL_ID = "ADVENTURE_DETAIL_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure_detail);

        //Set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Bind views
        ButterKnife.bind(this);

        //Set up presenter
        mPresenter = new AdventureDetailPresenter(this, new AdventureApi());

        //Retrieve specific adventure
        //Intent currentIntent = getIntent();
        String adventureId = getIntent().getStringExtra(ADVENTURE_DETAIL_ID);
        mPresenter.retrieveAdventure(adventureId);
    }

    /**
     * Starts the flow to create a plan for an adventure
     */
    @OnClick(R.id.adventure_detail_fab)
    public void createPlan() {

    }

    @Override
    public void onRetrieveAdventure(Adventure adventure) {
        //Put adventure contents into view (like a RecyclerView view bind)
        //Load image
        mTitle.setText(adventure.getAdventureTitle());

        mInfo.setText(adventure.getAdventureDetail());

        //Set map view
    }

    @Override
    public void displayError(String errorMessage) {

    }
}