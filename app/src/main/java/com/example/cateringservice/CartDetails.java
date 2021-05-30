
package com.example.cateringservice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cateringservice.manager.AppManager;
import com.example.cateringservice.models.ProductInfo;

import java.util.ArrayList;
import java.util.List;

public class CartDetails extends AppCompatActivity {
    private final String TAG = CartDetails.class.getSimpleName();

    RecyclerView recyclerView;
    TextView noItemTextView;
    Button placeOrderBtn;
    TextView finalPriceTextView;

    List<ProductInfo> allSelectedProductList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("Cart");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noItemTextView = findViewById(R.id.cartNoitemSelectedId);
        placeOrderBtn = findViewById(R.id.cartPlaceOrderBtnId);
        finalPriceTextView = findViewById(R.id.final_price);

        loadListView();
        loadTotalPrice();
    }

    private void loadListView() {
        allSelectedProductList.clear();
        allSelectedProductList = getAllSelectedProducts();
        if (allSelectedProductList.size() > 0) {
            noItemTextView.setVisibility(View.INVISIBLE);

            placeOrderBtn.setAlpha(1.0f);
            placeOrderBtn.setEnabled(true);

            MyCartAdapter myCartAdapter=new MyCartAdapter(allSelectedProductList, CartDetails.this);
            recyclerView.setAdapter(myCartAdapter);
        }
        else {
            noItemTextView.setVisibility(View.VISIBLE);

            placeOrderBtn.setAlpha(0.5f);
            placeOrderBtn.setEnabled(false);
        }
    }

    private void loadTotalPrice() {
        finalPriceTextView.setText(getTotalPrice().toString());
    }

    private List<ProductInfo> getAllSelectedProducts() {
        List<ProductInfo> _allSelectedProductList = new ArrayList<>();
        _allSelectedProductList.addAll(AppManager.getInstance().selectedDrinksList);
        _allSelectedProductList.addAll(AppManager.getInstance().selectedBreakfastList);
        _allSelectedProductList.addAll(AppManager.getInstance().selectedLunchList);
        return _allSelectedProductList;
    }

    private Integer getTotalPrice() {
        Integer totalPrice = 0;
        for (ProductInfo productInfo: allSelectedProductList) {
            totalPrice += (productInfo.price*productInfo.count);
        }
        return totalPrice;
    }

    public void btnPlaceOrderClicked(View view) {

        startActivity(new Intent(CartDetails.this, FinalOrderDetailsActivity.class));
    }

    private void gotoPreviousPage() {
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(CartDetails.this, HomeActivity.class));
                finish();
            }
        }, 1500);
    }

    private void clearAllSelectedProducts() {
        AppManager.getInstance().selectedDrinksList.clear();
        AppManager.getInstance().selectedBreakfastList.clear();
        AppManager.getInstance().selectedLunchList.clear();
    }
}
