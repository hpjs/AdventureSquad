package com.adventuresquad.activity.interfaces;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.adventuresquad.R;
import com.adventuresquad.activity.PlanAdventureActivity;

import java.util.Date;

/**
 * Fragment for the squad list
 * Created by Harrison on 2/06/2017.
 */
public class PlanSquadFragment extends Fragment implements View.OnClickListener, PlanFragment {
    //Fields
    //Number that this fragment is in in the list of fragments
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int mSectionNumber;
    //Note - none of the fields can be private apparently
    private Button mButton;

    /**
     * Create a new instance of the PlanSquadFragment
     * Basically acts as a factory for this particular fragment
     */
    public static PlanSquadFragment newInstance(int sectionNumber /*int num,
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
        //Get arguments and populate class & fragment with them
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

        //Bind view objects to code
        mButton = (Button)v.findViewById(R.id.plan_squad_next_button);
        mButton.setOnClickListener(this);
        //Example to bind objects
        //View tv = v.findViewById(R.id.text);
        //((TextView)tv).setText("Fragment #" + mNum);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Not sure what this method is for.
        //Sample below perhaps showing how to create a new array adapter using the parent activity
        //Specifically only called when the parent activity itself is created?
        //TODO - look up what this (onActivityCreated) method does in a fragment
        //Should be able to get the activity using getActivity()
        /*setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Cheeses.sCheeseStrings));*/
    }

    /**
     * Notifies parent activity that 'next' button was pressed
     * Activity should then change fragments
     */
    public void onNextButtonClick() {
        try { //Attempt to cast the parent activity as a SwipeFragmentHolder
            SwipeFragmentHolder parent = (SwipeFragmentHolder)getActivity();
            parent.onNextButtonClicked(mSectionNumber, this);
        } catch (ClassCastException castException){
            Log.e(PlanAdventureActivity.PLAN_ADVENTURE_DEBUG,
                    "Could not cast fragment parent as a 'SwipeFragmentHolder'!",
                    castException);
        }
    }

    //TODO - check if the fragment should be handling user input, or the activity instead
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.plan_squad_next_button:
                //Notify adventure that next button is pressed
                onNextButtonClick();
                break;
        }
    }

    /**
     * Retrieve the user's selected squad in the list
     * @return The user's selected squad to make this plan for
     */
    @Override
    public String getSquadId() {
        return null;
    }

    /**
     * Only for date fragment, should return null here
     * @return
     */
    @Override
    public long getDate() {
        return 0;
    }

    /*
     * This method is unused because we don't have a list (YET).
     * Sample code showing how you can use a click listener inside the fragment
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id);
    }*/
}
