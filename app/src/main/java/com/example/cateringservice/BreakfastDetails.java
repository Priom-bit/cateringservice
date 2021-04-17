package com.example.cateringservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class BreakfastDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_details);

        RecyclerView recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyBreakfastDescription[] myBreakfastDescription=new MyBreakfastDescription[]{
                new MyBreakfastDescription("BACON"," type of salt-cured beef made from various cuts",R.drawable.bacon,70),
                new MyBreakfastDescription("BHUNA_KHICHURI","a comfort rice dish popular in Bengali cuisine.",R.drawable.bhuna_khichuri,100),
                new MyBreakfastDescription("BISCUITS","any of various hard or crisp dry baked product",R.drawable.biscuits,20),
                new MyBreakfastDescription("BREAD_PUDDING"," In a small saucepan over low heat, warm milk, butter, vanilla, sugar and salt",R.drawable.bread_pudding,60),
                new MyBreakfastDescription("CHEESE_SANDWICH","simple and basic sandwich made by placing cheese in between 2 buttered slices of bread",R.drawable.cheese_sandwich,120),
                new MyBreakfastDescription("EGG_PARATHA"," spicy. egg stuffed, protein-packed, whole-wheat paratha recipe for breakfast",R.drawable.egg_paratha,30),
        };

        MyBreakfastAdapter myBreakfastAdapter = new MyBreakfastAdapter(myBreakfastDescription, BreakfastDetails.this);
        recyclerView.setAdapter(myBreakfastAdapter);

    }
}