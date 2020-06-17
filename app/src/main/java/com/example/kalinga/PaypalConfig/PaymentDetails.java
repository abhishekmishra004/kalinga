package com.example.kalinga.PaypalConfig;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalinga.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {

    TextView txtid,txtamount,txtstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        txtid = findViewById(R.id.txtId);
        txtamount =findViewById(R.id.txtAmount);
        txtstatus = findViewById(R.id.txtStatus);

        Intent intent = this.getIntent();
        try
        {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(PaymentDetails.this,"error"+intent.getStringExtra("PaymentDetails")+"\n"+
                    intent.getStringExtra("PaymentAmount")+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void showDetails(JSONObject response,String paymentAmount)
    {
        try {
            txtid.setText(response.getString("id"));
            txtstatus.setText(response.getString("state"));
            txtamount.setText(("$ "+paymentAmount));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(PaymentDetails.this,"error"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
