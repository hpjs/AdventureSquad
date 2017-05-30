package com.adventuresquad.interfaces;

import android.net.Uri;

/**
 * Allows a UI object to provide a callback when requesting an image download URI
 * E.g. used as an anonymous inner class in onBindViewHolder to load images asynchronously
 * Created by Harrison on 25/05/2017.
 */
public interface RetrieveImageUriRequest {

    public void onRetrieveImageUri(Uri uri);

    public void onRetrieveImageUriFail(Exception e);
}
