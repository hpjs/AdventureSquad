package com.adventuresquad.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.adapter.AdventureFeedAdapter;
import com.adventuresquad.adapter.ItemClickSupport;
import com.adventuresquad.api.AdventureApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.interfaces.PresentableAdventureListActivity;
import com.adventuresquad.model.Adventure;
import com.adventuresquad.presenter.MainPresenter;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity class for the main adventure feed
 */
public class MainActivity extends AppCompatActivity implements PresentableAdventureListActivity, ItemClickSupport.OnItemClickListener /*View.OnClickListener*/ {
    //Dependencies (set up in onCreate)
    //TODO -  change this to be an interface?
    private MainPresenter mPresenter;
    private AdventureFeedAdapter mAdventureFeedAdapter;

    //View bindings
    @BindView(R.id.activity_main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind views
        ButterKnife.bind(this);

        //Set up presenter4
        AdventureApi api = new AdventureApi();
        mPresenter = new MainPresenter(this, api, new StorageApi(api));

        //Set up recycler view adapter & manager etc
        mAdventureFeedAdapter = new AdventureFeedAdapter(this, mPresenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdventureFeedAdapter);

        //Set up click listener for individual list items in the recycler view
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(this);


        //Store an image with a particular adventure
        //
    }

    @OnClick(R.id.activity_main_fab)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_fab:
//                mPresenter.storeSampleData(getString(R.string.placeholder_text_short)
//                        + getString(R.string.placeholder_text_large));
                mPresenter.retrieveAdventureList();

                break;
        }
    }

    /**
     * Displays fullscreen loading icon
     */
    public void showLoadingIcon() {

    }

    /**
     * Hides full-screen loading icon
     */
    public void hideLoadingIcon() {

    }

    /**
     * Shows a given string resource as a toast
     */
    public void showToastMessage(String string) {
        Toast.makeText(this, string,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayError(String errorMessage) {
        showToastMessage(errorMessage);
    }

    @Override
    public void onRetrieveAdventureList(List<Adventure> adventureList) {
        mAdventureFeedAdapter.setAdventureList(adventureList);
        mAdventureFeedAdapter.notifyDataSetChanged();

        //TODO - test storing images in FireBase
        //Add images to all of the adventures
        //Uri imagePath = Uri.parse("android.resource://com.adventuresquad/" + R.drawable.firebase_adventure_placeholder);
        //mPresenter.addSampleImages(imagePath);
    }

    /**
     * When an item in the recycler view is clicked, go to that adventure in detail
     * @param recyclerView
     * @param position
     * @param v
     */
    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        //Get adventure that corresponds to
        Adventure selectedAdventure = mAdventureFeedAdapter.getListItem(position);
        //TODO - go to adventure details
        showToastMessage(selectedAdventure.getAdventureTitle()
                + " - " + selectedAdventure.getAdventureId());

        //Create intent to go to adventure detail
        Intent adventureDetail = new Intent(this, AdventureDetailActivity.class);
        adventureDetail.putExtra(AdventureDetailActivity.ADVENTURE_DETAIL_ID,
                selectedAdventure.getAdventureId());
        startActivity(adventureDetail);
    }
}
