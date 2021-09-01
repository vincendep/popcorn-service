package it.vincendep.popcorn.integration.job.common;

import java.util.Map;
import java.util.stream.Collectors;

public class JobUtils {

    public static String toJobParameterString(Map<String, String> map) {
        return map.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(","));
    }
}
