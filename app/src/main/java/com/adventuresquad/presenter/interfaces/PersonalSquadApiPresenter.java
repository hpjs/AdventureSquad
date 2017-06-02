package com.adventuresquad.presenter.interfaces;

/**
 * Similar to SquadApiPresenter, but for a user's 'personal' squad
 * This should probably be part of the login flow
 * Created by Harrison on 3/06/2017.
 */
public interface PersonalSquadApiPresenter {

    public void createPersonalSquad(String userId);

    /**
     * Called when a squad is successfully created
     */
    public void onCreatePersonalSquad();

    /**
     * Called when a squad has not been successfully created
     * @param e
     */
    public void onCreatePersonalSquadFail(Exception e);
}
