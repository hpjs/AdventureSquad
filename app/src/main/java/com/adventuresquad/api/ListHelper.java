package com.adventuresquad.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides helper classes for managing FireBase 'hashmap lists'
 * This is because fastest way to store a list of data in FB is by using set of keys instead of values
 * Created by Harrison on 11/06/2017.
 */
public class ListHelper {

    /**
     * Takes the all keys from a hashmap and puts into a list
     * @param map The map to retrieve the String keys from
     * @return A list of all keys in the map
     */
    public static List<String> toList(Map<String, Boolean> map) {
        List<String> idList = new ArrayList<>();
        for (String key : map.keySet()) {
            idList.add(key);
        }
        return idList;
    }
}
