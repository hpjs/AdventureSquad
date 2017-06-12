package com.adventuresquad.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;

import com.adventuresquad.R;
import com.adventuresquad.activity.interfaces.ListFragmentHolder;
import com.adventuresquad.model.Plan;

public class MyPlansActivity extends AppCompatActivity implements ListFragmentHolder<Plan> {

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

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plans);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.my_plans_fragment_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //Set up tab layout automatically by using the view pager
        TabLayout mTabLayout = (TabLayout)findViewById(R.id.my_plans_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        //Initialise navbar
        new NavbarHelper(this, R.id.navigation_myTrips);

        //Initialise presenter

    }

    //Potentially don't need.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Allows plan objects to be clicked, then goes to plan details page
     * @param object
     * @param position
     */
    @Override
    public void onItemClicked(Plan object, int position) {
        Intent planDetail = new Intent(this, PlanDetailActivity.class);
        //planDetail.putExtra()
        startActivity(planDetail);
    }

    /**
     * Defines the types of pages that can occur in the plans activity
     * Used to determine which list to load on each fragment
     */
    public enum PlansType {
        ALL,
        PERSONAL,
        SQUAD;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position) {
                case 0:
                    return MyPlansFragment.newInstance(position+1, PlansType.ALL);
                case 1:
                    return MyPlansFragment.newInstance(position+1, PlansType.PERSONAL);
                case 2:
                    return MyPlansFragment.newInstance(position+1, PlansType.SQUAD);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "All";
                case 1:
                    return "Personal";
                case 2:
                    return "Squad";
            }
            return null;
        }

    }

}
