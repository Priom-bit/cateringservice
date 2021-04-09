package com.example.cateringservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DrinksDetails extends AppCompatActivity {

    TextView Value;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_details);

        Value = (TextView) findViewById(R.id.value);

        RecyclerView recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyDrinksDescription[] myDrinksDescription=new MyDrinksDescription[]{
                new MyDrinksDescription("COCACOLA","most selling soft drinks",R.drawable.cocacola, 30),
                new MyDrinksDescription("SPRITE","lemon-lime flavored soft drink",R.drawable.sprite, 30),
                new MyDrinksDescription("FANTA","Fanta Orange is a soft drink with a tingly, fruity taste",R.drawable.fanta, 30),
        };

        MyDrinksAdapter myDrinksAdapter=new MyDrinksAdapter(myDrinksDescription, DrinksDetails.this);
        recyclerView.setAdapter(myDrinksAdapter);
    }

    public void incrementBtn(View v){

        count++;
        Value.setText("" + count);
    }

    public void decrementBtn(View v){

        if(count <= 0) count = 0;
        else count--;
        Value.setText("" + count);
    }
}