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

public class MyLunchAdapter extends RecyclerView.Adapter<MyLunchAdapter.ViewHolder> {
    List<ProductInfo> productInfoList;
    Context context;

    public MyLunchAdapter(List<ProductInfo> productInfoList, LunchDetails activity) {
        this.productInfoList=productInfoList;
        this.context=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater LayoutInflater= android.view.LayoutInflater.from(parent.getContext());
        View view=LayoutInflater.inflate(R.layout.lunch_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProductInfo productInfo=productInfoList.get(position);
        holder.textViewName.setText(productInfo.productName);
        holder.textViewdescription.setText(productInfo.description);
        Picasso.get().load(productInfo.imageUrl).placeholder(R.drawable.loading_animated).into(holder.lunchImage);
        holder.textViewLunchPrice.setText(productInfo.price.toString() + " tk");

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
        ImageView lunchImage;
        TextView textViewName;
        TextView textViewdescription;
        TextView textViewLunchPrice;
        TextView lunchTotalPrice;

        ImageButton incrementButton;
        TextView numberOfLunchProducts;
        ImageButton decrementButton;
        TextView totalPrice;

        int count = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lunchImage=itemView.findViewById(R.id.lunchimageView);
            textViewName=itemView.findViewById(R.id.lunchname);
            textViewdescription=itemView.findViewById(R.id.lunchdescription);
            textViewLunchPrice=itemView.findViewById(R.id.lunchprice);
            lunchTotalPrice = itemView.findViewById(R.id.lunchTotalPrice);
            incrementButton = itemView.findViewById(R.id.lunchincrementBtn);
            numberOfLunchProducts = itemView.findViewById(R.id.numberOfLunchProducts);
            decrementButton = itemView.findViewById(R.id.lunchdecrementBtn);
            totalPrice = itemView.findViewById(R.id.lunchTotalPrice);
        }
        public void incrementBtn(int position){

            count++;
            numberOfLunchProducts.setText("" + count);
            updateFinalPrice(position);
        }

        public void decrementBtn(int position){

            if(count <= 0) count = 0;
            else count--;
            numberOfLunchProducts.setText("" + count);
            updateFinalPrice(position);
        }

        public void updateFinalPrice(int position) {
            ProductInfo productInfo = productInfoList.get(position);
            totalPrice.setText("" + (productInfo.price*count) + " tk");
        }


    }
}

