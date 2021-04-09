package com.example.cateringservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class LunchDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_details);
        RecyclerView recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyLunchDescription[] myLunchDescription=new MyLunchDescription[]{
                new MyLunchDescription("BEEF_BIRIYANI","layered rice and meat dish",R.drawable.beef_biriyani,100),
                new MyLunchDescription("TEHERI","yellow rice dish in Awadhi cuisine",R.drawable.teheri, 70),
                new MyLunchDescription("CHINESE_VEGETABLE"," sweet flavor and crisp texture",R.drawable.chinese_vegetable,150),
                new MyLunchDescription("MOROG_POLAO","Bengali Chicken & Basmati Rice",R.drawable.morog_polao,100),
                new MyLunchDescription("MUTTON_BIRIYANI"," layering rice over slow cooked mutton gravy",R.drawable.mutton_biriyani,120),
                new MyLunchDescription("CHICKEN_BIRIYANI","savory chicken and rice dish that includes layers of chicken, rice, and aromatics that are steamed together",R.drawable.chicken_biriyani,110),
                new MyLunchDescription("FRIED_RICE","stir-fried in a wok or a frying pan and is usually mixed with other ingredients such as eggs, vegetables, seafood, or meat",R.drawable.fried_rice,100),
        };

        MyLunchAdapter myLunchAdapter = new MyLunchAdapter(myLunchDescription, LunchDetails.this);
        recyclerView.setAdapter(myLunchAdapter);

    }
}