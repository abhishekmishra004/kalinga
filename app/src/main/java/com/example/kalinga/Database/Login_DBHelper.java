package com.example.kalinga.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Login_DBHelper extends SQLiteOpenHelper {

    public static final String  DATABASE_NAME = "Kalinga.db";
    public static final String  TABLE_NAME = "User_Details";
    public  static final String CART_TABLE = "User_Cart";
    public static final String  WISH_TABLE = "User_wish";
    public static final String  USER_NAME = "Username";
    public static final String  PASS = "Password";
    public static final String  PHONE = "Phone_No";
    public static final String  EMAIL = "Email";
    public static final String  VERIFIED = "Verified";
    public static final String  DEVICEID = "DeviceID";
    public static final String  PRODUCT_ID = "Product_id";
    public static String cmd;
    Context gcontext;

    public Login_DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
        gcontext=context;
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cmd = "CREATE TABLE " + TABLE_NAME + " (" + USER_NAME + " TEXT ," + PASS + " TEXT ," + PHONE + " INTEGER ," + EMAIL + " TEXT ,"+ VERIFIED +" INTEGER ,"+ DEVICEID +" TEXT NOT NULL )";
        String query = "CREATE TABLE "+ CART_TABLE + " ( "+ USER_NAME + " TEXT ," + PRODUCT_ID + " TEXT )";
        String newQuery = "CREATE TABLE "+ WISH_TABLE + " ( "+ USER_NAME + " TEXT ," + PRODUCT_ID + " TEXT )";
        try {
            db.execSQL(cmd);
            db.execSQL(query);
            db.execSQL(newQuery);
        }
        catch (Exception e)
        {
          //  Toast.makeText(gcontext,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String Username,String Password,String Phone_No,String Email,Integer verified, String DeviceID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME,Username);
        contentValues.put(PASS,Password);
        contentValues.put(PHONE,Phone_No);
        contentValues.put(EMAIL,Email);
        contentValues.put(VERIFIED,verified);
        contentValues.put(DEVICEID,DeviceID);

        String query =  "SELECT * FROM "+ TABLE_NAME +" WHERE " + USER_NAME + " = '" + Username +"'";
        Cursor cs = db.rawQuery(query,null);
        if(cs.getCount()==0)
        {
            long result = db.insertWithOnConflict(TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
            return result != -1;
        }
        return false;

    }


    public Cursor checkdata(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+USER_NAME +" = '"+username+"'";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    public void updatelogin(String username, String Phoneid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "UPDATE "+TABLE_NAME+" SET "+DEVICEID+" = "+ Phoneid +" WHERE "+USER_NAME+" = '"+username+"'";
        db.execSQL(Query);
    }

    public void updatelogout(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "UPDATE "+TABLE_NAME+" SET "+DEVICEID+" = ' ' WHERE "+USER_NAME+" = '"+username+"'";
        db.execSQL(Query);
    }

    public Cursor checklogin(String Phoneid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+ DEVICEID +" = '"+Phoneid+"'";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    public Cursor checkuserid(String userid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+ USER_NAME +" = '"+userid+"'";
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    public boolean Add_cart(String username,String product_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME,username);
        contentValues.put(PRODUCT_ID,product_id);
        long result = db.insert(CART_TABLE,null,contentValues);
        return result != -1;
    }

    public Cursor View_cart(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+CART_TABLE+" WHERE "+ USER_NAME +" = '"+username+"'";
        Cursor res = db.rawQuery(query, null);
        return res;
    }

    public boolean Add_wish(String username,String product_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME,username);
        contentValues.put(PRODUCT_ID,product_id);
        long result = db.insert(WISH_TABLE,null,contentValues);
        return result != -1;
    }

    public Cursor View_wish(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+WISH_TABLE+" WHERE "+ USER_NAME +" = '"+username+"'";
        Cursor res = db.rawQuery(query, null);
        return res;
    }
}
