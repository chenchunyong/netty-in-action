package org.netty.util;

import com.google.gson.Gson;

public class JsonUtil {
    private static final Gson GSON = new Gson();

    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        return GSON.fromJson(jsonStr, clazz);
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }
}
