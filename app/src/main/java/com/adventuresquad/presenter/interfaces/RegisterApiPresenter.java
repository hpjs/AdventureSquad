package com.adventuresquad.presenter.interfaces;

/**
 * Created by Harrison on 22/05/2017.
 */
public interface RegisterApiPresenter {

    //Made redundant by newer onRegisterSuccess
    //public void onRegisterSuccess(String userId);

    void onRegisterSuccess(String userId, String userEmail);

    public void onRegisterFail(Exception exception);

}
