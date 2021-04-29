package com.example.cateringservice.models;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;

public class ProductInfo implements Serializable {
    public Integer id;
    public String productName;
    public String description;
    public String imageUrl;
    public Integer price;
    public Integer discount;
    public String size;
    public int count = 0;

    public static ProductInfo getProductInfoFrom(DocumentSnapshot documentSnapshot) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.id = documentSnapshot.getLong("id").intValue();
        productInfo.productName = documentSnapshot.getString("productname");
        productInfo.description = documentSnapshot.getString("description");
        productInfo.imageUrl = documentSnapshot.getString("imageurl");
        productInfo.price = documentSnapshot.getLong("price").intValue();
        productInfo.discount = documentSnapshot.getLong("discount").intValue();
        productInfo.size = documentSnapshot.getString("size");

        return productInfo;
    }

    public static ProductInfo getTestProduct(int _id) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.id = _id;
        productInfo.productName = "Product " + _id;
        productInfo.description = "Test description";
        productInfo.imageUrl = "https://catering-service-48379.web.app/images/egg_paratha.jpg";
        productInfo.price = 20;
        productInfo.discount = -1;
        productInfo.size = "Testing";

        return productInfo;
    }
}
