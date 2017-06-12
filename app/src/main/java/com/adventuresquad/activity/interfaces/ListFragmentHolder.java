package com.adventuresquad.activity.interfaces;

/**
 * Implemented by an activity that holds fragments that have clickable list items
 * Created by Harrison on 12/06/2017.
 */
public interface ListFragmentHolder<Type> {
    public void onItemClicked(Type object, int position);
}
