package it.vincendep.popcorn.util;

public class ArrayUtils {

    public static <T> boolean isEmpty(T[] t) {
        return t == null || t.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] t) {
        return ! isEmpty(t);
    }
}
