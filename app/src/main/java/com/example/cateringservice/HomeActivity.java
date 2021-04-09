package com.example.cateringservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = HomeActivity.class.getSimpleName();

    DrawerLayout drawerLayout;
    ListView listView;
    String mTitle[]={"Drinks","Breakfast","Lunch"};
    String mDescription[]={"Drinks Description","Breakfast Description","Lunch Description"};
    int images[]={R.drawable.drinks,R.drawable.breakfast,R.drawable.lunch};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout=findViewById(R.id.homeDrawerLayoutId);
        ImageSlider imageSlider = findViewById(R.id.slider);

        List<SlideModel> slideModels =new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.burger_slider,"50% OFF"));
        slideModels.add(new SlideModel(R.drawable.shawarma_slider,"BUY 1 GET 1 OFFER"));
        slideModels.add(new SlideModel(R.drawable.pizza_slider,"20% OFF"));
        imageSlider.setImageList(slideModels,true);
        imageSlider.stopSliding();

        imageSlider.setVisibility(View.INVISIBLE);

        Log.v(TAG, "Testing Home");


        listView=findViewById(R.id.listView);

        MyAdapter adapter=new MyAdapter(this,mTitle,mDescription,images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    //Toast.makeText(HomeActivity.this, "Drinks Description", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DrinksDetails.class));
                }
                else if(position==1) {
//                    Toast.makeText(HomeActivity.this, "Breakfast Description", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), BreakfastDetails.class));
                }
                else if(position==2) {
                   // Toast.makeText(HomeActivity.this, "Lunch Description", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LunchDetails.class));

                }
            }
        });

//        FloatingActionButton cartButton = findViewById(R.id.home_cart_button);
//        cartButton.setOnClickListener(v -> {
//            //startActivity(new Intent(getApplicationContext(), LunchDetails.class));
//        });
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
        //recreate();
        closeDrawer(drawerLayout);
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

                activity.startActivity(new Intent(activity.getApplicationContext(), Login_Form.class));
                activity.finishAffinity();
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

    public void cartButtonClicked(View view) {
        Log.v(TAG, "Testing Clicked at menu");
        Intent intent = new Intent(HomeActivity.this, CartDetails.class);
        startActivity(intent);
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImgs[];

        MyAdapter (Context c, String title[], String description[], int imgs[]){
            super(c, R.layout.row, R.id.textView1, title);
            this.context=c;
            this.rTitle=title;
            this.rDescription=description;
            this.rImgs=imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            ImageView images= row.findViewById(R.id.image);
            TextView myTitle= row.findViewById(R.id.textView1);
            TextView myDescription= row.findViewById(R.id.textView2);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);





            return row;
        }
    }
}