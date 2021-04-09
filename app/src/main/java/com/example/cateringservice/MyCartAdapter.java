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

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    MyCartDescription[] myDrinksDescription;
    Context context;

    public MyCartAdapter(MyCartDescription[]myDrinksDescriptions, CartDetails activity) {
        this.myDrinksDescription=myDrinksDescriptions;
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
        final MyCartDescription myDrinksDescriptionList=myDrinksDescription[position];
        holder.textViewName.setText(myDrinksDescriptionList.getDrinksName());
        holder.textViewdescription.setText(myDrinksDescriptionList.getDrinksDescription());
        holder.drinksImage.setImageResource(myDrinksDescriptionList.getDrinksImage());
        holder.textViewDrinkPrice.setText(myDrinksDescriptionList.getDrinksprice().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,myDrinksDescriptionList.getDrinksName(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return myDrinksDescription.length;
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
