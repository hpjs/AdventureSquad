package com.adventuresquad.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.adventuresquad.model.Adventure;
import com.adventuresquad.presenter.interfaces.AdventureApiPresenter;
import com.google.android.gms.tasks.OnFailureListener;
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
    //This is literally the identifier for an object in the FireBase tree
    //So every object underneath this needs a new unique identifier (which you can generate)
    //Every object in the database is just a key/value pair (literally just a huge JSON file)
    //'Database' here just means that it holds all of the individual adventure objects underneath it
    public static final String ADVENTURES_LIST = "adventures";

    public static final String DEBUG_ADVENTURE_API = "adventure_api";

    private FirebaseDatabase mDatabaseInstance;
    private DatabaseReference mAdventuresDatabase;

    /**
     * Constructor, initialises database references
     */
    public AdventureApi() {
        mDatabaseInstance = FirebaseDatabase.getInstance();
        mAdventuresDatabase = mDatabaseInstance.getReference(ADVENTURES_LIST);
    }

    /**
     * Put updated adventure object at a given location (NOTE: Also has the power to create new ones if ID is not found)
     * TODO - only allow it to update an existing entry, instead of create new ones
     * @param adventure The updated Adventure object to store in the DB
     * @param adventureId The key of the adventure object (data point to put the adventure)
     */
    public void putAdventure(Adventure adventure, String adventureId) {
        DatabaseReference mAdventureRef = mAdventuresDatabase.child(adventureId);
        mAdventureRef.setValue(adventure).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(DEBUG_ADVENTURE_API, "Error - could not update Adventure object");
            }
        });
    }

    /**
     * Adds adventure to database with unique ID automatically
     * @param adventure Adventure to store in DB
     */
    public void putAdventure(Adventure adventure) {
        //Creates data point, adds unique key to the Adventure
        // and then stores adventure object at new data point
        DatabaseReference mNewAdventureRef = mAdventuresDatabase.push();
        adventure.setAdventureId(mNewAdventureRef.getKey());
        //prepare callback method for when this task is complete
        //Set the actual data
        mNewAdventureRef.setValue(adventure);

        /*Currently don't need to listen for create completion
        mNewAdventureRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO - this might not actually work as expected
                //It apparently triggers when it's actually attached for the first time
                //As well as for any subsequent data changes
                //dataSnapshot.getKey();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    /**
     * Stores a list of adventures in the database
     * @param adventureList
     */
    public void putAdventureList(List<Adventure> adventureList) {
        for (Adventure a : adventureList) {
            putAdventure(a);
        }
    }

    /**
     * Retrieves a specific adventure according to it's UUID
     * @param callback The presenter to call methods on when a result is reached
     * @param id                The UUID of the adventure to retrieve
     */
    public void getAdventure(final RetrieveDataRequest<Adventure> callback, String id) {
        DatabaseReference adventure = mAdventuresDatabase.child(id);
        adventure.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Marshall adventure into an adventure object
                Adventure retrievedAdventure = dataSnapshot.getValue(Adventure.class);
                callback.onRetrieveData(retrievedAdventure);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onRetrieveDataFail(databaseError.toException());
            }
        });
    }

    /**
     * Retrieves a non-specific list of adventures from the online database
     *
     * @param callback What to notify when retrieve is complete
     */
    public void getAdventureList(final RetrieveDataRequest<List<Adventure>> callback) {
        mAdventuresDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            //Data retrieved
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Adventure> list = new ArrayList<Adventure>();
                //Loop over a list of retrieved adventures
                for (DataSnapshot adventureSnapshot : dataSnapshot.getChildren()) {
                    Adventure a = adventureSnapshot.getValue(Adventure.class);
                    list.add(a);
                }
                callback.onRetrieveData(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onRetrieveDataFail(databaseError.toException());
            }
        });

    }

    /*
     * Manually marshals the given data snapshot into an adventure object (should not be needed)
     * @param adventureSnapshot Snapshot of an adventure to marshal
     * @return
     */
//    public Adventure marshalData(DataSnapshot adventureSnapshot) {
//        String name = (String) adventureSnapshot.child("adventureTitle").getValue();
//        double latitude = (double) adventureSnapshot.child("latitude").getValue();
//        double longitude = (double) adventureSnapshot.child("longitude").getValue();
//        return new Adventure(name, latitude, longitude);
//    }

}
