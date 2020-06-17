package com.example.kalinga.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AddressDBhelper extends SQLiteOpenHelper {

    public static final String  DATABASE_NAME = "Kalinga.db";
    public static final String TABLE_NAME ="Address_table";
    public  static final String ADDRESS_ID ="Address_id";
    public  static final String USER_ID ="User_id";
    public  static final String HOUSE_NO ="House_no";
    public  static final String STREET ="Street";
    public  static final String CITY ="city";
    public  static final String DISTRICT ="District";
    public  static final String STATE ="State";
    public  static final String PIN ="pin";
    public  static final String LAND_MARK ="landmark";

    Context context;

    public AddressDBhelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        String query = "CREATE TABLE "+TABLE_NAME + "("+ADDRESS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+USER_ID+" TEXT ,"+ HOUSE_NO +" TEXT, "+ STREET +" TEXT , "+ CITY +"TEXT ,"+DISTRICT+" TEXT, "+STATE+" TEXT, "+PIN+" TEXT, "+LAND_MARK+" TEXT )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public boolean insert_values(String userid,String houseno,String street, String city, String district, String state,String pin,String landmark)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID,userid);
        contentValues.put(HOUSE_NO,houseno);
        contentValues.put(STREET,street);
        contentValues.put(CITY,city);
        contentValues.put(DISTRICT,district);
        contentValues.put(STATE,state);
        contentValues.put(PIN,pin);
        contentValues.put(LAND_MARK,landmark);
        long res = db.insert(TABLE_NAME,null,contentValues);
        if(res==-1)
             return false;
        else
            return true;
    }
    public Cursor ViewAddress(String userid)
    {
        String query = "SELECT * FROM "+ TABLE_NAME +" WHERE "+ USER_ID +" = '" + userid +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(query,null);
    }
}
