package com.adventuresquad.interfaces;

import com.adventuresquad.model.Squad;

/**
 * Provides an interface for a presenter to access / hold methods for a squad view
 * Created by Harrison on 8/06/2017.
 */
public interface PresentablePlanSquadView {

    /**
     * Adds a single squad to be displayed in the list
     * @param squad
     */
    public void displaySquad(Squad squad);

}
