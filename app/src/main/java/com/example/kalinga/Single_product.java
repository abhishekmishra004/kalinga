package com.example.kalinga;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalinga.Database.Login_DBHelper;
import com.example.kalinga.Database.ProductDatabase;
import com.example.kalinga.PaypalConfig.Config;
import com.example.kalinga.PaypalConfig.PaymentDetails;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.math.BigDecimal;

public class Single_product extends AppCompatActivity {

    ProductDatabase productDatabase;
    String product_id,Userid;
    ImageView product_image;
    TextView weigth,height,color,width,product_details,product_name,price;
    String amount ="",StoredValue="",Product_id = "";
    Button buy_button,add_cart,add_wish;
    Login_DBHelper dbhelper;



    public static  final  int PAYPAL_REQUEST_CODE = 7171;
    public  static PayPalConfiguration configuration = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLENT_ID);


    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product);
        productDatabase = new ProductDatabase(this);
        product_image = findViewById(R.id.ProductImage);
        weigth = findViewById(R.id.weight);
        width = findViewById(R.id.width);
        color = findViewById(R.id.color);
        height = findViewById(R.id.height);
        product_details = findViewById(R.id.Product_details);
        product_name = findViewById(R.id.Product_Name);
        price = findViewById(R.id.Total_price);
        buy_button =findViewById(R.id.Button_buy);
        add_cart = findViewById(R.id.Button_cart);
        add_wish = findViewById(R.id.Button_wishlist);
        dbhelper = new Login_DBHelper(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("pro");
        product_id =bundle.getString("id");
        Intent intent1 = new Intent(this,PayPalService.class);
        intent1.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,configuration);
        startService(intent1);

        Cursor res = productDatabase.find_product(product_id);
        if (res.getCount() == 0) {
            alert("Error", "Some Error happend Product not found");
        } else {
            while (res.moveToNext()) {
                Picasso.get().load(res.getString(2)).into(product_image);
                product_name.setText(res.getString(1));
                product_details.setText(res.getString(5));
                color.setText(res.getString(8));
                price.setText(String.valueOf(res.getInt(4)));
                amount = String.valueOf(res.getInt(4));
                weigth.setText(String.format("%s Kg", String.valueOf(res.getDouble(9))));
                width.setText(String.format("%s Cm", String.valueOf(res.getInt(10))));
                height.setText(String.format("%s Cm", String.valueOf(res.getInt(11))));
                break;
            }
        }
        SharedPreferences myPrefs;
        myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Userid = myPrefs.getString("STOREDVALUE", "");
        clickhandler();
    }


    void clickhandler()
    {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint({"MissingPermission", "HardwareIds"}) final String unique = telephonyManager.getDeviceId();
        buy_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //  check login for payment

                try {

                    if(Userid.equals("") || Userid.equals("LOGIN"))
                    {
                        Toast.makeText(Single_product.this,"User not Login",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        payment();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(Single_product.this,"User not Login"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                //

            }

        });


        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Userid.equals("") || Userid.equals("LOGIN"))
                {
                    Toast.makeText(Single_product.this,"User not Login",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(dbhelper.Add_cart(Userid,product_id))
                    {
                        Toast.makeText(Single_product.this,"Product Added",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Single_product.this,"Product Didn't Added",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        add_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Userid.equals("") || Userid.equals("LOGIN"))
                {
                    Toast.makeText(Single_product.this,"User not Login",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(dbhelper.Add_wish(Userid,product_id))
                    {
                        Toast.makeText(Single_product.this,"Product Added",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Single_product.this,"Product Didn't Added",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }

    void payment()
    {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD",
                "Pay For Product",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(Single_product.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PAYPAL_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation !=null)
                {
                    try
                    {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        Toast.makeText(Single_product.this,paymentDetails,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, PaymentDetails.class)
                                    .putExtra("PaymentDetails",paymentDetails)
                                    .putExtra("PaymentAmount",amount));
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            else if(resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(Single_product.this,"Canceled",Toast.LENGTH_SHORT).show();
        }
        else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(Single_product.this,"Invalid",Toast.LENGTH_SHORT).show();
    }

    public final void alert(String title, String message) {
        AlertDialog alertDialog = (new AlertDialog.Builder((Context) this)).setTitle(title).setMessage(message)
                .setPositiveButton("OK", null).create();
        alertDialog.show();
    }
}
