package com.adventuresquad.api;

import android.support.annotation.NonNull;

import com.adventuresquad.model.Plan;
import com.adventuresquad.presenter.interfaces.PlanApiPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Interacts with Google FireBase directly
 * Created by Harrison on 2/06/2017.
 */
public class PlanApi {

    public static final String PLANS_LIST = "plans";

    private FirebaseDatabase mDatabaseInstance;
    private DatabaseReference mPlansData;

    public PlanApi() {
        mDatabaseInstance = FirebaseDatabase.getInstance();
        mPlansData = mDatabaseInstance.getReference(PLANS_LIST);
    }

    public void putPlan(Plan plan, final PlanApiPresenter callback) {
        DatabaseReference mNewPlanRef = mPlansData.push();
        plan.setPlanId(mNewPlanRef.getKey());
        //prepare callback method for when this task is complete
        //Set the actual data
        mNewPlanRef.setValue(plan).addOnCompleteListener(new OnCompleteListener<Void>() {
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
