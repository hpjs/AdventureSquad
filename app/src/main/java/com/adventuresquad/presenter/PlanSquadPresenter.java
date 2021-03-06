package com.adventuresquad.presenter;

import com.adventuresquad.api.ListHelper;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.interfaces.PresentableListFragment;
import com.adventuresquad.model.Squad;
import com.adventuresquad.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.adventuresquad.api.ListHelper.toList;

/**
 * Presents a list of squads that is selectable for a plan
 * Created by Harrison on 8/06/2017.
 */
public class PlanSquadPresenter {
    private PresentableListFragment<Squad> mView;

    private SquadApi mSquadApi;
    private UserApi mUserApi;
    private StorageApi mStorageApi;
    private User mCurrentUser;
    private String mSelectedSquadId;

    public PlanSquadPresenter(PresentableListFragment<Squad> view, SquadApi squadApi, UserApi userApi, StorageApi storageApi) {
        mView = view;
        mSquadApi = squadApi;
        mUserApi = userApi;
        mStorageApi = storageApi;

        //Kick off retrieval process
        retrieveCurrentUser();
    }

    /**
     * Gets the current user so that we can get the squads for this user
     */
    public void retrieveCurrentUser() {
        mView.showLoadingIcon();
        //get current user
        mUserApi.retrieveCurrentUser(new RetrieveDataRequest<User>(){
            @Override
            public void onRetrieveData(User user) {
                //Get squads for this user
                mCurrentUser = user;
                retrieveSquads(user);
            }

            @Override
            public void onRetrieveDataFail(Exception e) {
                //Could not get current user
                //Squad retrieval failed
            }
        });
    }

    /**
     * Retrieve a list of Squad IDs that this user has.
     * @param user The user object to retrieve the squads of
     */
    private void retrieveSquads(User user) {
        if (user.getUserSquads() != null) {
            List<String> userSquads = toList(user.getUserSquads());
            mSquadApi.retrieveSquadList(userSquads, new RetrieveDataRequest<Squad>() {
                @Override
                public void onRetrieveData(Squad data) {
                    mView.hideLoadingIcon();
                    mView.addListItem(data);
                }

                @Override
                public void onRetrieveDataFail(Exception e) {
                    mView.hideLoadingIcon();
                    mView.displayMessage("Failed to retrieve one of your squads");
                }
            });
        } else {
            mView.hideLoadingIcon();
            mView.displayMessage("You don't have any squads!");
        }
    }

    public void completeFragment() {
        //If no squad was selected, then return the User's personal squad
        if (mSelectedSquadId == null || mSelectedSquadId.isEmpty()) {
            mSelectedSquadId = mCurrentUser.getUserSquadId();
        }
        //If squadId null/empty, then get personal squad ID. Otherwise, return what you've got.
        mView.onCompleteFragment();
    }

    /**
     * Sets the currently selected squad
     * @param squad The squad that was just clicked
     * @param itemSelected Whether the item was selected (true) or deselected (false)
     */
    public void squadSelected(Squad squad, boolean itemSelected) {
        if (itemSelected) {
            //Item selected, set the squad to the selected squad
            //mView.displayMessage("Squad selected: " + squad.getSquadId());
            setSelectedSquadId(squad.getSquadId());
        } else {
            //Item deselected, set the squad to the user's personal squad
            //mView.displayMessage("Squad Deselected, using user squad: " + mCurrentUser.getUserSquadId());
            setSelectedSquadId(mCurrentUser.getUserSquadId());
        }
    }

    public String getSelectedSquadId() {
        return mSelectedSquadId;
    }

    public void setSelectedSquadId(String selectedSquadId) {
        this.mSelectedSquadId = selectedSquadId;
    }
}
