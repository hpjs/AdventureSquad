package com.adventuresquad.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.interfaces.PresentablePlanDetailView;
import com.adventuresquad.presenter.PlanDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlanDetailActivity extends AppCompatActivity implements PresentablePlanDetailView {

    //Presenter
    PlanDetailPresenter mPresenter;

    //View bindings
    @BindView(R.id.plan_detail_toolbar)
    Toolbar mToolbar;

    //Squad item view bindings
    @BindView(R.id.squad_image)
    ImageView mImageSquad;
    @BindView(R.id.squad_name)
    TextView mTextSquadName;
    @BindView(R.id.squad_details)
    TextView mTextSquadDetail;

    @BindView(R.id.plan_date)
    TextView mTextPlanDate;
    @BindView(R.id.plan_adventure_title)
    TextView mTextAdventureTitle;
    @BindView(R.id.plan_adventure_detail)
    TextView mTextAdventureDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

        //Set up UI as necessary
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mPresenter = new PlanDetailPresenter();

    }

    @OnClick(R.id.plan_detail_fab)
    public void deletePlan() {
        //TODO - delete plan impl.
    }

    @Override
    public void displayMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
