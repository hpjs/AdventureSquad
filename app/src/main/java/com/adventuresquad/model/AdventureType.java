package com.adventuresquad.model;

import java.util.HashMap;

/**
 * Created by Harrison on 7/05/2017.
 */

/**
 * Enumerator for all types of adventures
 */
public enum AdventureType {
    HIKING,
    BEACHES,
    RAINFORESTS,
    CAMPING,
    ZIPLINING,
    SAILING,
    CYCLING,
    KAYAKING,
    SKATING,
    SURFING,
    CLIMBING;

    /**
     * Dictionary to hold mappings of enum to text string values
     */
    public static final HashMap<AdventureType, String> ADVENTURE_TYPE_STRING_DICTIONARY = new HashMap<AdventureType, String>() {
        {
            //TODO - Extract String resources in the correct way
            put(AdventureType.HIKING, "Hikes");
            put(AdventureType.BEACHES, "Beaches");
            put(AdventureType.RAINFORESTS, "Rainforests");
            put(AdventureType.CYCLING, "Cycling");
            put(AdventureType.KAYAKING, "Kayaking");
            put(AdventureType.CLIMBING, "Climbing");
            put(AdventureType.SURFING, "Surfing");
            put(AdventureType.SKATING, "Skating");
            put(AdventureType.CAMPING, "Camping");
            put(AdventureType.ZIPLINING, "Zip Lining");
            put(AdventureType.SAILING, "Sailing");
        }

        /*
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public String get(Object key) {
            return null;
        }

        @Override
        public String put(AdventureType key, String value) {
            return null;
        }

        @Override
        public String remove(Object key) {
            return null;
        }*/
    };
}
