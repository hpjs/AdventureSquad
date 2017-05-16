package com.adventuresquad.api;

import com.adventuresquad.model.Adventure;
import com.adventuresquad.presenter.AdventureApiPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * API class to get and set individual or lists of adventures from a data source (e.g. Firebase)
 * Created by Harrison on 13/05/2017.
 * TODO - Convert other API classes to match this architecture (dependency injection)
 */
public class AdventureApi {

    //NOTE: This key is not really the same thing as it is in SQL.
    //This is literally the identifier for an object
    //So every object underneath this needs a new unique identifier (which you can generate)
    //Every object in the database is just a key/value pair (literally just a big JSON file)
    //'Database' here just means that it holds all of the individual adventure objects underneath it
    public static final String ADVENTURES_DATABASE = "adventures";

    private FirebaseDatabase mDatabaseInstance;
    private DatabaseReference mAdventuresDatabase;

    public AdventureApi() {
        mDatabaseInstance = FirebaseDatabase.getInstance();
        mAdventuresDatabase = mDatabaseInstance.getReference(ADVENTURES_DATABASE);
    }

    public void testWriteData() {
        //NOTE: This directly sets the value of the 'adventures' object in the database!
        //So it ends up being a key of 'adventures' and a value of "My First Adventure!"
        //It's basically a literal representation of a JSON or XML file in a database.
        mAdventuresDatabase.setValue("My first adventure!");
    }

    //Pass in a listener to do the actual stuff when data is read
    public void testReadData(ValueEventListener listener) {
        //TODO - Confirm that database ref is initialised first

        //
        mAdventuresDatabase.addListenerForSingleValueEvent(listener);
    }

    //TODO - test this method
    public void putAdventure(Adventure adventure, String key) {
        //TODO - have it generate a unique ID to be the key value for this object
        DatabaseReference mNewAdventureRef = mAdventuresDatabase.child("UNIQUE_ID_HERE");
        mNewAdventureRef.setValue(adventure);
    }

    public void putAdventure(Adventure adventure) {
        //TODO - have it generate a unique ID to be the key value for this object
        putAdventure(adventure, "INSERT_UNIQUE_ID_HERE");
    }

    //TODO - test this method
    public void putAdventureList(List<Adventure> adventureList) {
        int i = 12340;
        for (Adventure a : adventureList) {
            putAdventure(a, "MOCK_UUID_" + i);
            i++;
        }
    }

    /**
     * Retrieves a specific adventure according to it's UUID
     *
     * @param callbackPresenter The presenter to call methods on when a result is reached
     * @param id                The UUID of the adventure to retrieve
     */
    public void getAdventure(final AdventureApiPresenter callbackPresenter, String id) {
        DatabaseReference adventure = mAdventuresDatabase.child(id);
        adventure.addListenerForSingleValueEvent(new ValueEventListener() {
            //TODO - test this method
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Marshall adventure into an adventure object
                Adventure retrievedAdventure = dataSnapshot.getValue(Adventure.class);
                callbackPresenter.onRetrieveAdventure(retrievedAdventure);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callbackPresenter.onRetrieveError(databaseError.toException());
            }
        });
    }

    /**
     * Retrieves a non-specific list of adventures from the online database
     *
     * @param callbackPresenter The presenter to call back methods on when complete
     */
    public void getAdventureList(final AdventureApiPresenter callbackPresenter) {
        //TODO - try and convert this to a normal firebase listener to see what happens
        mAdventuresDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            //Data retrieved
            //TODO - test this method
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Adventure> list = new ArrayList<Adventure>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Adventure a = child.getValue(Adventure.class);
                    list.add(a);
                }
                callbackPresenter.onRetrieveAdventureList(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callbackPresenter.onRetrieveError(databaseError.toException());
            }
        });

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
