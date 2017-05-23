package com.adventuresquad.model;

import android.net.Uri;

import java.util.List;

/**
 * Class to represent a single adventure that someone can go on
 * Created by Harrison Smith 11/05/2017
 */
public class Adventure {

    //Should have an ID to uniquely identify a single adventure
    //Problem - could book the same adventure multiple times
    //Solution - have an intermediary 'adventure bookings' object width the date etc

    //Adventure ID should be received from FireBase, has to be a string (hexadecimal UUID)
    private String mAdventureId;
    private String mAdventureTitle;
    private String mAdventureDetail;

    //Image URI
    private String mAdventureImageUri;

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
    public Adventure(String adventureTitle, String adventureDetail, double latitude, double longitude) {
        setAdventureTitle(adventureTitle);
        setAdventureDetail(adventureDetail);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    /**
     * Extended constructor
     * Still does not have image (which will be from Firebase storage)
     */
    public Adventure(String adventureTitle, String adventureDetail, double latitude, double longitude,
                     List<AdventureType> adventureTypes) {
        this(adventureTitle, adventureDetail, latitude, longitude);
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

    public String getAdventureId() {
        return mAdventureId;
    }

    public void setAdventureId(String adventureId) {
        mAdventureId = adventureId;
    }

    public String getAdventureDetail() {
        return mAdventureDetail;
    }

    public void setAdventureDetail(String adventureDetail) {
        mAdventureDetail = adventureDetail;
    }

    public String getAdventureImageUri() {
        return mAdventureImageUri;
    }

    public void setAdventureImageUri(String adventureImageUri) {
        mAdventureImageUri = adventureImageUri;
    }

    //TODO - add image storage for the images

    //TODO - encapsulate the class (getters/setters)
}
