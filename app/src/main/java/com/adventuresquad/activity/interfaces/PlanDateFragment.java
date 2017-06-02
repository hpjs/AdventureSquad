package com.adventuresquad.activity.interfaces;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.adventuresquad.R;

/**
 * Created by Harrison on 2/06/2017.
 */

public class PlanDateFragment extends Fragment implements View.OnClickListener {
    //Note - none of the fields can be private apparently
    Button mButton;

    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    public static PlanSquadFragment newInstance(/*int num,
                                                insert your 'constructor' arguments here
                                                can't use normal constructor stuff apparently*/) {
        PlanSquadFragment fragmentInstance = new PlanSquadFragment();

        // Inject arguments into the new fragmentInstance
        Bundle args = new Bundle();
        //args.putInt("num", num);
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

        //Example to bind objects
        //View tv = v.findViewById(R.id.text);
        //((TextView)tv).setText("Fragment #" + mNum);
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
        getActivity().onBackPressed();
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

    /*
     * This method is unused because we don't have a list (YET).
     * Sample code showing how you can use a click listener inside the fragment
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id);
    }*/
}
