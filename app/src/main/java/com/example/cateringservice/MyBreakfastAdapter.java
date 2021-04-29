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
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyBreakfastAdapter extends RecyclerView.Adapter<MyBreakfastAdapter.ViewHolder> {
    private final String TAG = MyBreakfastAdapter.class.getSimpleName();
    List<ProductInfo> productInfoList;

    CSConstants.RecyclerViewOnClickListener itemClickListener;

    public MyBreakfastAdapter(List<ProductInfo> productInfoList, CSConstants.RecyclerViewOnClickListener listener) {
        this.productInfoList=productInfoList;
        this.itemClickListener = listener;
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
        holder.numberOfBreakfastProducts.setText(Integer.toString(productInfo.count));
        holder.totalPrice.setText("" + (productInfo.price*productInfo.count) + " tk");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "You clicked at: position: " + position);
                itemClickListener.OnItemClicked(position);
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

        ImageButton incrementButton;
        TextView numberOfBreakfastProducts;
        ImageButton decrementButton;
        TextView totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            breakfastImage=itemView.findViewById(R.id.breakfastImageView);
            textViewName=itemView.findViewById(R.id.breakfastname);
            textViewdescription=itemView.findViewById(R.id.breakfastdescription);
            textViewBreakfastPrice=itemView.findViewById(R.id.breakfastprice);
            incrementButton = itemView.findViewById(R.id.breakfastincrementBtn);
            numberOfBreakfastProducts = itemView.findViewById(R.id.numberOfBreakfastProducts);
            decrementButton = itemView.findViewById(R.id.breakfastdecrementBtn);
            totalPrice = itemView.findViewById(R.id.breakfastTotalPrice);
        }
        public void incrementBtn(int position){
            ProductInfo productInfo = productInfoList.get(position);

            productInfo.count++;
            numberOfBreakfastProducts.setText("" + productInfo.count);
            updateFinalPrice(position);
        }

        public void decrementBtn(int position){
            ProductInfo productInfo = productInfoList.get(position);

            if(productInfo.count <= 0) productInfo.count = 0;
            else productInfo.count--;
            numberOfBreakfastProducts.setText("" + productInfo.count);
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
                for (ProductInfo productInfo : AppManager.getInstance().selectedBreakfastList) {
                    if (productInfo.id.equals(_productInfo.id)) {
                        isFound = true;
                        int index = AppManager.getInstance().selectedBreakfastList.indexOf(productInfo);
                        AppManager.getInstance().selectedBreakfastList.set(index, _productInfo);
                    }
                }
                if (!isFound) {
                    AppManager.getInstance().selectedBreakfastList.add(_productInfo);
                }
            }
            else {
                ProductInfo productInfo = productInfoList.get(position);
                AppManager.getInstance().selectedBreakfastList.remove(productInfo);
            }
            Log.v(TAG, "Nirob test drinks list: " + AppManager.getInstance().selectedBreakfastList.size());
        }

    }
}



