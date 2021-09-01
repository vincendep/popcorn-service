package it.vincendep.popcorn.util;

import java.util.Arrays;
import java.util.Objects;

public class ArrayUtils {

    public static <T> boolean isEmpty(T[] t) {
        return t == null || t.length == 0 || Arrays.stream(t).allMatch(Objects::isNull);
    }

    public static <T> boolean isNotEmpty(T[] t) {
        return ! isEmpty(t);
    }
}
