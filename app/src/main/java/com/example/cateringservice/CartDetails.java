
package com.example.cateringservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.cateringservice.manager.AppManager;
import com.example.cateringservice.models.ProductInfo;

import java.util.ArrayList;
import java.util.List;

public class CartDetails extends AppCompatActivity {
    private final String TAG = CartDetails.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);

        RecyclerView recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*List<ProductInfo> myCartDescription = new ArrayList<>();
        myCartDescription.add(ProductInfo.getTestProduct(1));
        myCartDescription.add(ProductInfo.getTestProduct(2));
        myCartDescription.add(ProductInfo.getTestProduct(3));
        myCartDescription.add(ProductInfo.getTestProduct(4));*/

        MyCartAdapter myCartAdapter=new MyCartAdapter(getAllSelectedProducts(), CartDetails.this);
        recyclerView.setAdapter(myCartAdapter);
    }

    private List<ProductInfo> getAllSelectedProducts() {
        List<ProductInfo> allSelectedProductList = new ArrayList<>();
        allSelectedProductList.addAll(AppManager.getInstance().selectedDrinksList);
        allSelectedProductList.addAll(AppManager.getInstance().selectedBreakfastList);
        allSelectedProductList.addAll(AppManager.getInstance().selectedLunchList);
        Log.v(TAG, "Nirob test cart drink size: " + AppManager.getInstance().selectedDrinksList.size());
        Log.v(TAG, "Nirob test cart drink size: " + AppManager.getInstance().selectedBreakfastList.size());
        Log.v(TAG, "Nirob test cart drink size: " + AppManager.getInstance().selectedLunchList.size());
        Log.v(TAG, "Nirob test cart drink size: " + allSelectedProductList.size());
        return allSelectedProductList;
    }
}
