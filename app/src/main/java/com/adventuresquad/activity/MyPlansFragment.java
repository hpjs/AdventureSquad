package com.adventuresquad.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.activity.interfaces.ListFragmentHolder;
import com.adventuresquad.adapter.ItemClickSupport;
import com.adventuresquad.adapter.PlansAdapter;
import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.interfaces.PresentableListView;
import com.adventuresquad.model.Plan;
import com.adventuresquad.presenter.MyPlansPresenter;
import com.adventuresquad.presenter.PersonalPlansPresenter;
import com.adventuresquad.presenter.SquadPlansPresenter;

import java.util.List;

/**
 * Fragment class for my plans activity
 * Provides a reusable fragment
 * Created by Harrison on 11/06/2017.
 */
public class MyPlansFragment extends Fragment implements PresentableListView<Plan>, ItemClickSupport.OnItemClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_PAGE_TYPE = "page_type";

    //The variables that correspond to the arguments above
    private MyPlansActivity.PlansType mPageType;
    private int mSectionNumber;

    //The recyclerview of trips and it's adapter
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private PlansAdapter mAdapter;
    private MyPlansPresenter mPresenter;


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MyPlansFragment newInstance(int sectionNumber, MyPlansActivity.PlansType pageType) {
        MyPlansFragment fragment = new MyPlansFragment();
        Bundle args = new Bundle();

        //Add arguments for the fragment to initialise with
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable(ARG_PAGE_TYPE, pageType);

        //Set arguments on new fragment and return to parent
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Take instance arguments and put them into the current fragment object
        mPageType = (MyPlansActivity.PlansType)getArguments().getSerializable(ARG_PAGE_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate view
        View rootView = inflater.inflate(R.layout.fragment_my_plans, container, false);

        //Bind view objects to code
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.my_plans_recycler_view);
        mProgressBar = (ProgressBar)rootView.findViewById(R.id.my_plans_progress_bar);

        //Set up recycler view
        mAdapter = new PlansAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        //Set up click listener for individual list items in the recycler view
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(this);

        //Set up fragment presenter
        switch(mPageType) {
            case PERSONAL:
                mPresenter = new PersonalPlansPresenter(this,
                        new PlanApi(), new StorageApi(), new UserApi(), new SquadApi());
                break;
            case SQUAD:
                mPresenter = new SquadPlansPresenter(this,
                        new PlanApi(), new StorageApi(), new UserApi(), new SquadApi());
                break;
            case ALL:
                mPresenter = new MyPlansPresenter(this,
                        new PlanApi(), new StorageApi(), new UserApi(), new SquadApi());
                break;
            default:
                mPresenter = new MyPlansPresenter(this,
                        new PlanApi(), new StorageApi(), new UserApi(), new SquadApi());
                break;
        }

        return rootView;
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Plan selectedPlan = mAdapter.getListItem(position);
        //getActivity().navigatePlanDetail(selectedPlan);
        ListFragmentHolder<Plan> parent = (ListFragmentHolder<Plan>)getActivity();
        parent.onItemClicked(selectedPlan, position);
    }

    //region List view & UI methods:

    @Override
    public void addListItem(Plan item) {
        mAdapter.addItem(item);
    }

    @Override
    public void updateList(List<Plan> itemList) {
        mAdapter.setPlanList(itemList);
    }

    @Override
    public void displayMessage(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIcon() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIcon() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    //endregion
}
