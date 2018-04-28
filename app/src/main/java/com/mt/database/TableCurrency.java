package com.mt.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class TableCurrency {
    private Context mContext;
    public TableCurrency(Context context){
        mContext = context;
    }

    SQLiteDatabase sd = new DatabaseHelper(mContext).getWritableDatabase();

    public void add(){
       // sd.execSQL();
    }
}
