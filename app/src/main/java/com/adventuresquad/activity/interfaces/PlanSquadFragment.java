package com.adventuresquad.activity.interfaces;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.adventuresquad.R;
import com.adventuresquad.activity.PlanAdventureActivity;
import com.adventuresquad.adapter.ItemClickSupport;
import com.adventuresquad.adapter.SquadsAdapter;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.interfaces.PresentableListFragment;
import com.adventuresquad.interfaces.PresentableListView;
import com.adventuresquad.model.Squad;
import com.adventuresquad.presenter.PlanSquadPresenter;

import java.util.Date;
import java.util.List;

/**
 * Fragment for the squad list
 * Created by Harrison on 2/06/2017.
 */
public class PlanSquadFragment extends Fragment
        implements View.OnClickListener, PresentableListFragment<Squad>,
        PlanFragment, ItemClickSupport.OnItemClickListener {
    //Fields
    //Number that this fragment is in in the list of fragments
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int mSectionNumber;

    //Presenter
    private PlanSquadPresenter mPresenter;

    //UI views and things
    private Button mButton;
    private RecyclerView mRecyclerView;
    private SquadsAdapter mAdapter;

    /**
     * Create a new instance of the PlanSquadFragment
     * Basically acts as a factory for this particular fragment
     */
    public static PlanSquadFragment newInstance(int sectionNumber) {
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

        //Get new instance arguments and populate class & fragment with them
        mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
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

        mRecyclerView = (RecyclerView)v.findViewById(R.id.plan_squad_list);

        //Set up recycler view (with context from the activity)
        mAdapter = new SquadsAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        //Set up click listener for individual list items in the recycler view
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(this);

        //Set up fragment presenter
        mPresenter = new PlanSquadPresenter(this, new SquadApi(), new UserApi(), new StorageApi());

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
     * Called when a squad is selected
     * @param recyclerView
     * @param position
     * @param v
     */
    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        //TODO - should update the currently selected squad
        //TODO - need to add stuff in Activity and Presenter to get the list into hereeh
    }

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
     * Next button clicked,
     */
    public void onNextButtonClick() {
        //TODO - if no squad selected, should set squad to be personal squad here (using presenter)

        mPresenter.completeFragment();
        //completeFragment();

    }

    /**
     * Presenter is done, notify Activity that fragment is done
     */
    @Override
    public void onCompleteFragment() {
        try { //Attempt to cast the parent activity as a SwipeFragmentHolder
            SwipeFragmentHolder parent = (SwipeFragmentHolder)getActivity();
            parent.onNextButtonClicked(mSectionNumber, this);
        } catch (ClassCastException castException){
            Log.e(PlanAdventureActivity.PLAN_ADVENTURE_DEBUG,
                    "Could not use Activity as a 'SwipeFragmentHolder'!",
                    castException);
        }
    }

    /**
     * Retrieve the user's selected squad in the list
     * @return The user's selected squad to make this plan for
     */
    @Override
    public String getSquadId() {
        return mPresenter.getSelectedSquadId();
    }

    /**
     * Only for date fragment, should return null here
     * @return
     */
    @Override
    public long getDate() {
        return 0;
    }

    @Override
    public void displayMessage(String errorMessage) {
        //No method to do so currently
    }

    @Override
    public void addListItem(Squad item) {
        mAdapter.addItem(item);
    }

    @Override
    public void updateList(List<Squad> itemList) {
        //No method to do so currently in the adapter, probably not needed currently
    }
}
