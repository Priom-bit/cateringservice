package com.example.cateringservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cateringservice.models.ProductInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    List<ProductInfo> productInfoList;
    Context context;

    public MyCartAdapter(List<ProductInfo> productInfoList, CartDetails activity) {
        this.productInfoList=productInfoList;
        this.context=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater LayoutInflater= android.view.LayoutInflater.from(parent.getContext());
        View view=LayoutInflater.inflate(R.layout.cart_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProductInfo productInfo=productInfoList.get(position);
        holder.textViewName.setText(productInfo.productName);
        holder.textViewdescription.setText(productInfo.description);
        Picasso.get().load(productInfo.imageUrl).into(holder.cartImage);
        holder.textViewCartPrice.setText(productInfo.price.toString() + " tk");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.incrementButton.setOnClickListener(view -> {
            holder.incrementBtn(position);
        });

        holder.decrementButton.setOnClickListener(view -> {
            holder.decrementBtn(position);
        });


    }

    @Override
    public int getItemCount() { return productInfoList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cartImage;
        TextView textViewName;
        TextView textViewdescription;
        TextView textViewCartPrice;
        TextView CartTotalPrice;

        ImageButton incrementButton;
        TextView numberOfCartProducts;
        ImageButton decrementButton;
        TextView totalPrice;

        int count = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartImage=itemView.findViewById(R.id.cartimageView);
            textViewName=itemView.findViewById(R.id.cartname);
            textViewdescription=itemView.findViewById(R.id.cartdescription);
            textViewCartPrice = itemView.findViewById(R.id.cartPrice);
            CartTotalPrice = itemView.findViewById(R.id.cartTotalPrice);
            incrementButton = itemView.findViewById(R.id.cartIncrementBtn);
            numberOfCartProducts = itemView.findViewById(R.id.numberOfCartProducts);
            decrementButton = itemView.findViewById(R.id.cartDecrementBtn);
            totalPrice = itemView.findViewById(R.id.cartTotalPrice);
        }
        public void incrementBtn(int position){

            count++;
            numberOfCartProducts.setText("" + count);
            updateFinalPrice(position);
        }

        public void decrementBtn(int position){

            if(count <= 0) count = 0;
            else count--;
            numberOfCartProducts.setText("" + count);
            updateFinalPrice(position);
        }

        public void updateFinalPrice(int position) {
            ProductInfo productInfo = productInfoList.get(position);
            totalPrice.setText("" + (productInfo.price*count) + " tk");
        }


    }
}
