package com.example.cateringservice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cateringservice.manager.AppManager;
import com.example.cateringservice.models.ProductInfo;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinalOrderDetailsActivity extends AppCompatActivity {
    private final String TAG= FinalOrderDetailsActivity.class.getSimpleName();
    private EditText nameEditText, phoneEditText, addressEditText;
    private Button ConfirmOrderBtn;
    List<ProductInfo> allSelectedProductList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order_details);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("Final Order Details");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ConfirmOrderBtn = (Button) findViewById(R.id.Confirm_final_order_btn);

        ConfirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmFinalOrder();
            }
        });
        nameEditText =(EditText) findViewById(R.id.shippment_name);
        phoneEditText =(EditText) findViewById(R.id.shippment_phone_number);
        addressEditText =(EditText) findViewById(R.id.shippment_address);

    }
    private List<ProductInfo> getAllSelectedProducts() {
        List<ProductInfo> _allSelectedProductList = new ArrayList<>();
        _allSelectedProductList.addAll(AppManager.getInstance().selectedDrinksList);
        _allSelectedProductList.addAll(AppManager.getInstance().selectedBreakfastList);
        _allSelectedProductList.addAll(AppManager.getInstance().selectedLunchList);
        return _allSelectedProductList;
    }

    void confirmFinalOrder(){
        allSelectedProductList=getAllSelectedProducts();
        Map<String, Object> order = new HashMap<>();
        Map<String, Object> orderMap = new HashMap<>();
        List<Map<String, Object>> products = new ArrayList<>();
        for (ProductInfo productInfo : allSelectedProductList) {
            Map<String, Object> product = new HashMap<>();
            product.put("productid", productInfo.id);
            product.put("productname", productInfo.productName);
            product.put("description", productInfo.description);
            product.put("price", productInfo.price);
            product.put("discount", productInfo.discount);
            product.put("imageurl", productInfo.imageUrl);
            product.put("size", productInfo.size);
            product.put("count", productInfo.count);
            products.add(product);
        }
        orderMap.put("products", products);
        order.put("order", orderMap);
        order.put("orderid", AppManager.getInstance().getGeneratedOrderId(getApplicationContext()));
        order.put("userid", AppManager.getInstance().userProfile.documentId);
        order.put("useremail", AppManager.getInstance().userProfile.email);
        order.put("username", AppManager.getInstance().userProfile.firstName + " " + AppManager.getInstance().userProfile.lastName);

        KProgressHUD progressHUD = KProgressHUD.create(FinalOrderDetailsActivity.this)
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
                ConfirmOrderBtn.setAlpha(0.5f);
                ConfirmOrderBtn.setEnabled(false);
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
                startActivity(new Intent(FinalOrderDetailsActivity.this, HomeActivity.class));
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