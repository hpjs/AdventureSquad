package com.adventuresquad.interfaces;

import com.adventuresquad.model.Plan;

import java.util.List;

/**
 * Allows presenter to communicate with top level view code
 * Created by Harrison on 4/06/2017.
 */
public interface PresentablePlanListView extends Presentable{

    /**
     * Updates the list of plans in the view e.g. in RecyclerView
     * @param planList
     */
    void updatePlanList(List<Plan> planList);
}
