package com.ex.server.utils;

import java.util.Collections;
import java.util.HashSet;

/**
 * Created by roy on 03/02/2015.
 */
public class Sets {
    public static <T>HashSet<T> getNewHashSetWithValues(T ... val){
        assert val != null;

        HashSet<T> set = new HashSet<>(val.length);
        Collections.addAll(set, val);

        return set;
    }
}
