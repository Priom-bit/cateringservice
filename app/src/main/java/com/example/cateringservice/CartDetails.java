
package com.example.cateringservice;

import androidx.appcompat.app.AlertDialog;
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
import android.widget.Toast;

import com.example.cateringservice.manager.AppManager;
import com.example.cateringservice.models.ProductInfo;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartDetails extends AppCompatActivity {
    private final String TAG = CartDetails.class.getSimpleName();

    RecyclerView recyclerView;
    TextView noItemTextView;
    Button placeOrderBtn;

    List<ProductInfo> allSelectedProductList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);

        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noItemTextView = findViewById(R.id.cartNoitemSelectedId);
        placeOrderBtn = findViewById(R.id.cartPlaceOrderBtnId);

        loadListView();
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

    private List<ProductInfo> getAllSelectedProducts() {
        List<ProductInfo> _allSelectedProductList = new ArrayList<>();
        _allSelectedProductList.addAll(AppManager.getInstance().selectedDrinksList);
        _allSelectedProductList.addAll(AppManager.getInstance().selectedBreakfastList);
        _allSelectedProductList.addAll(AppManager.getInstance().selectedLunchList);
        return _allSelectedProductList;
    }

    public void btnPlaceOrderClicked(View view) {
        Map<String, Object> order = new HashMap<>();
        Map<String, Object> orderMap = new HashMap<>();
        List<Map<String, Object>> products = new ArrayList<>();
        for (ProductInfo productInfo : allSelectedProductList) {
            Map<String, Object> product = new HashMap<>();
            product.put("productid", productInfo.id);
            product.put("count", productInfo.count);
            products.add(product);
        }
        orderMap.put("products", products);
        order.put("order", orderMap);
        order.put("orderid", AppManager.getInstance().getGeneratedOrderId(getApplicationContext()));

        KProgressHUD progressHUD = KProgressHUD.create(CartDetails.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait!")
                .setDetailsLabel("Order is placing.")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Services.getInstance().postRequest("orders", order, new Services.FireStoreCompletionListener() {
            @Override
            public void onGetSuccess(QuerySnapshot querySnapshots) {

            }

            @Override
            public void onPostSuccess() {
                progressHUD.dismiss();
                placeOrderBtn.setAlpha(0.5f);
                placeOrderBtn.setEnabled(false);
                clearAllSelectedProducts();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Your order is placed successfully!", Toast.LENGTH_SHORT).show();
                        AppManager.getInstance().setOrderNumber(getApplicationContext());
                        gotoPreviousPage();
                    }
                }, 200);
            }

            @Override
            public void onFailure(String error) {
                progressHUD.dismiss();
                Log.v(TAG, "Nirob test error to place order error: " + error);
            }
        });
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
