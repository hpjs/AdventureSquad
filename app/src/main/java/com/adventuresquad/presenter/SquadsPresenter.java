package com.adventuresquad.presenter;

import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.interfaces.PresentableListView;
import com.adventuresquad.model.Squad;
import com.adventuresquad.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        mView.showLoadingIcon();
        //get current user
        mUserApi.retrieveCurrentUser(new UserApi.RetrieveUserListener() {
            @Override
            public void onGetUser(User user) {
                //Get squads for this user
                mCurrentUser = user;
                retrieveSquads(user);
            }

            @Override
            public void onGetUserFail(Exception e) {
                //Could not get current user

            }
        });
    }

    /**
     * used for converting 'firebase' IDs into a more easily understandable list of strings
     * @param map
     * @return
     */
    private List<String> idMapToStringList(Map<String, Boolean> map) {
        List<String> idList = new ArrayList<>();
        for (String key : map.keySet()) {
            idList.add(key);
        }
        return idList;
    }

    /**
     * Retrieve a list of Squad IDs that this user has.
     * @param user
     */
    private void retrieveSquads(User user) {
        if (user.getUserSquads() != null) {
            List<String> userSquads = idMapToStringList(user.getUserSquads());
            mSquadApi.retrieveSquadList(userSquads, new RetrieveDataRequest<Squad>() {
                @Override
                public void onRetrieveData(Squad data) {
                    mView.hideLoadingIcon();
                    mView.addListItem(data);
                }

                @Override
                public void onRetrieveDataFail(Exception e) {
                    mView.displayMessage("Failed to retrieve one of your squads");
                }
            });
        } else {
            //TODO - Make this message look nicer later
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
