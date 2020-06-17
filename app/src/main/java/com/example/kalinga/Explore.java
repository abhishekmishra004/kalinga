package com.example.kalinga;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.kalinga.Adapters.home_singlemodelAda;
import com.example.kalinga.Database.ProductDatabase;
import com.example.kalinga.Models.home_singleproduct;

import java.util.ArrayList;

public class Explore extends AppCompatActivity {

    RecyclerView allProductRec;
    ProductDatabase productDatabase ;
    ArrayList<home_singleproduct> allproduct;
    Toolbar toolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        toolbar = findViewById(R.id.explore);
        setSupportActionBar(toolbar);


        allProductRec = findViewById(R.id.allproduct_rcv);
        productDatabase = new ProductDatabase(this);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rcvlayoutmanager = layoutmanager;
        allProductRec.setLayoutManager(rcvlayoutmanager);
        Cursor res = productDatabase.all_product();
        allproduct = new ArrayList<>();
        if (res.getCount() == 0) {
            alert("Error", "Some Error happend Product not found");
        } else {
            while (res.moveToNext()) {
                allproduct.add(
                        new home_singleproduct(res.getString(2),res.getString(1), res.getString(6), res.getInt(4),res.getString(0)));
            }
        }

        home_singlemodelAda adapter = new home_singlemodelAda(this, allproduct);
        allProductRec.setAdapter(adapter);
        
    }


    public final void alert(String title, String message) {
        AlertDialog alertDialog = (new AlertDialog.Builder((Context) this)).setTitle(title).setMessage(message)
                .setPositiveButton("OK", null).create();
        alertDialog.show();
    }
}
