package com.example.kalinga;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.kalinga.Database.Login_DBHelper;
import com.example.kalinga.Database.ProductDatabase;
import com.example.kalinga.Database.Review_database;
import com.example.kalinga.Database.Review_database;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class Splash_Screen extends AppCompatActivity {


    Login_DBHelper DBhelper;
    String dbusername;
    ProductDatabase productDatabase;
    Login_DBHelper login_dbHelper;
    Review_database DBreview;
    String unique;

    private String[] permissionString = new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.PROCESS_OUTGOING_CALLS,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.READ_SMS,
            Manifest.permission.ACCESS_NETWORK_STATE};

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash__screen);
        productDatabase = new ProductDatabase(this);
        DBreview = new Review_database(this);
        login_dbHelper = new Login_DBHelper(this);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(Splash_Screen.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
           // return;
            if (!haspermission(this, (String[]) Arrays.copyOf(this.permissionString, this.permissionString.length))) {
                ActivityCompat.requestPermissions((Activity) this, this.permissionString, 131);
            } else {
                this.delay();
            }
        }
        unique = telephonyManager.getDeviceId();
        // user entry admin
        boolean ch = login_dbHelper.insertData("a","a","12","a",1,unique);
        if(!ch){
            Toast.makeText(Splash_Screen.this, "Admin not created" , Toast.LENGTH_LONG).show();
        }
        String date = DateFormat.getDateInstance(DateFormat.SHORT).format(new Date());
        Toast.makeText(Splash_Screen.this, "" + date, Toast.LENGTH_LONG).show();
        String product_image = "https://firebasestorage.googleapis.com/v0/b/test-6b3af.appspot.com/o/test.jpg?alt=media&token=ff48144c-9bcf-40e1-aaba-e2cfe2b2af29";

        try {
            ch = productDatabase.insert_data("product1", "Decorator", product_image, "Wall Decor", 150
                    , "It is a beautiful masterpeice for decorating the home", "Kaling Kusum", date, "Brown", 1.025, 300, 300, 5);
            if (!ch) {
              //  Toast.makeText(Splash_Screen.this, "Data is not added for 1", Toast.LENGTH_SHORT).show();
            }
            ch = productDatabase.insert_data("product2", "Decorator", product_image, "Wall Decor", 150
                    , "It is a beautiful masterpeice for decorating the home", "Kaling Kusum", date, "Brown", 1.025, 300, 300, 4);
            if (!ch) {
                //Toast.makeText(Splash_Screen.this, "Data is not added for 1", Toast.LENGTH_SHORT).show();
            }
            ch = productDatabase.insert_data("product3", "Decorator", product_image, "Wall Decor", 150
                    , "It is a beautiful masterpeice for decorating the home", "Kaling Kusum", date, "Brown", 1.025, 300, 300, 0);
            if (!ch) {
                //Toast.makeText(Splash_Screen.this, "Data is not added for 1", Toast.LENGTH_SHORT).show();
            }
            ch = productDatabase.insert_data("product4", "Decorator", product_image, "Wall Decor", 150
                    , "It is a beautiful masterpeice for decorating the home", "Kaling Kusum", date, "Brown", 1.025, 300, 300, 4);
            if (!ch) {
                //Toast.makeText(Splash_Screen.this, "Data is not added for 1", Toast.LENGTH_SHORT).show();
            }
            ch = productDatabase.insert_data("product5", "Decorator", product_image, "Wall Decor", 150
                    , "It is a beautiful masterpeice for decorating the home", "Kaling Kusum", date, "Brown", 1.025, 300, 300, 1);
            if (!ch) {
               // Toast.makeText(Splash_Screen.this, "Data is not added for 1", Toast.LENGTH_SHORT).show();
            }
            ch = productDatabase.insert_data("product6", "Decorator", product_image, "Wall Decor", 150
                    , "It is a beautiful masterpeice for decorating the home", "Kaling Kusum", date, "Brown", 1.025, 300, 300, 3);
            if (!ch) {
                //Toast.makeText(Splash_Screen.this, "Data is not added for 1", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(Splash_Screen.this, "product not created", Toast.LENGTH_SHORT).show();
        }

        try {
            ch = DBreview.insert_data("1", "Unknown", "product1", "Beautiful", 4,
                    "beautiful Peice to decorate");
            if (!ch) {
             //   Toast.makeText(Splash_Screen.this, "review is not added for 1", Toast.LENGTH_SHORT).show();
            }
            ch = DBreview.insert_data("2", "Unknown", "product2", "Good", 5,
                    "beautiful Peice to decorate");
            if (!ch) {
               // Toast.makeText(Splash_Screen.this, "review is not added for 2", Toast.LENGTH_SHORT).show();
            }
            ch = DBreview.insert_data("3", "Unknown", "product3", "Great", 3,
                    "beautiful Peice to decorate");
            if (!ch) {
                //Toast.makeText(Splash_Screen.this, "review is not added for 3", Toast.LENGTH_SHORT).show();
            }
            ch = DBreview.insert_data("4", "Unknown", "product4", "Average", 1,
                    "beautiful Peice to decorate");
            if (!ch) {
                //Toast.makeText(Splash_Screen.this, "review is not added for 4", Toast.LENGTH_SHORT).show();
            }
            ch = DBreview.insert_data("5", "Unknown", "product5", "Beautiful", 2,
                    "beautiful Peice to decorate");
            if (!ch) {
                //Toast.makeText(Splash_Screen.this, "review is not added for 5", Toast.LENGTH_SHORT).show();
            }
            ch = DBreview.insert_data("6", "Unknown", "product6", "Nice", 4,
                    "beautiful Peice to decorate");
            if (!ch) {
                //Toast.makeText(Splash_Screen.this, "review is not added for 6", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(Splash_Screen.this, "Review not created", Toast.LENGTH_SHORT).show();
        }


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            if (!haspermission(this, (String[]) Arrays.copyOf(this.permissionString, this.permissionString.length))) {
                ActivityCompat.requestPermissions((Activity) this, this.permissionString, 131);
            } else {
                this.delay();
            }
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    alert("Error", "There is no internet connection");
                }
            }, 500);


        }


    }

    public boolean haspermission(Context context, String[] permissions) {
        boolean hasallpermission = true;
        int len = permissions.length;

        for (int i = 0; i < len; ++i) {
            String permission = permissions[i];
            int res = context.checkCallingOrSelfPermission(permission);
            if (res != PackageManager.PERMISSION_GRANTED) {
                hasallpermission = false;
            }
        }

        return hasallpermission;
    }

    public void delay() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor prefsEditor;
                                    prefsEditor = myPrefs.edit();


                                    Intent start = new Intent((Context) Splash_Screen.this, Home.class);

                                    try {
                                        Cursor check = DBhelper.checklogin(unique);
                                        if (check.getCount() == 0) {
                                            prefsEditor.putString("STOREDVALUE", "LOGIN");
                                            prefsEditor.apply();
                                            Splash_Screen.this.startActivity(start);
                                            Splash_Screen.this.finish();
                                        } else {
                                            while (check.moveToNext()) {
                                                dbusername = check.getString(0);
                                                prefsEditor.putString("STOREDVALUE", dbusername);
                                                prefsEditor.apply();
                                                Splash_Screen.this.startActivity(start);
                                                Splash_Screen.this.finish();
                                            }
                                        }
                                    } catch (Exception e) {

                                        prefsEditor.putString("STOREDVALUE", "LOGIN");
                                        prefsEditor.apply();
                                        Splash_Screen.this.startActivity(start);
                                        Splash_Screen.this.finish();
                                    }
                                }
                            }
                , 1000);

    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 131:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED
                        && grantResults[4] == PackageManager.PERMISSION_GRANTED
                        && grantResults[5] == PackageManager.PERMISSION_GRANTED
                        && grantResults[6] == PackageManager.PERMISSION_GRANTED) {
                    this.delay();
                } else {
                    Toast.makeText((Context) this, "please grant all permission", Toast.LENGTH_SHORT).show();
                    this.finish();
                }

                return;
            default:
                Toast.makeText((Context) this, "Something went wrong", Toast.LENGTH_SHORT).show();
                this.finish();
        }
    }

    public final void alert(String title, String message) {
        AlertDialog alertDialog = (new AlertDialog.Builder((Context) this)).setTitle(title).setMessage(message)
                .setPositiveButton("OK",
                        new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Splash_Screen.this.finish();
                            }
                        }
                ).create();
        alertDialog.show();
    }

}
