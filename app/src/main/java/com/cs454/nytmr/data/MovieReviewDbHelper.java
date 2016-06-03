package com.cs454.nytmr.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cs454.nytmr.data.MovieContract.ReviewEntry;

/**
 * Created by Brian on 6/2/16.
 */
public class MovieReviewDbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "reviews.db";

    public MovieReviewDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_REVIEWS_TABLE = "CREATE TABLE " + ReviewEntry.TABLE_NAME + " (" +
                ReviewEntry._ID + " INT PRIMARY KEY NOT NULL, " +
                ReviewEntry.COLUMN_DISPLAY_TITLE + " TEXT NOT NULL, " +
                ReviewEntry.COLUMN_SUMMARY_SHORT + " TEXT NOT NULL, " +
                ReviewEntry.COLUMN_PUBLISH_DATE + " TEXT NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_REVIEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ReviewEntry.TABLE_NAME);
        onCreate(db);
    }
}
