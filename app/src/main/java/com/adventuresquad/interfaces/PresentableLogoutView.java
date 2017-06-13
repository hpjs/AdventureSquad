package com.adventuresquad.interfaces;

import com.adventuresquad.model.User;

/**
 * Created by Harrison on 30/05/2017.
 */
public interface PresentableLogoutView extends PresentableItemView<User> {

    /**
     * After logout is successful, navigate to login screen
     */
    public void completeLogout();
}
