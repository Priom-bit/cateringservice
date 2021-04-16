package com.example.cateringservice.models;

import com.google.firebase.firestore.DocumentSnapshot;

public class DiscountProduct {
    public Integer id;
    public Integer discount;
    public String title;
    public ProductInfo productInfo;

    public static DiscountProduct getDiscountProductFrom(DocumentSnapshot documentSnapshot) {
        DiscountProduct discountProduct = new DiscountProduct();
        discountProduct.id = documentSnapshot.getLong("id").intValue();
        discountProduct.discount = documentSnapshot.getLong("discount").intValue();
        discountProduct.title = documentSnapshot.getString("title");

        return discountProduct;
    }
}
