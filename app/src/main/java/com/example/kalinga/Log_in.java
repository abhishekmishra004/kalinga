package com.example.kalinga;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.kalinga.Database.Login_DBHelper;

public class Log_in extends AppCompatActivity {

    TextView txtview_signup;
    TextView forgetpass;
    EditText usernameedit;
    EditText passwordedit;
    String username, password;
    Login_DBHelper DBhelper;

    Button Loginbutton;

    String dbusername, dbpassword, dbphoneno, dbemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        DBhelper = new Login_DBHelper(this);
        usernameedit = findViewById(R.id.user_edittext);
        passwordedit = findViewById(R.id.pass_edittext);
        Loginbutton = findViewById(R.id.login_button);



        txtview_signup = findViewById(R.id.signup_text);
        txtview_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Log_in.this, Signup.class);
                startActivity(intent);
            }
        });
        forgetpass = findViewById(R.id.forget_text);
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Log_in.this, ForgetPassword.class);
                startActivity(intent);
            }
        });


        clickhandler();
    }

    public void clickhandler() {

        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = usernameedit.getText().toString();
                password = passwordedit.getText().toString();
                username.trim();
                password.trim();
                username =username.toLowerCase();
                password = password.toLowerCase();
                Toast.makeText(Log_in.this, username, Toast.LENGTH_LONG).show();

                Cursor check = DBhelper.checkdata(username);
                if (check.getCount() == 0) {
                    alert("Error", "Some Error happend User not found");
                } else {
                    //StringBuffer buffer = new StringBuffer();
                    while (check.moveToNext()) {
                        /*buffer.append("Username :"+check.getString(0)+"\n");
                        buffer.append("Password :"+check.getString(1)+"\n");
                        buffer.append("Phone_No :"+check.getString(2)+"\n");
                        buffer.append("Email :"+check.getString(3)+"\n");
                        buffer.append("verified :"+check.getInt(4)+"\n");
                        buffer.append("Islogin :"+check.getInt(5)+"\n");*/
                        dbusername = check.getString(0);
                        dbpassword = check.getString(1);
                    }
                    if (!password.equals(dbpassword)) {
                        alert("Mismatch", "Password didnot matched");
                        return;
                    }

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                alert("Welcome", "Welcome back " + dbusername);
                                                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                                                @SuppressLint("MissingPermission")
                                                String unique = telephonyManager.getDeviceId();
                                                DBhelper.updatelogin(dbusername,unique);
                                                Intent intent = new Intent(Log_in.this, Home.class);
                                                SharedPreferences myPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                                SharedPreferences.Editor prefsEditor;
                                                prefsEditor = myPrefs.edit();
                                                prefsEditor.putString("STOREDVALUE", dbusername);
                                                prefsEditor.apply();
                                                startActivity(intent);
                                                Log_in.this.finish();

                                            }
                                        }
                            , 300);
                }
            }
        });


    }

    public final void alert(String title, String message) {
        AlertDialog alertDialog = (new AlertDialog.Builder((Context) this)).setTitle(title).setMessage(message)
                .setPositiveButton("OK", null).create();
        alertDialog.show();
    }


}
