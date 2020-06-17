package com.example.kalinga;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.kalinga.Database.Login_DBHelper;

public class My_Profile extends AppCompatActivity {

    Login_DBHelper DBhelper;
    String Dbusername,password,phoneNo,Email;
    TextView txtusername,txtphone,txtemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my__profile);
        DBhelper = new Login_DBHelper(this);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("login");
        Dbusername = bundle.getString("user");
        txtusername =findViewById(R.id.Nametxt);
        txtphone = findViewById(R.id.Phonetxt);
        txtemail = findViewById(R.id.emailtxt);

        Cursor check = DBhelper.checkdata(Dbusername);
        if (check.getCount() == 0) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    alert("No User","You Have Not Logined");
                    Intent start = new Intent((Context) My_Profile.this, Home.class);
                    Bundle bun = new Bundle();
                    bun.putString("user", "LOGIN");
                    start.putExtra("login", bun);
                    My_Profile.this.startActivity(start);
                    My_Profile.this.finish();

                }
            },500);


        } else {
            //StringBuffer buffer = new StringBuffer();
            while (check.moveToNext()) {
                        /*buffer.append("Username :"+check.getString(0)+"\n");
                        buffer.append("Password :"+check.getString(1)+"\n");
                        buffer.append("Phone_No :"+check.getString(2)+"\n");
                        buffer.append("Email :"+check.getString(3)+"\n");
                        buffer.append("verified :"+check.getInt(4)+"\n");
                        buffer.append("Islogin :"+check.getInt(5)+"\n");*/
                password = check.getString(1);
                try{
                    phoneNo= check.getString(2);
                }
                catch (Exception e)
                {
                    phoneNo= "Not Provided";
                }
                try{
                    Email = check.getString(3);
                }
                catch (Exception e)
                {
                    Email = "Not Provided";
                }
                txtusername.setText(Dbusername);
                txtphone.setText(phoneNo);
                txtemail.setText(Email);
                break;
            }
        }
    }

    public final void alert(String title, String message) {
        AlertDialog alertDialog = (new AlertDialog.Builder((Context) this)).setTitle(title).setMessage(message)
                .setPositiveButton("OK", null).create();
        alertDialog.show();
    }
}
