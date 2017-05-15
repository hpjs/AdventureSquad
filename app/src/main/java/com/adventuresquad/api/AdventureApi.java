package com.adventuresquad.api;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * API class to get and set individual or lists of adventures from a data source (e.g. Firebase)
 * Created by Harrison on 13/05/2017.
 * TODO - Convert other API classes to match this architecture (dependency injection)
 */
public class AdventureApi {

    public static final String ADVENTURE_DATABASE_LOCATION = "adventure";

    private FirebaseDatabase mDatabaseInstance;
    private DatabaseReference mAdventureRef;

    public AdventureApi() {
        mDatabaseInstance = FirebaseDatabase.getInstance();
        mAdventureRef = mDatabaseInstance.getReference(ADVENTURE_DATABASE_LOCATION);
    }

    public void testWriteData() {
        mAdventureRef.setValue("My first adventure!");
    }

    //Pass in a listener to do the actual stuff when data is read
    public void testReadData(ValueEventListener listener) {
        //TODO - Confirm that database ref is initialised first

        //
        mAdventureRef.addListenerForSingleValueEvent(listener);
    }

    /* SAmple code to get data
    ValueEventListener postListener = new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // Get Post object and use the values to update the UI
        Post post = dataSnapshot.getValue(Post.class);
        // ...
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Getting Post failed, log a message
        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
        // ...
    }
};
mPostReference.addValueEventListener(postListener);
    * */

    //Or only get data once:
    //addListenerForSingleValueEvent() method

}
