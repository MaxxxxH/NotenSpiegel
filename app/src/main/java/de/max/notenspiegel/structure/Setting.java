package de.max.notenspiegel.structure;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

import de.max.notenspiegel.util.Util;

public class Setting implements Serializable {
    public static final String KEY = "settings";
    private static final int MIN = 0;
    private static final int MAX = 100;
    private static final int DEFAULT_GOOD = 75;
    private static final int DEFAULT_SUFFICIENT = 50;
    private String name;
    private int maxPaper;

    private int good;
    private int sufficient;

    public Setting(String name, int maxPaper, int good, int sufficient) {
        this.name = name;
        this.maxPaper = maxPaper;
        this.good = good;
        this.sufficient = sufficient;
    }

    public String getKey() {
        return KEY + "_" + name;
    }

    public static Setting DEFAULT = new Setting("", 0, DEFAULT_GOOD, DEFAULT_SUFFICIENT);
    private static Setting defaultSetting;

    private boolean valid(int value) {
        return value >= MIN && value <= MAX;
    }

    public boolean validGood() {
        return validGood(this.good);
    }

    public boolean validGood(int value) {
        if (!valid(value)) {
            return false;
        }
        return value > sufficient;
    }
    public boolean validSufficient() {
        return validSufficient(this.sufficient);
    }

    public boolean validSufficient(int value) {
        if (!valid(value)) {
            return false;
        }
        return value < good;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPaper() {
        return maxPaper;
    }

    public void setMaxPaper(int maxPaper) {
        this.maxPaper = maxPaper;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        if (validGood(good)) {
            this.good = good;
        }
    }

    public int getSufficient() {

        return sufficient;
    }

    public void setSufficient(int sufficient) {
        if (validSufficient(sufficient))
            this.sufficient = sufficient;
    }

    public static Setting getDefaultSettings(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        if (preferences == null) {
            throw new NullPointerException("preferences == null");
        }
        if (preferences.contains(DEFAULT.getKey())) {
            defaultSetting = Util.load(DEFAULT.getKey(), preferences, Setting.class);
        } else {
            Util.save(DEFAULT, DEFAULT.getKey(), preferences);
            defaultSetting = DEFAULT;
        }
        return defaultSetting;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "name='" + name + '\'' +
                ", maxPaper=" + maxPaper +
                ", good=" + good +
                ", sufficient=" + sufficient +
                '}';
    }
}
