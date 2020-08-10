package com.github.gleans.utils;

import java.util.Collection;

public class ObjectUtils {

    public static boolean isEmpty(Collection<?> coll) {
        return null == coll || coll.size() == 0;
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }
}