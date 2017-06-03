package com.adventuresquad.presenter.interfaces;

import com.adventuresquad.model.User;

/**
 * Facilitates communication for a presenter to use the UserApi
 * Created by Harrison on 3/06/2017.
 */
public interface UserApiPresenter {

    /**
     * Called when a squad is successfully created
     */
    public void onAddUser();

    /**
     * Called when a squad has not been successfully created
     * @param e
     */
    public void onAddUserFail(Exception e);

    public void onRetrieveCurrentUser(User user);

    public void onRetrieveCurrentUserFail(Exception e);

    public void onUpdateUserSquad();

    public void onUpdateUserSquadFail();
}
