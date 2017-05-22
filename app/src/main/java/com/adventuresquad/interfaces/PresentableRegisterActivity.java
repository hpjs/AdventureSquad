package com.adventuresquad.interfaces;

/**
 * Created by Harrison on 22/05/2017.
 */

public interface PresentableRegisterActivity extends Presentable {

    public void onRegisterSuccess();

    public void onRegisterFail();

    /**
     * Called when validation of registration details fails
     */
    public void validationFail();

}
