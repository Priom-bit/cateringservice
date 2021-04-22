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

public class MyBreakfastAdapter extends RecyclerView.Adapter<MyBreakfastAdapter.ViewHolder> {
    List<ProductInfo> productInfoList;
    Context context;

    public MyBreakfastAdapter(List<ProductInfo> productInfoList, BreakfastDetails activity) {
        this.productInfoList=productInfoList;
        this.context=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater LayoutInflater= android.view.LayoutInflater.from(parent.getContext());
        View view=LayoutInflater.inflate(R.layout.breakfast_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProductInfo productInfo=productInfoList.get(position);
        holder.textViewName.setText(productInfo.productName);
        holder.textViewdescription.setText(productInfo.description);
        Picasso.get().load(productInfo.imageUrl).placeholder(R.drawable.loading_animated).into(holder.breakfastImage);
        holder.textViewBreakfastPrice.setText(productInfo.price.toString() + " tk");

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
        ImageView breakfastImage;
        TextView textViewName;
        TextView textViewdescription;
        TextView textViewBreakfastPrice;
        TextView breakfastTotalPrice;

        ImageButton incrementButton;
        TextView numberOfBreakfastProducts;
        ImageButton decrementButton;
        TextView totalPrice;

        int count = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            breakfastImage=itemView.findViewById(R.id.breakfastImageView);
            textViewName=itemView.findViewById(R.id.breakfastname);
            textViewdescription=itemView.findViewById(R.id.breakfastdescription);
            textViewBreakfastPrice=itemView.findViewById(R.id.breakfastprice);
            breakfastTotalPrice = itemView.findViewById(R.id.breakfastTotalPrice);
            incrementButton = itemView.findViewById(R.id.breakfastincrementBtn);
            numberOfBreakfastProducts = itemView.findViewById(R.id.numberOfBreakfastProducts);
            decrementButton = itemView.findViewById(R.id.breakfastdecrementBtn);
            totalPrice = itemView.findViewById(R.id.breakfastTotalPrice);
        }
        public void incrementBtn(int position){

            count++;
            numberOfBreakfastProducts.setText("" + count);
            updateFinalPrice(position);
        }

        public void decrementBtn(int position){

            if(count <= 0) count = 0;
            else count--;
            numberOfBreakfastProducts.setText("" + count);
            updateFinalPrice(position);
        }

        public void updateFinalPrice(int position) {
            ProductInfo productInfo = productInfoList.get(position);
            totalPrice.setText("" + (productInfo.price*count) + " tk");
        }

    }
}



