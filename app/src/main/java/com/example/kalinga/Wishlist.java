package com.example.kalinga;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.kalinga.Database.Login_DBHelper;
import com.example.kalinga.Database.ProductDatabase;
import com.example.kalinga.Models.home_singleproduct;

import java.util.ArrayList;

public class Wishlist extends AppCompatActivity {

    Toolbar toolbar;
    String Username;
    Login_DBHelper dbHelper;
    RecyclerView rcvWishAdded;
    String[] productid;
    ProductDatabase pdhelper;
    ArrayList<home_singleproduct> allproduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        toolbar = findViewById(R.id.wish_toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new Login_DBHelper(this);

        SharedPreferences myPrefs;
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Username = myPrefs.getString("STOREDVALUE", "");
        Cursor cursor  =  dbHelper.View_wish(Username);


    }
}
