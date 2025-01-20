package com.dolthhaven.dolt_mod_how.core.util;

public class Array {
    @SafeVarargs
    public static <T> T[] of(T... elements) {
        return elements;
    }
}
