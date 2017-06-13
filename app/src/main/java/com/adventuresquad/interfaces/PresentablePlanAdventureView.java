package com.adventuresquad.interfaces;

import com.adventuresquad.model.Squad;

import java.util.List;

/**
 * Provides an interface for a Plan presenter to present to a view
 * Created by Harrison on 2/06/2017.
 */
public interface PresentablePlanAdventureView extends Presentable {

    public void displaySquadList(List<Squad> userSquads);

    public void completePlanCreation();

    /**
     * Used when the data has completed adding a squad to a plan
     * View should navigate to next view (date) planning
     */
    public void onAddSquadToPlan();
}
