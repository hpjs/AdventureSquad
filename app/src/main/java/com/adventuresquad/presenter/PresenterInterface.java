package com.adventuresquad.presenter;

/**
 * Created by Harrison on 11/05/2017.
 */

import com.adventuresquad.model.Adventure;

import java.util.List;

/**
 * Default presenter class for duplicating to make new presenter objects
 */
public interface PresenterInterface {

    //Constructor (not part of an interface)
    //public DefaultPresenter(Activity activity);

    //Methods
    public void onRecieveData();

    public void onRecieveData(Adventure adventure);

    public void onRecieveData(List<Adventure> adventureList);
}
