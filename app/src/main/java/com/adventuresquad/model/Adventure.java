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
    //private String mAdventureId;
    private String mAdventureTitle;

    //Other adventure attributes, e.g. location, tags

    //Location
    private double mLatitude;
    private double mLongitude;

    //Types that an adventure matches
    private List<AdventureType> mAdventureTypes;

    /**
     * Empty constructor (for marshalling)
     */
    public Adventure (){}

    /**
     * Default constructor
     *
     * @param adventureTitle The title of the adventure
     * @param latitude       Location latitude of the adventure
     * @param longitude      Location longitude of the adventure
     */
    public Adventure(String adventureTitle, double latitude, double longitude) {
        setAdventureTitle(adventureTitle);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    /**
     * Extended constructor
     * Still does not have image (which will be from Firebase storage)
     */
    public Adventure(String adventureTitle, double latitude, double longitude,
                     List<AdventureType> adventureTypes) {
        this(adventureTitle, latitude, longitude);
        setAdventureTypes(adventureTypes);
    }

    /*public String getAdventureId() {
        return mAdventureId;
    }*/

    public String getAdventureTitle() {
        return mAdventureTitle;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public List<AdventureType> getAdventureTypes() {
        return mAdventureTypes;
    }

    public void setAdventureTitle(String adventureTitle) {
        mAdventureTitle = adventureTitle;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public void setAdventureTypes(List<AdventureType> adventureTypes) {
        mAdventureTypes = adventureTypes;
    }

    //TODO - add image storage for the images

    //TODO - encapsulate the class (getters/setters)
}
