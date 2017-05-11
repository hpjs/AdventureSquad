package com.adventuresquad.model;

import java.util.List;

/**
 * Class to represent a single adventure that someone can go on
 * Created by Harrison Smith 11/05/2017
 */
public class Adventure {

    //Should have an ID to uniquely identify a single adventure
    //Problem - could book the same adventure multiple times
    //Solution - have an intermediary 'adventure bookings' object width the date etc

    private long mAdventureId;
    private String mAdventureName;

    //Other adventure attributes, e.g. location, tags

    //Location
    private long mLatitude;
    private long mLongitude;

    //Types that an adventure matches
    private List<AdventureType> mAdventureTypes;

    //TODO - encapsulate the class
}
