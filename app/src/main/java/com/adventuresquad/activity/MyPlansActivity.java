package com.adventuresquad.activity;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.adventuresquad.R;

public class MyPlansActivity extends AppCompatActivity {

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

        //Initialise presenter

    }

//    private void initialiseTabView() {
//        // Create a tab listener that is called when the user changes tabs.
//        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
//            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//                // show the given tab
//            }
//
//            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
//                // hide the given tab
//            }
//
//            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//                // probably ignore this event
//            }
//        };
//
//        // Add 3 tabs, specifying the tab's text and TabListener
//        for (int i = 0; i < 3; i++) {
//            actionBar.addTab(
//                    actionBar.newTab()
//                            .setText("Tab " + (i + 1))
//                            .setTabListener(tabListener));
//        }
//
//    }

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
     * Defines the types of pages that can occur in the plans activity
     * Used to determine which list to load on each fragment
     */
    public enum MyTripsPage {
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
                    return MyPlansFragment.newInstance(position+1, MyTripsPage.ALL);
                case 1:
                    return MyPlansFragment.newInstance(position+1, MyTripsPage.PERSONAL);
                case 2:
                    return MyPlansFragment.newInstance(position+1, MyTripsPage.SQUAD);
            }

            return MyPlansFragment.newInstance(position + 1, MyTripsPage.ALL);
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
