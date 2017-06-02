package com.adventuresquad.model;

import java.util.List;

/**
 * POJO for holding User details
 */
public class User {
    private String mUserName;
    private String mUserEmail;
    //To link this user object to the authentication
    //TODO - may not actually need this as a field itself here (may still be useful to have another copy though)
    private String mUserId;
    //Password stored in authentication, not in app

    //List of preferred adventure types
    private List<AdventureType> mPreferredAdventureType;


    /**
     * Empty constructor
     */
    public User() {}

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
}
