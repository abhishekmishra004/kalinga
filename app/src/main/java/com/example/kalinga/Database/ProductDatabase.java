package com.example.kalinga.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDatabase extends SQLiteOpenHelper {

    public static final String  DATABASE_NAME = "Kalinga.db";
    public static  final String TABLE_NAME = "ProductDetails";
    public static  final String PRODUCT_ID = "ProductId";
    public static final String PRODUCT_IMAGE = "Product_image";
    public static  final String PRODUCT_NAME = "Product_name";
    public static  final String PRODUCT_CATEGORY = "ProductCategory";
    public static final String PRODUCT_DESCRIPTION = "Product_description";
    public static  final String PRODUCT_PRICE = "Product_price";
    public static final String SELLER_ID = "Seller_id";
    public static  final String ADDED_DATE = "Added_Date";
    public static  final String COLOR = "Color";
    public static  final String WEIGHT = "Weight";
    public static  final String WIDTH = "Width";
    public static  final String HEIGHT = "Height";
    public static  final String TOTAL_BUYS = "Total_buys";

    Context gcontext;

    public ProductDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
        gcontext=context;
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
       String cmd = "CREATE TABLE " + TABLE_NAME + " (" + PRODUCT_ID + " TEXT ," + PRODUCT_NAME + " TEXT ,"+ PRODUCT_IMAGE +" TEXT ," + PRODUCT_CATEGORY + " TEXT ," + PRODUCT_PRICE + " INTEGER ,"+ PRODUCT_DESCRIPTION +" TEXT ,"+ SELLER_ID +" TEXT ,"+ ADDED_DATE +" DATE ,"+
               COLOR+" TEXT ,"+WEIGHT+" DECIMAL( 12, 6 ) , "+WIDTH+" INTEGER,"+HEIGHT+" INTEGER, "+ TOTAL_BUYS +" INTEGER )";
       db.execSQL(cmd);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public Boolean insert_data(String productId,String productname,String productimage,String productcategory,int productprice,String Productdescription,String sellerid,String date,
                            String color, Double Weight,int width, int height,int totalbuys)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_ID,productId);
        contentValues.put(PRODUCT_NAME,productname);
        contentValues.put(PRODUCT_IMAGE,productimage);
        contentValues.put(PRODUCT_CATEGORY,productcategory);
        contentValues.put(PRODUCT_PRICE,productprice);
        contentValues.put(PRODUCT_DESCRIPTION,Productdescription);
        contentValues.put(SELLER_ID,sellerid);
        contentValues.put(ADDED_DATE,date);
        contentValues.put(COLOR,color);
        contentValues.put(WEIGHT,Weight);
        contentValues.put(WIDTH,width);
        contentValues.put(HEIGHT,height);
        contentValues.put(TOTAL_BUYS,totalbuys);
        String query =  "SELECT * FROM "+ TABLE_NAME +" WHERE " + PRODUCT_ID + " = '" + productId +"'";
        Cursor cs = db.rawQuery(query,null);
        if(cs.getCount()==0)
        {
            long result = db.insertWithOnConflict(TABLE_NAME,null,contentValues,SQLiteDatabase.CONFLICT_REPLACE);
            return result != -1;
        }
        return false;
    }

    public Cursor all_product()
    {
        String query = "SELECT * FROM "+ TABLE_NAME ;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(query,null);
    }

    public Cursor find_product(String id)
    {
        String query =  "SELECT * FROM "+ TABLE_NAME +" WHERE " + PRODUCT_ID + " = '" + id +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(query,null);
    }

    public Cursor recently_added()
    {
        String query = "SELECT * FROM "+ TABLE_NAME +" ORDER BY "+ ADDED_DATE +" DESC ";
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(query,null);
    }

    public Cursor frequent_buys()
    {
        String query = "SELECT * FROM "+ TABLE_NAME +" ORDER BY "+ TOTAL_BUYS +" DESC ";
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(query,null);
    }

}
