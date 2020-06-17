package com.example.kalinga;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kalinga.Adapters.Adapter_recently_added;
import com.example.kalinga.Adapters.home_singlemodelAda;
import com.example.kalinga.Database.AddressDBhelper;
import com.example.kalinga.Database.Login_DBHelper;
import com.example.kalinga.Database.ProductDatabase;
import com.example.kalinga.Models.home_singleproduct;
import com.example.kalinga.Models.recently_added;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    Toolbar toolbar;
    String Username;
    Login_DBHelper dbHelper;
    RecyclerView rcvCartAdded;
    String[] productid;
    ProductDatabase pdhelper;
    ArrayList<home_singleproduct> allproduct;
    Button buybutton;
    AddressDBhelper adhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        toolbar = findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);

        adhelper = new AddressDBhelper(this);
        dbHelper = new Login_DBHelper(this);
        pdhelper = new ProductDatabase(this);
        buybutton = findViewById(R.id.Buy_button);

        toolbar.setNavigationIcon(R.drawable.blackarrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences myPrefs;
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Username = myPrefs.getString("STOREDVALUE", "");
        rcvCartAdded = findViewById(R.id.rcvCart);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayout.HORIZONTAL);
        RecyclerView.LayoutManager rcvlayoutmanager = layoutmanager;
        rcvCartAdded.setLayoutManager(rcvlayoutmanager);
        Cursor cursor  =  dbHelper.View_cart(Username);
        productid = new String[cursor.getCount()];
        int i=0;
        while(cursor.moveToNext())
        {
            productid[i] = cursor.getString(1);
            i++;
        }
        allproduct = new ArrayList<>();
        int TotalPrice = 0;
        for(i=0;i < cursor.getCount();i++)
        {
            Cursor res = pdhelper.find_product(productid[i]);
            while(res.moveToNext())
            {
                allproduct.add(
                        new home_singleproduct(res.getString(2),res.getString(1), res.getString(6), res.getInt(4),res.getString(0)));
                TotalPrice += res.getInt(4);
            }
        }

        final home_singlemodelAda adapter = new home_singlemodelAda(this, allproduct);
        rcvCartAdded.setAdapter(adapter);

        buybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cs = adhelper.ViewAddress(Username);
                final String[] address = new String[cs.getCount()];
                int checkeditem =0;
                int i=0;
                while(cs.moveToNext())
                {
                    address[i] = "House no="+cs.getString(2)+
                                    "\nStreet ="+cs.getString(3)+
                                    "\nCity ="+cs.getString(4)+
                                    "\nDistrict ="+cs.getString(5)+
                                    "\nState ="+cs.getString(6)+
                                    "\nLandmark ="+cs.getString(8)+
                                    "\nPin ="+cs.getString(7)+"\n";
                    i++;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
                builder.setTitle("Select Address");
                builder.setSingleChoiceItems(address, checkeditem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Cart.this,address[which],Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Add Address", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }
    public final void alert(String title, String message) {
        AlertDialog alertDialog = (new AlertDialog.Builder((Context) this)).setTitle(title).setMessage(message)
                .setPositiveButton("OK", null).create();
        alertDialog.show();
    }
}
