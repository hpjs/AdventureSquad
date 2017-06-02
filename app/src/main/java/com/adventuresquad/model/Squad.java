package com.adventuresquad.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Squad object that can hold users and trip plans
 * Created by Harrison on 8/05/2017.
 */
public class Squad {

    private long mSquadId;
    private String mSquadName;
    //Note: Uses the string class for UUID of user
    private ArrayList<String> mSquadUsers;
    private List<String> mSquadPlans;

    public long getSquadId() {
        return mSquadId;
    }

    public void setSquadId(long squadId) {
        this.mSquadId = squadId;
    }

    public String getSquadName() {
        return mSquadName;
    }

    public void setSquadName(String squadName) {
        this.mSquadName = squadName;
    }

    public ArrayList<User> getSquadUsers() {
        return mSquadUsers;
    }

    public void setSquadUsers(ArrayList<User> squadUsers) {
        mSquadUsers = squadUsers;
    }

    public List<String> getPlans() {
        return mPlans;
    }

    public void setPlans(List<String> plans) {
        mPlans = plans;
    }
}
