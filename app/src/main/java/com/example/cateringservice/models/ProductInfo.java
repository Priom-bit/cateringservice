package com.example.cateringservice.models;

import com.google.firebase.firestore.DocumentSnapshot;

public class ProductInfo {
    public Integer id;
    public String productName;
    public String description;
    public String imageUrl;
    public Integer price;
    public Integer discount;
    public String size;

    public static ProductInfo getProductInfoFrom(DocumentSnapshot documentSnapshot) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.id = documentSnapshot.getLong("id").intValue();
        productInfo.productName = documentSnapshot.getString("productName");
        productInfo.description = documentSnapshot.getString("description");
        productInfo.imageUrl = documentSnapshot.getString("imageUrl");
        productInfo.price = documentSnapshot.getLong("price").intValue();
        productInfo.discount = documentSnapshot.getLong("discount").intValue();
        productInfo.size = documentSnapshot.getString("size");

        return productInfo;
    }
}
