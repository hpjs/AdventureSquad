package com.adventuresquad.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Squad object that can hold users and trip plans
 * Created by Harrison on 8/05/2017.
 */
public class Squad {
    //Note: Uses the string class for UUID for items
    private String mSquadId;
    private String mSquadName;
    private List<String> mSquadUsers;
    private List<String> mSquadPlans;

    /**
     * Empty constructor (for FireBase / other marshal & unmarshalling)
     */
    public Squad() {}

    public String getSquadId() {
        return mSquadId;
    }

    public void setSquadId(String squadId) {
        this.mSquadId = squadId;
    }

    public String getSquadName() {
        return mSquadName;
    }

    public void setSquadName(String squadName) {
        this.mSquadName = squadName;
    }

    public List<String> getSquadUsers() {
        return mSquadUsers;
    }

    /**
     * Setter for users who are in this squad
     * @param squadUsers
     */
    public void setSquadUsers(List<String> squadUsers) {
        mSquadUsers = squadUsers;
    }

    public void addSquadUser(String userId) {
        mSquadUsers.add(userId);
    }

    public List<String> getSquadPlans() {
        return mSquadPlans;
    }

    public void setPlans(List<String> squadPlans) {
        mSquadPlans = squadPlans;
    }
}
