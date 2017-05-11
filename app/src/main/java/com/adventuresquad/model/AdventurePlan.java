package com.adventuresquad.model;

/**
 * Created by Harrison on 8/05/2017.
 */

import java.text.DateFormat;

/**
 * Intermediary class between User/Squad and adventure
 */
public class AdventurePlan {

    private int mAdventurePlanId;
    //The adventure that this plan is for
    private int mAdventureId;

    private DateFormat bookingDate;
}
