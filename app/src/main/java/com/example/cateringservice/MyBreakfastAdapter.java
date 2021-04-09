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

public class MyBreakfastAdapter extends RecyclerView.Adapter<MyBreakfastAdapter.ViewHolder> {
    MyBreakfastDescription[] myBreakfastDescription;
    Context context;

    public MyBreakfastAdapter(MyBreakfastDescription[] breakfastDescriptions, BreakfastDetails activity) {
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
        final MyBreakfastDescription myBreakfastDescriptionList=myBreakfastDescription[position];
        holder.textViewName.setText(myBreakfastDescriptionList.getBreakfastName());
        holder.textViewdescription.setText(myBreakfastDescriptionList.getBreakfastDescription());
        holder.breakfastImage.setImageResource(myBreakfastDescriptionList.getBreakfastImage());
        holder.textViewBreakfastPrice.setText(myBreakfastDescriptionList.getBreakfastprice().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,myBreakfastDescriptionList.getBreakfastName(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return myBreakfastDescription.length;
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
