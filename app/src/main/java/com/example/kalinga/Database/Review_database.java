package com.example.kalinga.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Review_database extends SQLiteOpenHelper {

    public static final String  DATABASE_NAME = "Kalinga.db";
    public static  final String TABLE_NAME = "Reviews";
    public static  final String REVIEW_ID = "Review_id";
    public static  final String USER_ID = "Username";
    public static  final String PRODUCT_ID = "ProductId";
    public static  final String REVIEW_HEAD = "Review_head";
    public static  final String REVIEW_STAR = "Review_star";
    public static  final String FULL_REVIEW = "Full_review";
    Context context;


    public Review_database(Context context){
        super(context, DATABASE_NAME, null, 1);
        this.context =context;
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cmd = "CREATE TABLE " + TABLE_NAME + " (" + REVIEW_ID + " TEXT ," + USER_ID + " TEXT ,"+ PRODUCT_ID +" TEXT ," + REVIEW_HEAD + " TEXT ," + REVIEW_STAR + " INTEGER ,"+ FULL_REVIEW +" TEXT )";
        try {
            db.execSQL(cmd);
        }
        catch (Exception e)
        {
           // Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public Boolean insert_data(String reviewId,String Userid,String Productid,String Reviewhead,int reviewstar,String Fullreview)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REVIEW_ID,reviewId);
        contentValues.put(USER_ID,Userid);
        contentValues.put(PRODUCT_ID,Productid);
        contentValues.put(REVIEW_HEAD,Reviewhead);
        contentValues.put(REVIEW_STAR,reviewstar);
        contentValues.put(FULL_REVIEW,Fullreview);
        String query =  "SELECT * FROM "+ TABLE_NAME +" WHERE " + REVIEW_ID + " = '" + reviewId +"'";
        Cursor cs = db.rawQuery(query,null);
        if(cs.getCount()==0)
        {
            long result = db.insert(TABLE_NAME,null,contentValues);
            return result != -1;
        }
        return false;


    }

    public Cursor frequentProduct()
    {
        String query = "SELECT * FROM "+ TABLE_NAME +" ORDER BY "+ REVIEW_STAR +" DESC ";
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(query,null);
    }

}
