package com.example.cateringservice;

import android.content.Context;
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

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    private final String TAG = MyCartAdapter.class.getSimpleName();
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
        Picasso.get().load(productInfo.imageUrl).placeholder(R.drawable.loading_animated).into(holder.cartImage);
        holder.textViewCartPrice.setText(productInfo.price.toString() + " tk");
        holder.numberOfCartProducts.setText(Integer.toString(productInfo.count));
        holder.totalPrice.setText("" + (productInfo.price*productInfo.count) + " tk");

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

        ImageButton incrementButton;
        TextView numberOfCartProducts;
        ImageButton decrementButton;
        TextView totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartImage=itemView.findViewById(R.id.cartimageView);
            textViewName=itemView.findViewById(R.id.cartname);
            textViewdescription=itemView.findViewById(R.id.cartdescription);
            textViewCartPrice = itemView.findViewById(R.id.cartPrice);
            incrementButton = itemView.findViewById(R.id.cartIncrementBtn);
            numberOfCartProducts = itemView.findViewById(R.id.numberOfCartProducts);
            decrementButton = itemView.findViewById(R.id.cartDecrementBtn);
            totalPrice = itemView.findViewById(R.id.cartTotalPrice);
        }
        public void incrementBtn(int position){
            ProductInfo productInfo = productInfoList.get(position);

            productInfo.count++;
            numberOfCartProducts.setText("" + productInfo.count);
            updateFinalPrice(position);
        }

        public void decrementBtn(int position){
            ProductInfo productInfo = productInfoList.get(position);

            if(productInfo.count <= 0) productInfo.count = 0;
            else productInfo.count--;
            numberOfCartProducts.setText("" + productInfo.count);
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
