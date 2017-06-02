package com.adventuresquad.interfaces;

import com.adventuresquad.model.Adventure;

/**
 *
 * Created by Harrison on 22/05/2017.
 */
public interface PresentableAdventureView extends Presentable {

    /**
     * Called when a single adventure is successfully recieved
     * @param adventure
     */
    public void displayAdventure(Adventure adventure);

}
