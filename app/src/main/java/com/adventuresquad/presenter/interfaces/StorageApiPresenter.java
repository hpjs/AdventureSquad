package com.adventuresquad.presenter.interfaces;

import com.adventuresquad.interfaces.RetrieveImageUriRequest;

/**
 *
 * Created by Harrison on 23/05/2017.
 */
public interface StorageApiPresenter {

    public void retrieveAdventureImageUri(String adventureId, RetrieveImageUriRequest callback);

    //This has been made redundant by passing down a custom 'image request' object
    // instead of using this standard system of callbacks
    //public void onRetrieveAdventureImageUri(String uri);
}
