package com.adventuresquad.interfaces;

import com.adventuresquad.model.Adventure;

import java.util.List;

/**
 * Interface to allow MainPresenter to perform callback methods on the MainActivity
 * Created by Harrison on 16/05/2017.
 */
public interface PresentableMainActivity extends Presentable {

    public void displayError(String errorMessage);

    public void onRetrieveAdventure(Adventure adventure);

    public void onRetrieveAdventureList(List<Adventure> adventureList);

}
