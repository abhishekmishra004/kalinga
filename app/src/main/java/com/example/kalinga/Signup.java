package com.example.kalinga;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kalinga.Database.Login_DBHelper;

import java.sql.Connection;
import java.sql.Statement;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    Login_DBHelper userdata;
    EditText usernameedit,passedit,confpassedit,phoneedit,emailedit;
    public String Username,Password,Phone_No,Email,confpass;
    Button signupbutton;
    CheckBox phonecheck,emailcheck;
    SQLiteDatabase db ;// = new SQLiteDatabase();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        userdata = new Login_DBHelper(this);
        usernameedit = findViewById(R.id.username_edittext);
        passedit = findViewById(R.id.password_edittext);
        confpassedit = findViewById(R.id.confpassword_edittext);
        phoneedit = findViewById(R.id.phone_edittext);
        emailedit = findViewById(R.id.email_edittext);
        signupbutton = findViewById(R.id.SignupButton);
        phonecheck = findViewById(R.id.phone_check);
        emailcheck = findViewById(R.id.email_check);
        clickhandler();
    }



    protected void clickhandler()
    {
        phonecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phonecheck.isChecked())
                { phoneedit.setVisibility(View.VISIBLE);}
                else
                { phoneedit.setVisibility(View.INVISIBLE); }
            }
        });

        emailcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailcheck.isChecked())
                { emailedit.setVisibility(View.VISIBLE);  }
                else
                { emailedit.setVisibility(View.INVISIBLE); }
            }
        });

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!phonecheck.isEnabled() && !emailedit.isEnabled())
                {
                    alert("Failed","Atleast provide email or phone number");
                    return;
                }
                Password = passedit.getText().toString();
                confpass = confpassedit.getText().toString();
                Phone_No = phoneedit.getText().toString();
                Email = emailedit.getText().toString();
                Username = usernameedit.getText().toString();
                Username = Username.toLowerCase();
                Password = Password.toLowerCase();
                confpass = confpass.toLowerCase();
                Email = Email.toLowerCase();

                if(!Password.equals(confpass))
                { alert("Mismatch","Password and confirm password doesnot match");
                 return;}
                if(Password.length()< 6)
                { alert("Short","Password length should be greater than 6");
                    return;}

                if(!Patterns.PHONE.matcher(Phone_No).matches()&& Phone_No.length()>0)
                {
                    alert("Mismatch","Phone number not valid");
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()&& Email.length()>0)
                {
                    alert("Mismatch","Email not valid");
                    return;
                }
                if(Phone_No.length()==0 && Email.length()==0)
                {
                    alert("Enter Data","Enter atleast phone_no or email");
                    return;
                }


                //DATABSE
                if(Phone_No.length()==0 )
                {

                    Boolean check = userdata.insertData(Username,Password,null,Email,0,"0");
                    if(check)
                    {
                        alert("Accepted","Data accepted go to login screen");
                        Intent intent = new Intent(Signup.this,Home.class);
                        startActivity(intent);
                        Signup.this.finish();
                    }
                    else
                    {
                        alert("Error","Error Occured(phoneno or email is registed)");
                    }

                }
                else if(Email.length()==0 )
                {
                   Boolean check= userdata.insertData(Username,Password,Phone_No,null,0,"0");
                    if(check)
                    {
                        alert("Accepted","Data accepted go to login screen");
                        Intent intent = new Intent(Signup.this,Home.class);
                        startActivity(intent);
                        Signup.this.finish();
                    }
                    else
                    {
                        alert("Error","Error Occured(phoneno or email is registed)");
                    }
                }
                else
                {
                   Boolean check = userdata.insertData(Username,Password,Phone_No,Email,0,"0");
                    if(check)
                    {

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    AlertDialog alertDialog = (new AlertDialog.Builder(Signup.this)).setTitle("Accepted").setMessage("Data accepted go to login screen for login")
                                                            .setPositiveButton("OK",
                                                                    new AlertDialog.OnClickListener()
                                                                    {

                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            Intent intent = new Intent(Signup.this,Home.class);
                                                                            Bundle bundle = new Bundle();
                                                                            bundle.putString("user","LOGIN");
                                                                            intent.putExtra("login",bundle);
                                                                            startActivity(intent);
                                                                            Signup.this.finish();
                                                                        }
                                                                    }).create();
                                                    alertDialog.show();

                                                }
                                            }
                                , 500);


                    }
                    else
                    {
                        alert("Error","Error Occured(phoneno or email is registed)");
                    }
                }

            }
        });
    }

    public final void alert(String title,String message) {
        AlertDialog alertDialog = (new AlertDialog.Builder((Context)this)).setTitle(title).setMessage(message)
                .setPositiveButton("OK", null).create();
        alertDialog.show();
    }


}
