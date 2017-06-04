package com.adventuresquad.activity.interfaces;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.adventuresquad.R;
import com.adventuresquad.activity.PlanAdventureActivity;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Harrison on 2/06/2017.
 */

public class PlanDateFragment extends Fragment implements View.OnClickListener, PlanFragment {
    //Fragment views
    private Button mButton;
    private DatePicker mDatePicker;

    //Fragment section number details
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int mSectionNumber;

    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    public static PlanSquadFragment newInstance(int sectionNumber/*int num,
                                                insert your 'constructor' arguments here
                                                can't use normal constructor stuff apparently*/) {
        PlanSquadFragment fragmentInstance = new PlanSquadFragment();

        // Inject arguments into the new fragmentInstance
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);

        //Set arguments on new fragment and return to
        fragmentInstance.setArguments(args);
        return fragmentInstance;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Shows how to use the given arguments to set local variables
        mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        //mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    /**
     * Use this method to initialise the fragment's UI
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate current view
        View v = inflater.inflate(R.layout.fragment_plan_adventure_squad, container, false);

        //Bind views
        mButton = (Button)v.findViewById(R.id.plan_squad_next_button);
        mDatePicker = (DatePicker)v.findViewById(R.id.plan_adventure_date_picker);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Sample showing how to handle something?
        //Perhaps showing how to create a new array adapter using the parent activity
        //Should be able to get the activity using getActivity()
        /*setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Cheeses.sCheeseStrings));*/
    }

    /**
     * Callback method to notify activity that 'next' button was pressed
     * Activity should then change fragments
     */
    public void onCompleteButtonClick() {
        try { //Attempt to cast the parent activity as a SwipeFragmentHolder
            SwipeFragmentHolder parent = (SwipeFragmentHolder)getActivity();
            parent.onNextButtonClicked(mSectionNumber, this);
        } catch (ClassCastException castException){
            Log.e(PlanAdventureActivity.PLAN_ADVENTURE_DEBUG,
                    "Could not cast fragment parent as a 'SwipeFragmentHolder'!",
                    castException);
        }
    }

    //TODO - check if the fragment should be handling user input, or the activity
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.plan_adventure_create_button:
                //Notify adventure that next button is pressed
                onCompleteButtonClick();
                break;
        }
    }

    @Override
    public String getSquadId() {
        return null;
    }

    @Override
    public long getDate() {
        //Get the date from the date picker
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth() + 1;
        int dayOfMonth = mDatePicker.getDayOfMonth();

        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth, 0, 0);

        return c.getTimeInMillis();
    }

    /*
     * This method is unused because we don't have a list (YET).
     * Sample code showing how you can use a click listener inside the fragment
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id);
    }*/
}
