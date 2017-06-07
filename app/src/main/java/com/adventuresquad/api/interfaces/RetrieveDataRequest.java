package com.adventuresquad.api.interfaces;

/**
 * Generic interface that allows classes to retrieve data from an API and receive feedback asynchronously.
 * Created by Harrison on 6/06/2017.
 */
public interface RetrieveDataRequest<Type> {
    public void onRetrieveData(Type data);
    public void onRetrieveDataFail(Exception e);
}
