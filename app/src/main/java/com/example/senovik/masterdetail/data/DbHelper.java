package com.example.senovik.masterdetail.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.senovik.masterdetail.data.DbContract.ContactEntry;


public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "stores.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_STORE_TABLE = "CREATE TABLE " + ContactEntry.TABLE_NAME + " (" +
                ContactEntry._ID + " INTEGER PRIMARY KEY, " +
                ContactEntry.COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                ContactEntry.COLUMN_SECOND_NAME + " TEXT NOT NULL, " +
                ContactEntry.COLUMN_PHONE + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_STORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContactEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
