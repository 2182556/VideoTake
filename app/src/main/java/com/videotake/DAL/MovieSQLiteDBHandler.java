package com.videotake.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MovieSQLiteDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MovieDB";


    public MovieSQLiteDBHandler(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
//        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
//                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
//                + KEY_LOC + " TEXT,"
//                + KEY_DESG + " TEXT"+ ")";
//        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
//        // Drop older table if exist
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
//        // Create tables again
//        onCreate(db);
    }

}
