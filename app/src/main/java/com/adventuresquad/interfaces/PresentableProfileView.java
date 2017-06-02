package com.adventuresquad.interfaces;

/**
 * Created by Harrison on 30/05/2017.
 */

public interface PresentableProfileView extends Presentable {

    /**
     * After logout is successful, navigate to login screen
     */
    public void completeLogout();
}
