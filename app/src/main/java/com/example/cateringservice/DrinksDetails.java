package com.example.cateringservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.cateringservice.manager.AppManager;
import com.example.cateringservice.models.ProductInfo;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

public class DrinksDetails extends AppCompatActivity {
    private final String TAG = DrinksDetails.class.getSimpleName();

    RecyclerView recyclerView;

    List<ProductInfo> productInfoList;
    MyDrinksAdapter myDrinksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_details);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("Drinks");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productInfoList = new ArrayList<>();
        loadDrinksData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "Nirob test onActivityResult");
        if (requestCode == CSConstants.DRINK_ACTIVITY_REQUEST_CODE && resultCode == CSConstants.ACTIVITY_RESULT_CODE && data.hasExtra("productInfoDetails")) {
            ProductInfo _productInfo = (ProductInfo) data.getSerializableExtra("productInfoDetails");
            ProductInfo productInfo = getProductOfSameId(_productInfo);
            if (productInfo != null) {
                int index = productInfoList.indexOf(productInfo);
                productInfoList.set(index, _productInfo);
                myDrinksAdapter.notifyDataSetChanged();
            }
        }
    }

    private ProductInfo getProductOfSameId(ProductInfo productInfo) {
        for (ProductInfo productInfo1 : productInfoList) {
            if (productInfo.id.equals(productInfo1.id)) {
                return productInfo1;
            }
        }
        return null;
    }

    private void loadDrinksData() {
        KProgressHUD progressHUD = KProgressHUD.create(DrinksDetails.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait!")
                .setDetailsLabel("Loading Drinks Data!")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Services.getInstance().getRequestGreaterLess("productInfo", "id", 1, 99, 20, new Services.FireStoreCompletionListener() {
            @Override
            public void onGetSuccess(QuerySnapshot querySnapshots) {
                progressHUD.dismiss();
                Log.v(TAG, "Nirob test drinks size: " + querySnapshots.size());
                for (QueryDocumentSnapshot documentSnapshot : querySnapshots) {
                    ProductInfo productInfo = ProductInfo.getProductInfoFrom(documentSnapshot);
                    ProductInfo selectedProductInfo = AppManager.getInstance().getProductIfExistInDrinks(productInfo);
                    if (selectedProductInfo != null) {
                        productInfoList.add(selectedProductInfo);
                    }
                    else {
                        productInfoList.add(productInfo);
                    }
                }
                loadListView();
            }

            @Override
            public void onPostSuccess() {

            }

            @Override
            public void onFailure(String error) {
                progressHUD.dismiss();
            }
        });
    }

    private void loadListView() {
        myDrinksAdapter = new MyDrinksAdapter(productInfoList, new CSConstants.RecyclerViewOnClickListener() {
            @Override
            public void OnItemClicked(int position) {
                Log.v(TAG, "Nirob test OnItemClicked psss: " + position);
                ProductInfo productInfo1 = productInfoList.get(position);
                Intent intent = new Intent(DrinksDetails.this, ProductDetails.class);
                intent.putExtra("productDetails", productInfo1);
                startActivityForResult(intent, CSConstants.DRINK_ACTIVITY_REQUEST_CODE);
            }
        });
        recyclerView.setAdapter(myDrinksAdapter);
    }

    public void cartButtonClicked(View view) {
        Intent intent = new Intent(DrinksDetails.this, CartDetails.class);
        startActivity(intent);
    }
}