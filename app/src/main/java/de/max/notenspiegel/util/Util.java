package de.max.notenspiegel.util;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.max.notenspiegel.structure.Subject;

public final class Util {
    private Util() {
    }

    public static void save(Subject subject, SharedPreferences preferences) {
        save(subject, subject.getKey(), preferences);
    }

    public static void setKeys(List<String> keys, String key, SharedPreferences preferences) {
        setKeys( new HashSet<>(keys), key, preferences);
    }

    public static void setKeys(Set<String> keys, String key, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, keys);
        editor.apply();
    }

    public static <T> void save(T t, String key, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, toJson(t));
        editor.apply();
    }

    public static <T> T load(String key, SharedPreferences preferences, Class<T> tClass) {

        return fromJson(preferences.getString(key, ""), tClass);
    }

    public static <T> List<T> loadAll(String keysLocation, SharedPreferences preferences, Class<T> tClass) {
        if (!preferences.contains(keysLocation)) {
            return new ArrayList<>();
        }
        Set<String> keys = preferences.getStringSet(keysLocation, new HashSet<>());
        List<T> tList = new ArrayList<>();
        for (String key : keys) {
            if (!preferences.contains(key)) {
                continue;
            }
            tList.add(load(key, preferences, tClass));
        }
        return tList;
    }

    public static <T> String toJson(T t) {
        Gson gson = new Gson();
        System.out.println(gson.toJson(t));
        return gson.toJson(t);
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        Gson gson = new Gson();
        return gson.fromJson(json, tClass);
    }
}
