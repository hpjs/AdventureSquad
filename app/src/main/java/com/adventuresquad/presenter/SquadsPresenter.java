package com.adventuresquad.presenter;

import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.interfaces.PresentableListView;
import com.adventuresquad.model.Squad;
import com.adventuresquad.model.User;

import java.util.List;

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

        //Kick off squad retrieval
        retrieveSquadList();
    }

    /**
     * Gets a list of squad objects for the view
     */
    public void retrieveSquadList() {
        //get current user
        mUserApi.retrieveCurrentUser(new UserApi.RetrieveUserListener() {
            @Override
            public void onGetUser(User user) {
                //Get squads for this user
                mCurrentUser = user;
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

            @Override
            public void onGetUserFail(Exception e) {
                //Could not get current user

            }
        });

        //get squads of that user
    }

    /**
     * Called when a single squad is loaded for the user
     */
    private void onSquadLoaded(Squad squad) {
        //TODO - fix up squad image retrieval

    }

}
