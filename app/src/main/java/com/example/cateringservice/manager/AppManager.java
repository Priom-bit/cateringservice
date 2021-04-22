package com.example.cateringservice.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.cateringservice.Services;
import com.example.cateringservice.models.User;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class AppManager {
    private final String TAG = AppManager.class.getSimpleName();
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String UserInfoKey = "UserInfoKey";
    public static final String LoggedInKey = "LoggedInKey";

    public User userProfile = new User();

    private AppManager() {
        Log.v(TAG, "Services constructor created");
    }

    private static final AppManager instance = new AppManager();

    public static AppManager getInstance() {
        return instance;
    }

    public void setUserProfile(Context context) {
        User _user = getUserInfo(context);
        if (_user != null) {
            userProfile = _user;
        }
    }

    public void saveUserInfo(Context context, User user) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        Log.v(TAG, "Nirob test save user: " + userJson);
        editor.putString(AppManager.UserInfoKey, userJson);
        editor.apply();
    }

    public User getUserInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String userJson = sharedPreferences.getString(AppManager.UserInfoKey, "");
        Log.v(TAG, "Nirob test get user: " + userJson);
        User user = gson.fromJson(userJson, User.class);
        //Log.v(TAG, "Nirob test get user firstName: " + user.firstName);
        return user;
    }

    public void setLogIn(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putBoolean(AppManager.LoggedInKey, true);
        editor.apply();
    }

    public void setLogOut(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putBoolean(AppManager.LoggedInKey, false);
        editor.apply();
    }

    public boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Boolean isLoggedIn = sharedPreferences.getBoolean(AppManager.LoggedInKey, false);
        return isLoggedIn;
    }

}
