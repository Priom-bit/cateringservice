package com.example.cateringservice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cateringservice.manager.AppManager;
import com.example.cateringservice.models.ProductInfo;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

public class MyDrinksAdapter extends RecyclerView.Adapter<MyDrinksAdapter.ViewHolder> {
    private final String TAG = MyDrinksAdapter.class.getSimpleName();
    List<ProductInfo> productInfoList;
    CSConstants.RecyclerViewOnClickListener itemClickListener;

    public interface DrinksOnClickListener {
        void OnItemClicked(int position);
    }

    public MyDrinksAdapter(List<ProductInfo> productInfoList, CSConstants.RecyclerViewOnClickListener listener) {
        this.productInfoList = productInfoList;
        this.itemClickListener = listener;
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
        Picasso.get().load(productInfo.imageUrl).placeholder(R.drawable.loading_animated).into(holder.drinksImage);
        holder.textViewDrinkPrice.setText(productInfo.price.toString() + " tk");
        holder.numberOfDrinkProducts.setText(Integer.toString(productInfo.count));
        holder.totalPrice.setText("" + (productInfo.price*productInfo.count) + " tk");

        holder.itemView.setOnClickListener(v -> {
            Log.v(TAG, "You clicked at: position: " + position);
            this.itemClickListener.OnItemClicked(position);
        });

        holder.incrementButton.setOnClickListener(view -> {
            holder.incrementBtn(position);
        });

        holder.decrementButton.setOnClickListener(view -> {
            holder.decrementBtn(position);
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

        ImageButton incrementButton;
        TextView numberOfDrinkProducts;
        ImageButton decrementButton;
        TextView totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            drinksImage=itemView.findViewById(R.id.drinksimageView);
            textViewName=itemView.findViewById(R.id.drinksname);
            textViewdescription=itemView.findViewById(R.id.drinksdescription);
            textViewDrinkPrice = itemView.findViewById(R.id.drinksPrice);
            incrementButton = itemView.findViewById(R.id.drinkIncrementBtn);
            numberOfDrinkProducts = itemView.findViewById(R.id.numberOfDrinkProducts);
            decrementButton = itemView.findViewById(R.id.drinkDecrementBtn);
            totalPrice = itemView.findViewById(R.id.drinksTotalPrice);
        }

        public void incrementBtn(int position){
            ProductInfo productInfo = productInfoList.get(position);
            productInfo.count++;
            numberOfDrinkProducts.setText("" + productInfo.count);
            updateFinalPrice(position);
        }

        public void decrementBtn(int position){

            ProductInfo productInfo = productInfoList.get(position);
            if(productInfo.count <= 0) productInfo.count = 0;
            else productInfo.count--;
            numberOfDrinkProducts.setText("" + productInfo.count);
            updateFinalPrice(position);
        }

        public void updateFinalPrice(int position) {
            ProductInfo productInfo = productInfoList.get(position);
            totalPrice.setText("" + (productInfo.price*productInfo.count) + " tk");
            replaceIfFound(productInfo, position);
        }

        private void replaceIfFound(ProductInfo _productInfo, int position) {
            if (_productInfo.count > 0) {
                boolean isFound = false;
                for (ProductInfo productInfo : AppManager.getInstance().selectedDrinksList) {
                    if (productInfo.id.equals(_productInfo.id)) {
                        isFound = true;
                        int index = AppManager.getInstance().selectedDrinksList.indexOf(productInfo);
                        AppManager.getInstance().selectedDrinksList.set(index, _productInfo);
                    }
                }
                if (!isFound) {
                    AppManager.getInstance().selectedDrinksList.add(_productInfo);
                }
            }
            else {
                ProductInfo productInfo = productInfoList.get(position);
                AppManager.getInstance().selectedDrinksList.remove(productInfo);
            }
            Log.v(TAG, "Nirob test drinks list: " + AppManager.getInstance().selectedDrinksList.size());
        }

    }
}
