package com.adventuresquad.presenter;

import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.interfaces.PresentableListView;
import com.adventuresquad.model.Squad;
import com.adventuresquad.model.User;

/**
 * Provides presentation for the squads activity
 * Created by Harrison on 7/06/2017.
 */
public class SquadsPresenter {
    private SquadApi mSquadApi;
    private UserApi mUserApi;
    private StorageApi mStorageApi;

    //View for this presenter
    private PresentableListView<Squad> mView;

    //The current user
    private User mCurrentUser;

    public SquadsPresenter(PresentableListView<Squad> view,
                           SquadApi squadApi, UserApi userApi, StorageApi storageApi) {
        mView = view;
        mSquadApi = squadApi;
        mUserApi = userApi;
        mStorageApi = storageApi;

        //Kick off user's squad retrieval
        retrieveCurrentUser();
    }

    /**
     * Gets the current user so that we can get the squads for this user
     */
    public void retrieveCurrentUser() {
        //get current user
        mUserApi.retrieveCurrentUser(new UserApi.RetrieveUserListener() {
            @Override
            public void onGetUser(User user) {
                //Get squads for this user
                mCurrentUser = user;
                retrieveSquad(user);
            }

            @Override
            public void onGetUserFail(Exception e) {
                //Could not get current user

            }
        });
    }

    /**
     *
     */
    private void retrieveSquad(User user) {
        //Check that the user's squad list is not null
        if (user.getUserSquadList() != null) {
            //Proceed with retrieving the squad list for this user
            mSquadApi.retrieveSquadList(user.getUserSquadList(), new RetrieveDataRequest<Squad>() {
                @Override
                public void onRetrieveData(Squad data) { //Single squad retrieved
                    mView.addListItem(data);
                }

                @Override
                public void onRetrieveDataFail(Exception e) { //Single squad retrieve failed
                    mView.displayMessage("Failed to retrieve one of your squads");
                }
            });
        }
        else {
            //User doesn't have any squads
            //TODO - Make this message a bit nicer later
            mView.displayMessage("You don't have any squads!");
        }
    }

    /**
     * Called when a single squad is loaded for the user
     */
    private void onSquadLoaded(Squad squad) {
        //TODO - fix up squad image retrieval

    }

}
