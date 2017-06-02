package com.adventuresquad.presenter.interfaces;

/**
 * Created by Harrison on 3/06/2017.
 */
public interface UserApiPresenter {

    /**
     * Adds a user (using Auth system) to the Users list
     */
    //public void addUser();

    /**
     * Called when a squad is successfully created
     */
    public void onAddUser();

    /**
     * Called when a squad has not been successfully created
     * @param e
     */
    public void onAddUserFail(Exception e);
}
