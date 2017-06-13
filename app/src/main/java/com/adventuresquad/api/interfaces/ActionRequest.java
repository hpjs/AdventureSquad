package com.adventuresquad.api.interfaces;

/**
 * Allows requests to be made to the API that don't store/retrieve any data.
 * Created by Harrison on 13/06/2017.
 */
public interface ActionRequest {
    public void onActionComplete();
    public void onActionFail(Exception e);
}
