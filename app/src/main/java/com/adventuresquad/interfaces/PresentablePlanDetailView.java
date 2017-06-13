package com.adventuresquad.interfaces;

import com.adventuresquad.model.Adventure;
import com.adventuresquad.model.Plan;
import com.adventuresquad.model.Squad;

/**
 * Provides an interface for a plan detail presenter to present to a view
 * Implemented by a view to be presentable
 * Created by Harrison on 13/06/2017.
 */
public interface PresentablePlanDetailView extends Presentable {

    void displayPlan(Plan plan);

    void displayPlanImage(String imageUrl);

    void displaySquad(Squad squad);

    void displayAdventureDetail(Adventure adventure);

}
