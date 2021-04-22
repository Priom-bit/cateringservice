package com.example.cateringservice.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.cateringservice.Services;

public class AppManager {
    private final String TAG = AppManager.class.getSimpleName();
    public static final String MyPREFERENCES = "MyPrefs" ;

    private AppManager() {
        Log.v(TAG, "Services constructor created");
    }

    private static final AppManager instance = new AppManager();

    public static AppManager getInstance() {
        return instance;
    }

    public void setLogIn(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putBoolean("LoggedInKey", true);
        editor.apply();
    }

    public void setLogOut(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putBoolean("LoggedInKey", false);
        editor.apply();
    }

    public boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Boolean isLoggedIn = sharedPreferences.getBoolean("LoggedInKey", false);
        return isLoggedIn;
    }

}
