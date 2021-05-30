package com.example.cateringservice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cateringservice.manager.AppManager;
import com.example.cateringservice.models.ProductInfo;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

public class BreakfastDetails extends AppCompatActivity {
    private final String TAG = BreakfastDetails.class.getSimpleName();

    RecyclerView recyclerView;

    List<ProductInfo> productInfoList;
    MyBreakfastAdapter myBreakfastAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_details);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("Breakfast");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productInfoList = new ArrayList<>();
        loadBreakfastData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "Nirob test onActivityResult");
        if (requestCode == CSConstants.BREAKFAST_ACTIVITY_REQUEST_CODE && resultCode == CSConstants.ACTIVITY_RESULT_CODE && data.hasExtra("productInfoDetails")) {
            ProductInfo _productInfo = (ProductInfo) data.getSerializableExtra("productInfoDetails");
            ProductInfo productInfo = getProductOfSameId(_productInfo);
            if (productInfo != null) {
                int index = productInfoList.indexOf(productInfo);
                productInfoList.set(index, _productInfo);
                myBreakfastAdapter.notifyDataSetChanged();
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

    private void loadBreakfastData() {
        KProgressHUD progressHUD = KProgressHUD.create(BreakfastDetails.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait!")
                .setDetailsLabel("Loading Breakfast Data!")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Services.getInstance().getRequestGreaterLess("productInfo", "id", 101, 199, 20, new Services.FireStoreCompletionListener() {
            @Override
            public void onGetSuccess(QuerySnapshot querySnapshots) {
                progressHUD.dismiss();
                Log.v(TAG, "Nirob test drinks size: " + querySnapshots.size());
                for (QueryDocumentSnapshot documentSnapshot : querySnapshots) {
                    ProductInfo productInfo = ProductInfo.getProductInfoFrom(documentSnapshot);
                    ProductInfo selectedProductInfo = AppManager.getInstance().getProductIfExistInBreakfast(productInfo);
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
        myBreakfastAdapter = new MyBreakfastAdapter(productInfoList, new CSConstants.RecyclerViewOnClickListener() {
            @Override
            public void OnItemClicked(int position) {
                ProductInfo productInfo = productInfoList.get(position);
                Intent intent = new Intent(BreakfastDetails.this, ProductDetails.class);
                intent.putExtra("productDetails", productInfo);
                startActivityForResult(intent, CSConstants.BREAKFAST_ACTIVITY_REQUEST_CODE);
            }
        });
        recyclerView.setAdapter(myBreakfastAdapter);

    }

    public void cartButtonClicked(View view) {
        Intent intent = new Intent(BreakfastDetails.this, CartDetails.class);
        startActivity(intent);
    }
}

