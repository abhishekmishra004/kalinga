package com.example.kalinga;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalinga.Adapters.Adapter_recently_added;
import com.example.kalinga.Adapters.home_singlemodelAda;
import com.example.kalinga.Database.Login_DBHelper;
import com.example.kalinga.Database.ProductDatabase;
import com.example.kalinga.Database.Review_database;
import com.example.kalinga.Models.home_singleproduct;
import com.example.kalinga.Models.recently_added;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Home extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerlayout;
    NavigationView navigationView;
    GridView gview;
    RecyclerView recyclerview,rcvRecentlyAdded,rcvFrequentbuys,rcvFeaturedproduct;
    ArrayList<home_singleproduct> allproduct;
    ArrayList<recently_added> recentAdded;
    String Dbusername;
    Login_DBHelper dbhelper;
    ProductDatabase productDatabase;
    Review_database review_database;



    public String[] item = new String[]
            {
                    "Category1", "Category 2", "Category 3", "Category 4", "Category 5", "Category 6"
            };
    final List<String> itemlist = new ArrayList<String>(Arrays.asList(item));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_navigation_drawer);
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        dbhelper = new Login_DBHelper(this);
        productDatabase = new ProductDatabase(this);
        review_database = new Review_database(this);

        drawerlayout = findViewById(R.id.home_drawerlayout);
        navigationView = findViewById(R.id.home_navigation);

        rcvRecentlyAdded =findViewById(R.id.rvRecentlyAdded);
        rcvFrequentbuys = findViewById(R.id.rvFrequentBuys);
        rcvFeaturedproduct = findViewById(R.id.rvFeaturedProduct);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,
                drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerlayout.setDrawerListener(toogle);
        toogle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        toogle.syncState();


        gview = findViewById(R.id.Grid_home);

        gview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemlist) {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                tv.setText(itemlist.get(position));
                tv.setBackgroundColor(Color.parseColor("#c6c6c6"));
                tv.setTextColor(Color.parseColor("#000000"));
                return tv;
            }
        });

        View headerview = navigationView.getHeaderView(0);
        final TextView txtview_edit = (TextView) headerview.findViewById(R.id.Login_text);

        SharedPreferences myPrefs;
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Dbusername = myPrefs.getString("STOREDVALUE", "LOGIN");
        txtview_edit.setText(Dbusername);


        if(Dbusername.equals("LOGIN"))
             navigationView.getMenu().getItem(6).setTitle("Login");
        else
            navigationView.getMenu().getItem(6).setTitle("Logout");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.Explore:{
                        Intent intent = new Intent(Home.this, Explore.class);
                        startActivity(intent);
                        break;
                    }

                    case R.id.my_Profile:{

                        try {
                                   Intent intent = new Intent(Home.this, My_Profile.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("user",Dbusername);
                                    intent.putExtra("login",bundle);
                                    startActivity(intent);

                        }
                        catch (Exception e)
                        {
                            Toast.makeText(Home.this,"exception"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }




                        break;
                    }
                    case R.id.my_cart:{
                        Toast.makeText(Home.this,"My_Cart",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Home.this, Cart.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("user",Dbusername);
                        intent.putExtra("login",bundle);
                        startActivity(intent);
                        break;
                    }
                    case R.id.my_order:{
                        break;
                    }case R.id.my_wishlist:{
                    Toast.makeText(Home.this,"My_wishlist",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Home.this, Wishlist.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("user",Dbusername);
                    intent.putExtra("login",bundle);
                    startActivity(intent);
                    break;
                }
                    case R.id.Settings:{
                        Toast.makeText(Home.this,"Settings",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.Logout:{
                        if(Dbusername.equals("LOGIN"))
                        {
                            Intent intent = new Intent(Home.this, Log_in.class);
                            startActivity(intent);
                        }
                        else{
                            final AlertDialog.Builder builder =new AlertDialog.Builder(Home.this);
                            builder.setMessage("Are you Sure?");
                            builder.setTitle("Confirm");
                            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dbhelper.updatelogout(Dbusername);
                                    View headerview = navigationView.getHeaderView(0);
                                    TextView txtview_edit = (TextView) headerview.findViewById(R.id.Login_text);
                                    txtview_edit.setText("LOGIN");
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();

                        }
                        break;
                    }
                    case R.id.nav_share:{
                        Toast.makeText(Home.this,"Share",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.nav_aboutus:{
                        Toast.makeText(Home.this,"About us",Toast.LENGTH_SHORT).show();
                        break;
                    }


                }

                return true;
            }
        });



        //recyclerview();
        recentlyAdded();
        frequentBuys();
        featuredProduct();
    }



    public void recentlyAdded()
    {
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayout.HORIZONTAL);
        RecyclerView.LayoutManager rcvlayoutmanager = layoutmanager;
        rcvRecentlyAdded.setLayoutManager(rcvlayoutmanager);

        Cursor res = productDatabase.recently_added();
        recentAdded = new ArrayList<>();
        if(res.getCount() == 0)
        {
            alert("Error", "Some Error happend Product not found");
        }
        else
        {
            while (res.moveToNext()) {
                recentAdded.add(
                        new recently_added(res.getString(0),res.getString(2)));
            }
        }
        Adapter_recently_added  adapter = new Adapter_recently_added(this,recentAdded);
        rcvRecentlyAdded.setAdapter(adapter);
    }

    public void frequentBuys()
    {
       LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayout.HORIZONTAL);
        RecyclerView.LayoutManager rcvlayoutmanager = layoutmanager;
        rcvFrequentbuys.setLayoutManager(rcvlayoutmanager);
        Cursor res = productDatabase.frequent_buys();
        recentAdded = new ArrayList<>();
        if(res.getCount() == 0)
        {
            alert("Error", "Some Error happend Product not found");
        }
        else
        {
            while (res.moveToNext()) {
                recentAdded.add(
                        new recently_added(res.getString(0),res.getString(2)));
            }
        }
        Adapter_recently_added  adapter = new Adapter_recently_added(this,recentAdded);
        rcvFrequentbuys.setAdapter(adapter);
    }

    public void featuredProduct()
    {
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayout.HORIZONTAL);
        RecyclerView.LayoutManager rcvlayoutmanager = layoutmanager;
        rcvFeaturedproduct.setLayoutManager(rcvlayoutmanager);
        Cursor res = review_database.frequentProduct();
        recentAdded = new ArrayList<>();
        if(res.getCount() == 0)
        {
            alert("Error", "Some Error happend Product not found");
        }
        else
        {
            while (res.moveToNext()) {
                String id = res.getString(2);
                Cursor cs = productDatabase.find_product(id);
                if(cs.getCount()==0)
                {
                    alert("Error", "Some Error happend Product not found");
                }
                else
                {

                    while(cs.moveToNext())
                    {

                        String url  = cs.getString(2);
                        recentAdded.add(
                                new recently_added(id,url));
                        break;
                    }

                }
            }
        }
        Adapter_recently_added  adapter = new Adapter_recently_added(this,recentAdded);
        rcvFeaturedproduct.setAdapter(adapter);
    }


    public final void alert(String title, String message) {
        AlertDialog alertDialog = (new AlertDialog.Builder((Context) this)).setTitle(title).setMessage(message)
                .setPositiveButton("OK", null).create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(Gravity.LEFT); //CLOSE Nav Drawer!
        }
        else{
        Home.this.finish();
        }
    }
}
