package com.adventuresquad.api;

/**
 * Generic interface that allows classes to retrieve data from an API and receive feedback asynchronously.
 * Created by Harrison on 6/06/2017.
 */
public interface StoreDataRequest<Type> {
    public void onStoreData(Type data);
    public void onStoreDataFail(Exception e);
}
