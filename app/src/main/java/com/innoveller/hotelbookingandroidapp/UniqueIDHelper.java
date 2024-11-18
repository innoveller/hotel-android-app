package com.innoveller.hotelbookingandroidapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class UniqueIDHelper {
    private static final String PREFS_NAME = "PREFS_NAME";
    private static final String APP_USER_TOKEN = "APP_USER_TOKEN";

    public static String getAppUserToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String uniqueID = sharedPreferences.getString(APP_USER_TOKEN, null);
        if (uniqueID == null) {
            uniqueID = UUID.randomUUID().toString();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(APP_USER_TOKEN, uniqueID);
            editor.apply();
        }

        return uniqueID;
    }
}
