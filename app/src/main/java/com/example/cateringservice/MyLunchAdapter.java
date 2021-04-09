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

public class MyLunchAdapter extends RecyclerView.Adapter<MyLunchAdapter.ViewHolder> {
    MyLunchDescription[] myLunchDescription;
    Context context;

    public MyLunchAdapter(MyLunchDescription[] lunchDescriptions, LunchDetails activity) {
        this.myLunchDescription=lunchDescriptions;
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
    public void onBindViewHolder(@NonNull MyLunchAdapter.ViewHolder holder, int position) {
        final MyLunchDescription myLunchDescriptionList=myLunchDescription[position];
        holder.textViewName.setText(myLunchDescriptionList.getLunchName());
        holder.textViewdescription.setText(myLunchDescriptionList.getLunchDescription());
        holder.lunchImage.setImageResource(myLunchDescriptionList.getLunchImage());
        holder.textViewLunchPrice.setText(myLunchDescriptionList.getLunchprice().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,myLunchDescriptionList.getLunchName(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return myLunchDescription.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView lunchImage;
        TextView textViewName;
        TextView textViewdescription;
        TextView textViewLunchPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lunchImage=itemView.findViewById(R.id.lunchimageView);
            textViewName=itemView.findViewById(R.id.lunchname);
            textViewdescription=itemView.findViewById(R.id.lunchdescription);
            textViewLunchPrice=itemView.findViewById(R.id.lunchprice);
        }

    }
}

