package com.example.cateringservice;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_details);

        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productInfoList = new ArrayList<>();
        loadBreakfastData();
    }

    private void loadBreakfastData() {
        KProgressHUD progressHUD = KProgressHUD.create(BreakfastDetails.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait!")
                .setDetailsLabel("Checking Login Data!")
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
        MyBreakfastAdapter myBreakfastAdapter = new MyBreakfastAdapter(productInfoList, BreakfastDetails.this);
        recyclerView.setAdapter(myBreakfastAdapter);

    }

    public void cartButtonClicked(View view) {
        Intent intent = new Intent(BreakfastDetails.this, CartDetails.class);
        startActivity(intent);
    }
}

