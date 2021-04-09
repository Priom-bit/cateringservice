package com.example.cateringservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

public class NavigationDrawer extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        drawerLayout=findViewById(R.id.drawer_layout);
    }

    public void ClickMenu(View view){

        openDrawer(drawerLayout);

    }

    public static void openDrawer(DrawerLayout drawerLayout) {

        drawerLayout.openDrawer(GravityCompat.START);

    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        recreate();
    }
    public void ClickAboutUs(View view){

        redirectActivity(this,AboutUs.class);
    }
   public void ClickLogout(View view){
       logout(this);
   }

    public static void logout(Activity activity) {

        AlertDialog.Builder builder=new AlertDialog.Builder(activity);

        builder.setTitle("Logout");
        builder.setMessage("Want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.show();
    }


    public static void redirectActivity(Activity activity,Class aClass) {
        Intent intent= new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);


        //activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        closeDrawer(drawerLayout);

    }
}