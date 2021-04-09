package com.example.cateringservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.analytics.FirebaseAnalytics;

public class SplashActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            //Do something after 100ms
            callAfterSomeTimes();
        }, 1000);
    }

    void callAfterSomeTimes() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Boolean isLoggedIn = sharedPreferences.getBoolean("LoggedInKey", false);
        if (isLoggedIn) {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(SplashActivity.this, Login_Form.class);
            startActivity(intent);
        }
        finish();
    }
}