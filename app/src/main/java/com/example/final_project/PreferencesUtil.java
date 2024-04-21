package com.example.final_project;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtil {

    private static final String PREFS_NAME = "PetAppPreferences";
    private static final String SELECTED_SHELTER_ID_KEY = "selectedShelterId";

    public static void setSelectedShelterId(Context context, String shelterId) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SELECTED_SHELTER_ID_KEY, shelterId);
        editor.apply();
    }

    public static String getSelectedShelterId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getString(SELECTED_SHELTER_ID_KEY, null);
    }
}

