package com.example.kalinga.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kalinga.Models.recently_added;
import com.example.kalinga.R;
import com.example.kalinga.Single_product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_recently_added extends RecyclerView.Adapter<Adapter_recently_added.ViewHolder> {

    private Context context;
    private ArrayList<recently_added> mlist;

    public Adapter_recently_added(@NonNull Context con,ArrayList<recently_added> list) {
        context=con;
        mlist=list;
    }


    @NonNull
    @Override
    public Adapter_recently_added.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.card_recently_added, viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final recently_added recentlyAdded = mlist.get(i);
        final String product_id  = recentlyAdded.getProduct_id();
        String product_image = recentlyAdded.getProduct_link();
        ImageView imageview = viewHolder.image;
        Picasso.get().load(product_image).into(imageview);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,product_id,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Single_product.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",product_id);
                intent.putExtra("pro",bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.single_recent_added);
        }
    }
}
