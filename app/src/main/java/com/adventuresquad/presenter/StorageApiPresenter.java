package com.adventuresquad.presenter;

import com.adventuresquad.interfaces.RetrieveImageUriRequest;

/**
 *
 * Created by Harrison on 23/05/2017.
 */
interface StorageApiPresenter {

    public void retrieveAdventureImageUri(String adventureId, RetrieveImageUriRequest callback);

    public void onRetrieveAdventureImageUri(String uri);
}
