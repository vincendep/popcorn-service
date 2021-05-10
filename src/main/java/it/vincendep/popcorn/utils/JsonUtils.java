package it.vincendep.popcorn.utils;

import org.springframework.boot.configurationprocessor.json.JSONObject;

public class JsonUtils {

    public static class JsonParseException extends RuntimeException {

        JsonParseException(String message) {
            super(message);
        }

        JsonParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(String json, String path) {
        try {
            String[] keys = path.split("\\.");
            JSONObject jsonObject = new JSONObject(json);
            Object obj = jsonObject.get(keys[0]);
            for (int i = 1; obj != null && i < keys.length; i++) {
                obj = ((JSONObject) obj).get(keys[i]);
            }
            return (T) obj;
        } catch (Exception e) {
            throw new JsonParseException("Error reading property at \"" + path + "\" from json: " + json, e);
        }
    }
}
