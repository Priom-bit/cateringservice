
package com.example.cateringservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cateringservice.models.ProductInfo;

import java.util.ArrayList;
import java.util.List;

public class CartDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);

        RecyclerView recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ProductInfo> myCartDescription = new ArrayList<>();
        myCartDescription.add(ProductInfo.getTestProduct(1));
        myCartDescription.add(ProductInfo.getTestProduct(2));
        myCartDescription.add(ProductInfo.getTestProduct(3));
        myCartDescription.add(ProductInfo.getTestProduct(4));

        MyCartAdapter myCartAdapter=new MyCartAdapter(myCartDescription, CartDetails.this);
        recyclerView.setAdapter(myCartAdapter);
    }
}
