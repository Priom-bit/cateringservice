package com.example.cateringservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.cateringservice.manager.AppManager;
import com.example.cateringservice.models.ProductInfo;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {
    private final String TAG = ProductDetails.class.getSimpleName();

    ImageSlider imageSlider;
    TextView titleView;
    TextView descriptionView;
    TextView productPriceView;
    TextView countView;
    TextView totalPriceView;

    List<SlideModel> slideModels;
    ProductInfo productInfo = new ProductInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        imageSlider = findViewById(R.id.slider);
        titleView = findViewById(R.id.details_title);
        descriptionView = findViewById(R.id.details_description);
        productPriceView = findViewById(R.id.productPrice);
        countView = findViewById(R.id.numberOfProducts);
        totalPriceView = findViewById(R.id.productTotalPrice);

        slideModels = new ArrayList<>();

        productInfo = (ProductInfo) getIntent().getSerializableExtra("productDetails");

        loadTopSlider();
        loadDetailsData();

    }

    private void loadTopSlider() {
        slideModels.clear();

        Log.v(TAG, "Nirob test imgurl: " + productInfo.imageUrl);
        SlideModel slideModel = new SlideModel(productInfo.imageUrl, getTopSliderTitle(productInfo));
        slideModels.add(slideModel);

        imageSlider.setImageList(slideModels,true);
        imageSlider.stopSliding();
    }

    private void loadDetailsData() {
        titleView.setText(productInfo.productName);
        descriptionView.setText(productInfo.description);
        productPriceView.setText(productInfo.price.toString() + " tk");
        countView.setText(String.valueOf(productInfo.count));
        totalPriceView.setText(String.valueOf(productInfo.count*productInfo.price) + " tk");
    }

    private String getTopSliderTitle(ProductInfo productInfo) {
        if (productInfo.discount > 0) {
            return getPercentageOf(productInfo.price, productInfo.discount) + "% OFF";
        }
        else if (productInfo.discount == 0) {
            return "BUY 1 GET 1 FREE";
        }
        return "";
    }

    private int getPercentageOf(int price, int discount) {
        float percentage = (discount*100)/20.0f;

        return (int) Math.ceil(percentage);
    }

    public void incrementBtn(View view){
        productInfo.count++;
        countView.setText("" + productInfo.count);
        updateFinalPrice();
    }

    public void decrementBtn(View view){
        if(productInfo.count <= 0) productInfo.count = 0;
        else productInfo.count--;
        countView.setText("" + productInfo.count);
        updateFinalPrice();
    }

    public void updateFinalPrice() {
        totalPriceView.setText("" + (productInfo.price*productInfo.count) + " tk");
        int productType = getProductType();
        if (productType == 1)
            replaceIfFoundDrinks(productInfo);
        else if (productType == 2)
            replaceIfFoundBreakfast(productInfo);
        else if (productType == 3)
            replaceIfFoundLunch(productInfo);

        Intent data = new Intent();
        data.putExtra("productInfoDetails", productInfo);
        setResult(CSConstants.ACTIVITY_RESULT_CODE, data);
    }

    private int getProductType() {
        if (productInfo.id > 0 && productInfo.id < 100)
            return 1;
        else if (productInfo.id > 100 && productInfo.id < 200)
            return 2;
        else if (productInfo.id > 200 && productInfo.id < 300)
            return 3;
        return 0;
    }

    private void replaceIfFoundDrinks(ProductInfo _productInfo) {
        if (_productInfo.count > 0) {
            boolean isFound = false;
            for (ProductInfo productInfo : AppManager.getInstance().selectedDrinksList) {
                if (productInfo.id.equals(_productInfo.id)) {
                    isFound = true;
                    int index = AppManager.getInstance().selectedDrinksList.indexOf(productInfo);
                    AppManager.getInstance().selectedDrinksList.set(index, _productInfo);
                }
            }
            if (!isFound) {
                AppManager.getInstance().selectedDrinksList.add(_productInfo);
            }
        }
        else {
            AppManager.getInstance().selectedDrinksList.remove(productInfo);
        }
        Log.v(TAG, "Nirob test drinks list: " + AppManager.getInstance().selectedDrinksList.size());
    }

    private void replaceIfFoundBreakfast(ProductInfo _productInfo) {
        if (_productInfo.count > 0) {
            boolean isFound = false;
            for (ProductInfo productInfo : AppManager.getInstance().selectedBreakfastList) {
                if (productInfo.id.equals(_productInfo.id)) {
                    isFound = true;
                    int index = AppManager.getInstance().selectedBreakfastList.indexOf(productInfo);
                    AppManager.getInstance().selectedBreakfastList.set(index, _productInfo);
                }
            }
            if (!isFound) {
                AppManager.getInstance().selectedBreakfastList.add(_productInfo);
            }
        }
        else {
            AppManager.getInstance().selectedBreakfastList.remove(productInfo);
        }
        Log.v(TAG, "Nirob test drinks list: " + AppManager.getInstance().selectedBreakfastList.size());
    }

    private void replaceIfFoundLunch(ProductInfo _productInfo) {
        if (_productInfo.count > 0) {
            boolean isFound = false;
            for (ProductInfo productInfo : AppManager.getInstance().selectedLunchList) {
                if (productInfo.id.equals(_productInfo.id)) {
                    isFound = true;
                    int index = AppManager.getInstance().selectedLunchList.indexOf(productInfo);
                    AppManager.getInstance().selectedLunchList.set(index, _productInfo);
                }
            }
            if (!isFound) {
                AppManager.getInstance().selectedLunchList.add(_productInfo);
            }
        }
        else {
            AppManager.getInstance().selectedLunchList.remove(productInfo);
        }
        Log.v(TAG, "Nirob test drinks list: " + AppManager.getInstance().selectedLunchList.size());
    }

    public void cartButtonClicked(View view) {
        Intent intent = new Intent(ProductDetails.this, CartDetails.class);
        startActivity(intent);
    }
}