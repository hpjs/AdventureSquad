package com.adventuresquad.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.activity.interfaces.PlanDateFragment;
import com.adventuresquad.activity.interfaces.PlanFragment;
import com.adventuresquad.activity.interfaces.PlanFragmentHolder;
import com.adventuresquad.activity.interfaces.PlanSquadFragment;
import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.interfaces.PresentableListView;
import com.adventuresquad.interfaces.PresentablePlanView;
import com.adventuresquad.model.Squad;
import com.adventuresquad.presenter.PlanPresenter;

import java.util.Date;
import java.util.List;

public class PlanAdventureActivity extends AppCompatActivity implements PlanFragmentHolder, PresentablePlanView {

    //Constants
    public static final String ADVENTURE_DETAIL_ID = "ADVENTURE_DETAIL_ID";
    public static final String PLAN_ADVENTURE_DEBUG = "plan_adventure";
    public static final String ADVENTURE_TITLE = "ADVENTURE_TITLE";

    PlanPresenter mPresenter;

    //Fragment presenter sections
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private int mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_adventure);

        Toolbar toolbar = (Toolbar) findViewById(R.id.plan_toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.plan_fragment_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mCurrentFragment = 0;

        //Get specific adventure ID
        String adventureId = getIntent().getStringExtra(ADVENTURE_DETAIL_ID);
        String adventureTitle = getIntent().getStringExtra(ADVENTURE_TITLE);

        //Set up presenter and back end logic
        mPresenter = new PlanPresenter(this, adventureId, adventureTitle, new PlanApi(), new UserApi(), new SquadApi());
    }

    @Override
    public void onBackPressed() {
        //Check the current fragment
        if (mCurrentFragment > 0) {
            //return to previous fragment
            //TODO - go to previous fragment on 'back' pressed
        } else {
            super.onBackPressed();
        }
    }

    /**
     * TODO - change this to be more flexible,
     * instead of fragment passing it's position, should probably retrieve from adapter somehow?
     */
    @Override
    public void onNextButtonClicked(int currentSection, PlanFragment planFragment) {
        int totalSections = mSectionsPagerAdapter.getCount();
        mCurrentFragment = currentSection;

        //Converting from zero-indexed to compare if it's the last fragment in the set
        if ((currentSection + 1) < totalSections) {
            //User has finished squad selection fragment
            String squadId = planFragment.getSquadId();
            mPresenter.addSquadToPlan(squadId);
            displayMessage("Assigned squad to plan: " + squadId);
            mViewPager.setCurrentItem(currentSection + 1);
        } else {
            //Fragment was the last one, add date to plan and complete plan
            long date = planFragment.getDate();
            mPresenter.addDateToPlan(date);
            mPresenter.createPlan();
        }
    }

    /**
     * Called when adding a squad to a plan is complete (locally)
     */
    @Override
    public void onAddSquadToPlan() {
        //Squad adding to plan is complete
        //TODO - figure out how to put fragment change here instead of in 'onNextButtonClicked'
        //mSectionsPagerAdapter.
    }

    @Override
    public void displayMessage(String errorMessage) {
        Toast.makeText(this, errorMessage,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displaySquadList(List<Squad> userSquads) {

    }

    @Override
    public void completePlanCreation() {
        //Finish activity
        finish();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private int currentSectionPosition = 0;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private PlanSquadFragment mSquadFragment;

        /**
         * This method is the thing that makes the fragment depending on the position.
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch(position) {
                case 0:
                    //Create a new PlanSquadFragment instead of placeholder(it works!!).
                    mSquadFragment = PlanSquadFragment.newInstance(position);
                    return mSquadFragment;
                case 1:
                    return PlanDateFragment.newInstance(position);
                case 2:
                    return PlanDateFragment.newInstance(position);
                default:
                    Log.w(PLAN_ADVENTURE_DEBUG, "Plan FragmentPagerAdapter tried to make a fragment out of bounds at position " + position);
                    //return PlaceholderFragment.newInstance(position, R.layout.fragment_plan_adventure_date);
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }

        /**
         * Returns the number of the currently activated section
         * @return
         */
        public int getCurrentSectionPosition() {
            return currentSectionPosition;
        }

        private void setCurrentSectionPosition(int currentSectionPosition) {
            this.currentSectionPosition = currentSectionPosition;
        }
    }
}
