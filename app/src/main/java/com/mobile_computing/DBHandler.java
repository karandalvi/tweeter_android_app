package com.mobile_computing;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

/**
 * Created by Karan J Dalvi on 1/25/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "favorites.db";
    private final static String TABLE_NAME = "favorites";
    private final static String COLUMN_ID = "m_id";
    private final static String COLUMN_TITLE = "m_title";
    private final static String COLUMN_TEXT = "m_text";
    private final static String COLUMN_DATE = "m_date";
    private final static String COLUMN_IMG_URL = "m_img_url";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER , " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_TEXT + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_IMG_URL + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        onCreate(db);
    }

    //add a new entry into favorites.db
    public void addFavorite(Datum item) {
        removeFavorite(item.id());
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, item.id());
        values.put(COLUMN_TITLE, item.title());
        values.put(COLUMN_DATE, item.date());
        values.put(COLUMN_TEXT, item.title());
        values.put(COLUMN_IMG_URL, item.imageUrl());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //remove entry from favorites.db
    public void removeFavorite(int id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + String.valueOf(id);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    //method to check if a particular ID is already marked as favorite
    public boolean checkFavorite(int m_id) {

        String query = "SELECT * FROM " + TABLE_NAME + " " +
                "WHERE " + COLUMN_ID + " = " + String.valueOf(m_id);
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        if (c.getCount() > 0)
        {
            db.close();
            return true;
        }
        else
        {
            db.close();
            return false;
        }
    }

    //print the list of IDs marked as favorite
    public String dbToString() {
        String dbString = "";
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1;";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast())
        {
            if (c.getString(c.getColumnIndex("m_id")) != null)
            {
                dbString += c.getString(c.getColumnIndex("m_id"));
                dbString += "\n";
            }
        }
        db.close();
        return dbString;
    }

}
