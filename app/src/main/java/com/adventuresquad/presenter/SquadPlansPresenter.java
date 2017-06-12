package com.adventuresquad.presenter;

import com.adventuresquad.activity.MyPlansFragment;
import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.model.Plan;

/**
 * Subclass of MyPlansPresenter that filters out personal plans, only shows squad plans
 * Created by Harrison on 12/06/2017.
 */
public class SquadPlansPresenter extends MyPlansPresenter {

    /**
     * Full constructor for parent class
     */
    public SquadPlansPresenter(MyPlansFragment view,
                                  PlanApi planApi,
                                  StorageApi storageApi,
                                  UserApi userApi,
                                  SquadApi squadApi) {
        super(view, planApi, storageApi, userApi, squadApi);
    }

    /**
     * Filters out any plans that aren't part of a squad (personal plans)
     * @param plan The plan to check the filter against
     * @return True if plan is part of a squad, false otherwise
     */
    @Override
    public boolean filterMatch(Plan plan) {
        String userSquadId = mCurrentUser.getUserSquadId();
        //Filter out any plans that are part of the user's personal squad
        return !plan.getSquadId().equals(userSquadId);
    }
}
