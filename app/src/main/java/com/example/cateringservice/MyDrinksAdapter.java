package com.example.cateringservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cateringservice.models.ProductInfo;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

public class MyDrinksAdapter extends RecyclerView.Adapter<MyDrinksAdapter.ViewHolder> {
    List<ProductInfo> productInfoList;
    Context context;

    public MyDrinksAdapter(List<ProductInfo> _productInfoList, DrinksDetails activity) {
        this.productInfoList = _productInfoList;
        this.context=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater LayoutInflater= android.view.LayoutInflater.from(parent.getContext());
        View view=LayoutInflater.inflate(R.layout.drinks_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProductInfo productInfo = productInfoList.get(position);
        holder.textViewName.setText(productInfo.productName);
        holder.textViewdescription.setText(productInfo.description);
        //holder.drinksImage.setImageResource();
        Picasso.get().load(productInfo.imageUrl).into(holder.drinksImage);
        holder.textViewDrinkPrice.setText(productInfo.price.toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, productInfo.productName,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView drinksImage;
        TextView textViewName;
        TextView textViewdescription;
        TextView textViewDrinkPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            drinksImage=itemView.findViewById(R.id.drinksimageView);
            textViewName=itemView.findViewById(R.id.drinksname);
            textViewdescription=itemView.findViewById(R.id.drinksdescription);
            textViewDrinkPrice = itemView.findViewById(R.id.drinksPrice);
        }

    }
}
