package com.adventuresquad.interfaces;

/**
 * Provides extra methods specific to fragments that contain a selectable list
 * Created by Harrison on 9/06/2017.
 */
public interface PresentableListFragment<Type> extends PresentableListView<Type> {

    /**
     * Called when the presenter has finished logic and should move on to next fragment
     */
    public void onCompleteFragment();

    //Note: removed this method for the time being.
    //public void onCompleteFragment(Type completeData);
}
