
package com.example.cateringservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class CartDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_details);

        RecyclerView recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyCartDescription[] myDrinksDescription=new MyCartDescription[]{
                new MyCartDescription("COCACOLA","most selling soft drinks",R.drawable.cocacola, 30),
                new MyCartDescription("SPRITE","lemon-lime flavored soft drink",R.drawable.sprite, 30),
                new MyCartDescription("FANTA","Fanta Orange is a soft drink with a tingly, fruity taste",R.drawable.fanta, 30),
        };

        MyCartAdapter myDrinksAdapter=new MyCartAdapter(myDrinksDescription, CartDetails.this);
        recyclerView.setAdapter(myDrinksAdapter);
    }
}
