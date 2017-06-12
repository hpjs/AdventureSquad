package com.adventuresquad.api.interfaces;

/**
 * Generic interface that allows classes to retrieve data from an API and receive feedback asynchronously.
 * Created by Harrison on 6/06/2017.
 */
public interface RetrieveDataRequest<Type> {
    public void onRetrieveData(Type data);
    public void onRetrieveDataFail(Exception e);

    /**
     * Provides a custom exception for when no data is found
     */
    class NoDataException extends Exception {
        public NoDataException(){ super(); }
        public NoDataException(String message) {
            super(message);
        }
        public NoDataException(Throwable cause) { super(cause); }
        public NoDataException(String message, Throwable cause) { super(message, cause); }
    }
}
