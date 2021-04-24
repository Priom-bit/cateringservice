package com.example.cateringservice.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.cateringservice.Services;
import com.example.cateringservice.models.ProductInfo;
import com.example.cateringservice.models.User;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppManager {
    private final String TAG = AppManager.class.getSimpleName();
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String UserInfoKey = "UserInfoKey";
    public static final String LoggedInKey = "LoggedInKey";
    public static final String OrderNumberKey = "OrderNumberKey";

    public User userProfile = new User();
    public List<ProductInfo> selectedDrinksList = new ArrayList<>();
    public List<ProductInfo> selectedBreakfastList = new ArrayList<>();
    public List<ProductInfo> selectedLunchList = new ArrayList<>();

    public String gaid = "00000000";

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

    public void setOrderNumber(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int orderNumber = sharedPreferences.getInt(AppManager.OrderNumberKey, 1);

        editor.putInt(AppManager.OrderNumberKey, orderNumber + 1);
    }

    public int getOrderNumber(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int orderNumber = sharedPreferences.getInt(AppManager.OrderNumberKey, 1);

        return orderNumber;
    }

    public void setGAID(Context context) {
        AsyncTask.execute(() -> {
            try {
                AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                gaid = adInfo != null ? adInfo.getId() : null;
                Log.i(TAG, "Nirob test gaid: " + gaid);
            } catch (Exception e) {
                Toast toast = Toast.makeText(context, "error occurred ", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public ProductInfo getProductIfExistInDrinks(ProductInfo _productInfo) {
        for (ProductInfo productInfo : AppManager.getInstance().selectedDrinksList) {
            if (productInfo.id.equals(_productInfo.id)) {
                return productInfo;
            }
        }
        return null;
    }

    public ProductInfo getProductIfExistInBreakfast(ProductInfo _productInfo) {
        for (ProductInfo productInfo : AppManager.getInstance().selectedBreakfastList) {
            if (productInfo.id.equals(_productInfo.id)) {
                return productInfo;
            }
        }
        return null;
    }

    public ProductInfo getProductIfExistInLunch(ProductInfo _productInfo) {
        for (ProductInfo productInfo : AppManager.getInstance().selectedLunchList) {
            if (productInfo.id.equals(_productInfo.id)) {
                return productInfo;
            }
        }
        return null;
    }

    public String getGeneratedOrderId(Context context) {
        String orderId = "";
        String documentId = AppManager.getInstance().userProfile.documentId;
        String gaid = AppManager.getInstance().gaid;
        orderId = orderId + documentId.substring(0,3);
        orderId = orderId + documentId.substring(documentId.length() - 4, documentId.length() - 1) + "_";
        orderId = orderId + gaid.substring(0, 2);;
        orderId = orderId + gaid.substring(gaid.length() - 3, gaid.length() - 1) + "_";
        orderId = orderId + AppManager.getInstance().getOrderNumber(context);

        return orderId;
    }

}
