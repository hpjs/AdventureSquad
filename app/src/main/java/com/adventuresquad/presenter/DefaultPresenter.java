package com.adventuresquad.presenter;

/**
 * Created by Harrison on 11/05/2017.
 */

/**
 * Default presenter class for duplicating to make new presenter objects
 */
public class DefaultPresenter {
    private DefaultPresenter mActivity;

    public DefaultPresenter(DefaultPresenter activity) {
        mActivity = activity;
    }
}
