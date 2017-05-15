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

    //Adventure ID should be recieved from firebase, has to be a string (hexadecimal UUID)
    private String mAdventureId;
    private String mAdventureTitle;

    //Other adventure attributes, e.g. location, tags

    //Location
    private long mLatitude;
    private long mLongitude;

    //Types that an adventure matches
    private List<AdventureType> mAdventureTypes;

    /**
     * Default constructor
     *
     * @param adventureTitle The title of the adventure
     * @param latitude       Location latitude of the adventure
     * @param longitude      Location longitude of the adventure
     */
    public Adventure(String adventureTitle, long latitude, long longitude) {
        mAdventureTitle = adventureTitle;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    /**
     * Extended constructor
     * Still does not have image (which will be from Firebase storage)
     */
    public Adventure(String adventureTitle, long latitude, long longitude,
                     List<AdventureType> adventureTypes) {
        this(adventureTitle, latitude, longitude);
        mAdventureTypes = adventureTypes;
    }

    public String getAdventureId() {
        return mAdventureId;
    }

    public String getAdventureTitle() {
        return mAdventureTitle;
    }

    public long getLatitude() {
        return mLatitude;
    }

    public long getLongitude() {
        return mLongitude;
    }

    public List<AdventureType> getAdventureTypes() {
        return mAdventureTypes;
    }

    //TODO - add image storage for the images

    //TODO - encapsulate the class (getters/setters)
}
