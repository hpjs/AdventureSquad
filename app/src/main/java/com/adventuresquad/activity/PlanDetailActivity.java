package com.adventuresquad.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.api.GlideApp;
import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.interfaces.PresentablePlanDetailView;
import com.adventuresquad.model.Adventure;
import com.adventuresquad.model.Plan;
import com.adventuresquad.model.Squad;
import com.adventuresquad.presenter.PlanDetailPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlanDetailActivity extends AppCompatActivity implements PresentablePlanDetailView {

    //Constants (for bundle/intent)
    public static final String PLAN_ID = "plan_id";

    //Presenter
    PlanDetailPresenter mPresenter;

    //View bindings
    @BindView(R.id.plan_detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.plan_detail_view)
    RelativeLayout mContent;
    @BindView(R.id.plan_detail_progress_bar)
    ProgressBar mProgressBar;

    //Squad item view bindings
    @BindView(R.id.squad_image)
    ImageView mImageSquad;
    @BindView(R.id.squad_name)
    TextView mTextSquadName;
    @BindView(R.id.squad_details)
    TextView mTextSquadDetail;

    //Plan view bindings
    @BindView(R.id.plan_detail_image)
    ImageView mPlanImage;
    @BindView(R.id.plan_date)
    TextView mTextPlanDate;

    //Adventure detail view bindings
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

        //Get plan ID from intent object & create presenter
        String planId = getIntent().getStringExtra(PLAN_ID);
        mPresenter = new PlanDetailPresenter(this, planId, new PlanApi(), new SquadApi(), new AdventureApi(), new StorageApi());

    }

    @OnClick(R.id.plan_detail_fab)
    public void deletePlan() {
        //TODO - delete plan implementation.
    }

    /**
     * Displays the given plan in the UI
     * @param plan
     */
    @Override
    public void displayPlan(Plan plan) {
        //Display plan
        mToolbar.setTitle(plan.getPlanTitle());
        setTitle(plan.getPlanTitle());

        //Date formatting and display
        Date date = new Date(plan.getBookingDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEEEEEE, d MMMMMMMMMM yyyy", Locale.ENGLISH);
        mTextPlanDate.setText(dateFormat.format(date));

    }

    @Override
    public void displayPlanImage(String imageUrl) {
        //Load image
        GlideApp
                .with(mPlanImage.getContext())
                .load(imageUrl)
                .placeholder(R.color.colorPrimary)
                .error(R.drawable.ic_broken_image_black_24dp)
                .fitCenter()
                .into(mPlanImage);
    }

    @Override
    public void displaySquad(Squad squad) {
        //mImageSquad
        mTextSquadName.setText(squad.getSquadName());
        if (squad.getSquadUsers() != null) {
            mTextSquadDetail.setText(squad.getSquadUsers().size() + " members");
        }
        if (squad.getSquadPlans() != null) {
            mTextSquadDetail.setText(", " + squad.getSquadPlans().size() + " plans");
        }
    }

    @Override
    public void displayAdventureDetail(Adventure adventure) {
        mTextAdventureTitle.setText(adventure.getAdventureTitle());
        mTextAdventureDetail.setText(adventure.getAdventureDetail());
    }

    @Override
    public void displayMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIcon() {
        mContent.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIcon() {
        mProgressBar.setVisibility(View.GONE);
        mContent.setVisibility(View.VISIBLE);
    }
}
