package com.adventuresquad.presenter;

/**
 * Created by Harrison on 22/05/2017.
 * Allows communication between a presenter and the AdventureApi
 * Nutshell: Provides callback methods for the Api to use when stuff happens
 * Goal: Should not be any actual database code in the presenter. Makes code swapping easier.
 */
public interface LoginApiPresenter {

    public void onLoginSuccess();

    public void onLoginFail();

}
