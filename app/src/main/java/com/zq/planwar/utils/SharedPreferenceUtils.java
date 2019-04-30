package com.zq.planwar.utils;

import android.annotation.SuppressLint;

import com.zq.planwar.PlanWarApplication;
import com.zq.planwar.core.options.NamePair;

public class SharedPreferenceUtils {

    public static final String FILE_NAME = "config";

    @SuppressLint("ApplySharedPref")
    public static void save(NamePair key, String value) {
        PlanWarApplication.get().getSharedPreferences(FILE_NAME, 0)
                .edit().putString(key.toString(), value)
                .commit();
    }

    public static String get(NamePair key,String defaultValue) {
        return PlanWarApplication.get().getSharedPreferences(FILE_NAME, 0)
                .getString(key.toString(),defaultValue);
    }
}
