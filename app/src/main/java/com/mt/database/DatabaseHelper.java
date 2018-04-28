package com.mt.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mt.buddy.CurrencyBuddy;
import com.mt.buddy.RateBuddy;
import com.mt.exchangerate.Config;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context , String name , SQLiteDatabase.CursorFactory factory ,
                         int version){
       super(context , name , factory , version);
    }
    public DatabaseHelper (Context context ){
         super(context , Config.DATABASE_NAME , null , 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE currency"
                +"(" +CurrencyBuddy.DataSchema.ENAME + " TEXT PRIMARY KEY,"
                + CurrencyBuddy.DataSchema.CNAME + "TEXT);");

//        db.execSQL("CREATE TABLE rate"
//                + "(" + RateBuddy.RATE.NAME + "TEXT PRIMARY KEY ,"
//                + RateBuddy.RATE.RATE + "FLOAT,"
//                + RateBuddy.RATE.DATE + "TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db , int oldVersion , int newVersion){
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }
}
