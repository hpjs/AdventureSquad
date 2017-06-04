package com.adventuresquad.model;

import java.util.Date;

/**
 * Intermediary class between User/Squad and adventure
 * Created by Harrison on 8/05/2017.
 */
public class Plan {

    private String mPlanId;
    private String mAdventureId; //The adventure that this plan is for
    private String mSquadId; //The ID of the squad that this trip is for
    private Date bookingDate;

    public Plan() {

    }

    public String getPlanId() {
        return mPlanId;
    }

    public void setPlanId(String planId) {
        mPlanId = planId;
    }

    public String getAdventureId() {
        return mAdventureId;
    }

    public void setAdventureId(String adventureId) {
        mAdventureId = adventureId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getSquadId() {
        return mSquadId;
    }

    public void setSquadId(String squadId) {
        mSquadId = squadId;
    }
}