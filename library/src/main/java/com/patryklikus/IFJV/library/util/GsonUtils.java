/* Copyright Patryk Likus All Rights Reserved. */
package com.patryklikus.IFJV.library.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javax.annotation.Nullable;

public class GsonUtils {
    @Nullable
    public static JsonArray getArrayMemberSafe(JsonObject json, String member) {
        return getArraySafe(json.get(member));
    }

    @Nullable
    public static JsonArray getArraySafe(JsonElement json) {
        if (json == null) {
            return null;
        }
        return json.isJsonArray() ? json.getAsJsonArray() : null;
    }

    @Nullable
    public static JsonObject getObjectMemberSafe(JsonObject json, String member) {
        return getObjectSafe(json.get(member));
    }

    @Nullable
    public static JsonObject getObjectSafe(JsonElement json) {
        if (json == null) {
            return null;
        }
        return json.isJsonObject() ? json.getAsJsonObject() : null;
    }

    @Nullable
    public static Boolean getBooleanMemberSafe(JsonObject json, String member) {
        return getBooleanSafe(json.get(member));
    }

    @Nullable
    public static Boolean getBooleanSafe(JsonElement json) {
        if (json == null || !json.isJsonPrimitive()) {
            return null;
        }
        JsonPrimitive primitive = json.getAsJsonPrimitive();
        return primitive.isBoolean() ? primitive.getAsBoolean() : null;
    }

    @Nullable
    public static Integer getIntegerMemberSafe(JsonObject json, String member) {
        return getIntegerSafe(json.get(member));
    }

    @Nullable
    public static Integer getIntegerSafe(JsonElement json) {
        if (json == null || !json.isJsonPrimitive()) {
            return null;
        }
        JsonPrimitive primitive = json.getAsJsonPrimitive();
        return primitive.isNumber() ? primitive.getAsInt() : null;
    }

    @Nullable
    public static Long getLongMemberSafe(JsonObject json, String member) {
        return getLongSafe(json.get(member));
    }

    @Nullable
    public static Long getLongSafe(JsonElement json) {
        if (json == null || !json.isJsonPrimitive()) {
            return null;
        }
        JsonPrimitive primitive = json.getAsJsonPrimitive();
        return primitive.isNumber() ? primitive.getAsLong() : null;
    }

    @Nullable
    public static Double getDoubleMemberSafe(JsonObject json, String member) {
        return getDoubleSafe(json.get(member));
    }

    @Nullable
    public static Double getDoubleSafe(JsonElement json) {
        if (json == null || !json.isJsonPrimitive()) {
            return null;
        }
        JsonPrimitive primitive = json.getAsJsonPrimitive();
        return primitive.isNumber() ? primitive.getAsDouble() : null;
    }

    @Nullable
    public static String getStringMemberSafe(JsonObject json, String member) {
        return getStringSafe(json.get(member));
    }

    @Nullable
    public static String getStringSafe(JsonElement json) {
        if (json == null || !json.isJsonPrimitive()) {
            return null;
        }
        return getStringSafe(json.getAsJsonPrimitive());
    }

    @Nullable
    public static String getStringSafe(JsonPrimitive json) {
        if (json == null || !json.isString()) {
            return null;
        }
        return json.getAsString();
    }
}