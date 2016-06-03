package com.cs454.nytmr.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cs454.nytmr.data.MovieContract.ReviewEntry;

/**
 * Created by Brian on 6/2/16.
 */


/*
    Manages a local database for movie review data.
 */
public class MovieReviewDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "reviews.db";

    public MovieReviewDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a table to hold film information. The information consists of
        // title, short summary, publish date
        final String SQL_CREATE_REVIEWS_TABLE = "CREATE TABLE " + ReviewEntry.TABLE_NAME + " (" +
                ReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ReviewEntry.COLUMN_DISPLAY_TITLE + " TEXT NOT NULL, " +
                ReviewEntry.COLUMN_SUMMARY_SHORT + " TEXT NOT NULL, " +
                ReviewEntry.COLUMN_PUBLISH_DATE + " TEXT NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_REVIEWS_TABLE);
    }

    /*
        Drops reviews.db and calls onCreate()
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ReviewEntry.TABLE_NAME);
        onCreate(db);
    }
}
