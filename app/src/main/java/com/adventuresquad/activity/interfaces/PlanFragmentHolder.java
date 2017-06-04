package com.adventuresquad.activity.interfaces;

import android.support.v4.app.Fragment;

/**
 * Specific fragment holder for the 'Plan Adventure' flow
 * Allows the fragments to communicate specific data / actions back to the activity
 * Created by Harrison on 2/06/2017.
 */
public interface PlanFragmentHolder extends SwipeFragmentHolder {

    void onNextButtonClicked(int currentSection, PlanFragment fragment);
}
