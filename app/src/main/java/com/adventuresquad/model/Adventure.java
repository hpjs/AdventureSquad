package com.adventuresquad.model;

/**
 * Class to represent a single adventure that someone can go on
 * Created by Harrison Smith 11/05/2017
 */
public class Adventure {

    //Should have an ID to uniquely identify a single adventure
    //Problem - could book the same adventure multiple times
    //Solution - have an intermediary 'adventure bookings' object width the date etc

    private int mAdventureId;
    private String mAdventureName;

    //Other adventure attributes, e.g. location,

    //Location
    private long mLatitude;
    private long mLongitude;
}
