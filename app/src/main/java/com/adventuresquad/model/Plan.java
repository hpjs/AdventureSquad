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
    private String mPlanTitle; //The title of the plan. Default: Same as adventure title.
    private String mPlanImageUrl; //URI that points to the plan's image
    private long bookingDate; //Booking date as Unix time (milliseconds since 'epoch')

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

    public long getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(long bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getSquadId() {
        return mSquadId;
    }

    public void setSquadId(String squadId) {
        mSquadId = squadId;
    }

    public String getPlanTitle() {
        return mPlanTitle;
    }

    public void setPlanTitle(String planTitle) {
        mPlanTitle = planTitle;
    }

    public String getPlanImageUrl() {
        return mPlanImageUrl;
    }

    public void setPlanImageUrl(String planImageUrl) {
        mPlanImageUrl = planImageUrl;
    }
}
