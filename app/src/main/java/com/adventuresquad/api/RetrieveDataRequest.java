package com.adventuresquad.api;

/**
 * A generic interface that allows classes to call an API and provide a callback.
 * Created by Harrison on 6/06/2017.
 */
public interface RetrieveDataRequest<Type> {
    public void onRetrieveData(Type data);
    public void onRetrieveDataFail(Exception e);
}
