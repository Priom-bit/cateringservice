package com.example.cateringservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cateringservice.models.ProductInfo;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

public class DrinksDetails extends AppCompatActivity {
    private final String TAG = DrinksDetails.class.getSimpleName();

    RecyclerView recyclerView;
    TextView value;

    List<ProductInfo> productInfoList;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_details);

        value = (TextView) findViewById(R.id.value);

        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productInfoList = new ArrayList<>();
                /*[]{
                new MyDrinksDescription("COCACOLA","most selling soft drinks",R.drawable.cocacola, 30),
                new MyDrinksDescription("SPRITE","lemon-lime flavored soft drink",R.drawable.sprite, 30),
                new MyDrinksDescription("FANTA","Fanta Orange is a soft drink with a tingly, fruity taste",R.drawable.fanta, 30),
        };*/
        loadDrinksData();
    }

    private void loadDrinksData() {
        KProgressHUD progressHUD = KProgressHUD.create(DrinksDetails.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait!")
                .setDetailsLabel("Checking Login Data!")
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
                    productInfoList.add(productInfo);
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
        MyDrinksAdapter myDrinksAdapter=new MyDrinksAdapter(productInfoList, DrinksDetails.this);
        recyclerView.setAdapter(myDrinksAdapter);
    }

    public void incrementBtn(View v){

        count++;
        value.setText("" + count);
    }

    public void decrementBtn(View v){

        if(count <= 0) count = 0;
        else count--;
        value.setText("" + count);
    }
}