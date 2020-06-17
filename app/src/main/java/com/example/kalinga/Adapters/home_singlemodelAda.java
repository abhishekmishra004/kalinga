package com.example.kalinga.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalinga.Models.home_singleproduct;
import com.example.kalinga.R;
import com.example.kalinga.Single_product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class home_singlemodelAda extends RecyclerView.Adapter<home_singlemodelAda.ViewHolder> {

    private Context context;
    private ArrayList<home_singleproduct> mlist;
    public home_singlemodelAda(Context con, ArrayList<home_singleproduct> list)
    {
        context=con;
        mlist=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.home_allproduct_single, viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final home_singleproduct singleproduct = mlist.get(i);
        int p= singleproduct.getPrice();
        String price = "Rs "+String.valueOf(p);
        ImageView image = viewHolder.item_image;
        TextView productName,SellerName,Price;
        productName = viewHolder.pro_name;
        SellerName = viewHolder.sell_name;
        Price = viewHolder.price;
        Picasso.get().load(singleproduct.getImage()).into(image);
        productName.setText(singleproduct.getProduct_name());
        SellerName.setText(singleproduct.getSeller_name());
        Price.setText(price);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Single_product.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",singleproduct.getProduct_id());
                intent.putExtra("pro",bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView item_image;
        TextView pro_name,sell_name,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.product_image);
            pro_name= itemView.findViewById(R.id.product_name);
            sell_name = itemView.findViewById(R.id.seller_name);
            price = itemView.findViewById(R.id.price);
        }
    }

}
