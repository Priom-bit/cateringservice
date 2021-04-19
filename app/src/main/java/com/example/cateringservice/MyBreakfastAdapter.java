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

import java.util.List;

public class MyBreakfastAdapter extends RecyclerView.Adapter<MyBreakfastAdapter.ViewHolder> {
    List<ProductInfo> myBreakfastDescription;
    Context context;

    public MyBreakfastAdapter(List<ProductInfo> breakfastDescriptions, BreakfastDetails activity) {
        this.myBreakfastDescription=breakfastDescriptions;
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
    public void onBindViewHolder(@NonNull MyBreakfastAdapter.ViewHolder holder, int position) {
        final ProductInfo productInfo=myBreakfastDescription.get(position);
        holder.textViewName.setText(productInfo.productName);
        holder.textViewdescription.setText(productInfo.description);
        Picasso.get().load(productInfo.imageUrl).into(holder.breakfastImage);
        holder.textViewBreakfastPrice.setText(productInfo.price.toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,productInfo.productName,Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return myBreakfastDescription.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView breakfastImage;
        TextView textViewName;
        TextView textViewdescription;
        TextView textViewBreakfastPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            breakfastImage=itemView.findViewById(R.id.breakfastImageView);
            textViewName=itemView.findViewById(R.id.breakfastname);
            textViewdescription=itemView.findViewById(R.id.breakfastdescription);
            textViewBreakfastPrice=itemView.findViewById(R.id.breakfastprice);
        }

    }
}
