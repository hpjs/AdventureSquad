package com.adventuresquad.presenter;

import com.adventuresquad.activity.MyPlansFragment;
import com.adventuresquad.api.PlanApi;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.model.Plan;

/**
 * Subclass of MyPlansPresenter that only displays personal plans.
 * Created by Harrison on 12/06/2017.
 */
public class PersonalPlansPresenter extends MyPlansPresenter {

    /**
     * Full constructor for parent class
     */
    public PersonalPlansPresenter(MyPlansFragment view,
                                  PlanApi planApi,
                                  StorageApi storageApi,
                                  UserApi userApi,
                                  SquadApi squadApi) {
        super(view, planApi, storageApi, userApi, squadApi);
    }

    /**
     * Filters out any plans that aren't part of a squad (personal plans)
     * @param plan The plan to check the filter against
     * @return True if plan is a personal plan, false otherwise
     */
    @Override
    public boolean filterMatch(Plan plan) {
        String userSquadId = mCurrentUser.getUserSquadId();
        //Filter out any plans that are NOT part of the user's personal squad
        return plan.getSquadId().equals(userSquadId);
    }
}
