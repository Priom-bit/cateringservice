package com.example.cateringservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static void openDrawer(DrawerLayout drawerLayout) {
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
    }

    public static void redirectActivity(AboutUs aboutUs) {
    }

    public static void redirectActivity(AboutUs aboutUs, Class<MainActivity> mainActivityClass) {
    }

    public static void Logout(AboutUs aboutUs) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}