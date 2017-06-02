package com.adventuresquad.api;

import android.support.annotation.NonNull;

import com.adventuresquad.model.Plan;
import com.adventuresquad.model.Squad;
import com.adventuresquad.presenter.interfaces.PlanApiPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Harrison on 2/06/2017.
 */
public class SquadApi {
    public static final String PLANS_LIST = "squads";

    private FirebaseDatabase mDatabaseInstance;
    private DatabaseReference mSquadsData;

    public SquadApi() {
        mDatabaseInstance = FirebaseDatabase.getInstance();
        mSquadsData = mDatabaseInstance.getReference(PLANS_LIST);
    }

    public void putPlan(Squad squad, final PlanApiPresenter callback) {
        DatabaseReference mNewPlanRef = mSquadsData.push();
        squad.setSquadId(mNewPlanRef.getKey());
        //prepare callback method for when this task is complete
        //Set the actual data
        mNewPlanRef.setValue(squad).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onCreatePlan();
                } else {
                    callback.onCreatePlanFail(task.getException());
                }
            }
        });
    }

}
