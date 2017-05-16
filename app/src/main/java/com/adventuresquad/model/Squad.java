package com.adventuresquad.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Squad object that can hold users and trip plans
 * Created by Harrison on 8/05/2017.
 */
public class Squad {

    private long squadId;
    private String squadName;
    //Note: Uses the string class for UUID of user
    private ArrayList<User> mSquadUsers;

    private List<AdventurePlan> mPlannedAdventures;
}
