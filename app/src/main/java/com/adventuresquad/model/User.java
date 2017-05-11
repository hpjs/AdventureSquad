package com.adventuresquad.model;

import java.util.List;

/**
 * POJO for holding User details
 */
public class User {
    private String mUserName;
    private String mUserEmail;
    //To link this user object to the authentication
    private String mUserId;
    //Password stored in authentication, not in app

    //List of preferred adventure types
    private List<AdventureType> mPreferredAdventureType;

    //List of all of this user's planned adventures
    private List<Adventure> mAdventurePlans;

    /**
     * Minimum constructor
     */
    public User(String mUserId, String mUserEmail, String mUserName) {
        this.setUserId(mUserId);
        this.setUserEmail(mUserEmail);
        this.setUserName(mUserName);
    }

    /**
     * Full constructor
     */
    public User(String mUserId, String mUserEmail, String mUserName, List<AdventureType> mPreferredAdventureType) {
        this(mUserId, mUserEmail, mUserName);
        this.mPreferredAdventureType = mPreferredAdventureType;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public void setUserEmail(String mUserEmail) {
        this.mUserEmail = mUserEmail;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public List<AdventureType> getPreferredAdventureType() {
        return mPreferredAdventureType;
    }

    public void setPreferredAdventureType(List<AdventureType> mPreferredAdventureType) {
        this.mPreferredAdventureType = mPreferredAdventureType;
    }

    //May have to remove this if not needed
    public List<Adventure> getMyAdventures() {
        return mAdventurePlans;
    }

    //No public getter/setter for the adventure list. Rather have it get/set individual adventures
    /*
    public void setMyAdventures(List<Adventure> mMyAdventures) {
        this.mMyAdventures = mMyAdventures;
    }*/

    /**
     * Returns the user's specified AdventurePlan object
     * TODO - This method doesn't make sense. If you already had the adventurePlanId, you'd be able to look it up elsewhere
     * Instead make a method that returns an adventure from a plan id, or a plan from an adventure id
     * @param adventurePlanId the ID of the AdventurePlan in the planned adventures list
     * @return The adventure plan object
     */
    public AdventurePlan getAdventurePlan(int adventurePlanId) {
        //Look through list of planned adventures to find a certain booking object
        //TODO - implement getAdventurePlan
        return null;
    }

    /**
     * Gets the most recent planned Adventure that matches the given AdventureId
     * @param adventureId the id of the adventure that you want to get
     * @return The specific adventure that you are looking for. Null if no match found.
     */
    public Adventure getAdventure(int adventureId) {
        //TODO - implement getAdventure
        return null;
    }
}
