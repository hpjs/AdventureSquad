package com.adventuresquad.presenter.interfaces;

/**
 * Used by an API to communicate with a presenter
 * Created by Harrison on 30/05/2017.
 */
public interface LogoutApiPresenter {
    public void logout();

    public void onLogoutSuccess();

    public void onLogoutFail(Exception e);

}
