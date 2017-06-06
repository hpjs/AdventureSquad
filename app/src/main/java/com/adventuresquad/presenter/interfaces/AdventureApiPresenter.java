package com.adventuresquad.presenter.interfaces;

import com.adventuresquad.model.Adventure;

import java.util.List;

/**
 * Created by Harrison on 11/05/2017.
 * Allows communication between a presenter and the AdventureApi
 * Nutshell: Provides callback methods for the Api to use when stuff happens
 * Goal: Should not be any actual database code in the presenter. Makes code swapping easier.
 */
public interface AdventureApiPresenter {

    //Constructor (not part of an interface)
    //public DefaultPresenter(Activity activity);

    //Methods

    /**
     * Called when creating an adventure has completed successfully
     * @param adventureId
     */
    public void onCreateAdventure(String adventureId);

    /**
     * Called when creating a new adventure object has failed
     * @param e
     */
    public void onCreateAdventureError(Exception e);

    /**
     * Callback after an API request for a specific adventure
     * TODO: Should consider sending an identifier, so presenter knows what adventure it's getting
     *
     * @param adventure The adventure that was retrieved by the API
     */
    public void onRetrieveAdventure(Adventure adventure);

//    /**
//     * Callback method after an API request for a list of adventures
//     *
//     * @param adventureList The list of adventures that was retrieved by the API
//     */
//    public void retrieveAdventureImageUris(List<Adventure> adventureList);

//    /**
//     * Called if there was a generic issue with retrieving a certain request for data
//     */
//    public void onRetrieveError(Exception e);
    /**
     * Called when there is a specific Firebase error that occcurs.
     * NOTE: Avoid where possible, will likely be deprecated later.
     * NOTE: Have removed this from the interface. The presenter should have no knowledge of the specific Firebase errors or anything else.
     */ /*
    public void onGetDatabaseError(DatabaseError error);*/
}
