package com.adventuresquad.activity.interfaces;

/**
 * Interface for swiping fragments to communicate with their activity (and therefore the presenter)
 * Created by Harrison on 2/06/2017.
 */
public interface SwipeFragmentHolder {

    /**
     * Used when the 'next' button is clicked on any of the fragments
     * If it's the final fragment in the set, the Activity should finish the swipe fragment flow
     */
    public void onNextButtonClicked();

}
