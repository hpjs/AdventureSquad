package com.adventuresquad.interfaces;

/**
 * Provides an interface for a presenter to present to a registration view
 * Created by Harrison on 22/05/2017.
 */
public interface PresentableRegisterView extends Presentable {

    public void registrationComplete();

    public void onRegisterFail();

    /**
     * Called when validation of registration details fails
     */

    public void validationFail(PresentableRegisterView.ValidationError reason);

    public enum ValidationError {
        NO_NAME,
        NO_EMAIL,
        EMAIL_TAKEN,
        REGISTER_FAIL,
        PASSWORD_MISMATCH;
    }

}
