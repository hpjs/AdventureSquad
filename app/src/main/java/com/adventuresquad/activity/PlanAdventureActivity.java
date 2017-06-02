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

import com.adventuresquad.R;
import com.adventuresquad.activity.interfaces.PlanDateFragment;
import com.adventuresquad.activity.interfaces.PlanFragmentHolder;
import com.adventuresquad.activity.interfaces.PlanSquadFragment;
import com.adventuresquad.api.PlanApi;
import com.adventuresquad.interfaces.PresentablePlanView;
import com.adventuresquad.model.Squad;
import com.adventuresquad.presenter.PlanPresenter;

import java.util.List;

public class PlanAdventureActivity extends AppCompatActivity implements PlanFragmentHolder, PresentablePlanView {

    public static final String PLAN_ADVENTURE_DEBUG = "plan_adventure";

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

        //Set up presenter and back end logic
        PlanApi api = new PlanApi();
        mPresenter = new PlanPresenter(api, this);
    }

    //TODO - override plan activity onBackPressed() to not close if on Date fragment
    @Override
    public void onBackPressed() {
        //Check the current fragment
    }

    /**
     * TODO - change this to be more flexible,
     * instead of fragment passing it's position, should probably retrieve from adapter somehow?
     */
    @Override
    public void onNextButtonClicked(int currentSection) {
        int totalSections = mSectionsPagerAdapter.getCount();
        currentSection = currentSection + 1; //Converting from zero-indexed to 'normal' numbers

        if (currentSection >= totalSections) {
            //Fragment was the last one, plan creation is complete
            finish(); //TODO - replace with presenter logic to create adventure
        } else {
            mViewPager.setCurrentItem(currentSection);
        }
    }

    @Override
    public void displayMessage(String errorMessage) {

    }

    @Override
    public void displaySquadList(List<Squad> userSquads) {

    }

    @Override
    public void completePlanCreation() {

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

        /**
         * This method is the thing that makes the fragment depending on the position.
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            //NOTE - both things are inflated at the same time, therefore 'current position' is not accurate
            //Figure out a better way to do this
//            setCurrentSectionPosition(position);
            switch(position) {
                case 0:
                    //Create a new PlanSquadFragment instead of placeholder(it works!!).
                    return PlanSquadFragment.newInstance(position);
                case 1:
                    return PlanDateFragment.newInstance(position);
                default:
                    Log.w(PLAN_ADVENTURE_DEBUG, "Plan FragmentPagerAdapter tried to make a fragment out of bounds at position " + position);
                    return PlaceholderFragment.newInstance(position, R.layout.fragment_plan_adventure_date);
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

    /**
     * A placeholder fragment containing a simple view.
     * TODO - remove this, and fully replace with your own fragment system.
     * This should only be a temporary thing.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_FRAGMENT_TO_LOAD = "fragment_to_load";

        private static Button mButton;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, int fragmentToLoad) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putInt(ARG_FRAGMENT_TO_LOAD, fragmentToLoad);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //Inflates the specified fragment
            View rootView = inflater.inflate(getArguments().getInt(ARG_FRAGMENT_TO_LOAD), container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
//        NOTE: DEPRECATED, try something different
//        @Override
//        public void onAttach(Activity activity) {
//
//        }

    }
}
