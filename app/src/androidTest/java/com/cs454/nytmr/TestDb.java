package com.cs454.nytmr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.cs454.nytmr.data.MovieContract;
import com.cs454.nytmr.data.MovieReviewDbHelper;

import java.util.HashSet;


/**
 * Created by Brian on 6/2/16.
 */
public class TestDb extends AndroidTestCase {
    Context context;
    public static final String LOG_TAG = TestDb.class.getSimpleName();

    void deleteTheDatabase() {
        mContext.deleteDatabase("reviews.db");
    }

    public void setUp() throws Exception {
        //deleteTheDatabase();
        super.setUp();
        context = getContext();
    }


    public void testCreateDb() throws Throwable {
        final HashSet<String> tablenameHashSet = new HashSet<String>();
        tablenameHashSet.add(MovieContract.ReviewEntry.TABLE_NAME);
        deleteTheDatabase();

        SQLiteDatabase db = new MovieReviewDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        db.close();
    }

    public void testInsertQuery() {
        Cursor cursor = null;
        deleteTheDatabase();
        SQLiteDatabase db = new MovieReviewDbHelper(this.mContext).getWritableDatabase();
        db.execSQL("INSERT INTO reviews (_ID, display_title, summary_short, publish_date) VALUES(1, \"Pulp Fiction\", \"Quentin Tarantino Film\", \"01/01/1996\");");

        cursor = db.rawQuery("SELECT display_title FROM reviews", null);
        String title = "";
        if (cursor != null) {
            if (cursor.moveToNext()) {
                title = cursor.getString(0);
            }
        }
        System.out.println("TITLE: " + title);
        assertEquals("Pulp Fiction", title);
        deleteTheDatabase();
        db.close();
    }

    public void testInsertQuery2(){
        Cursor cursor = null;
        deleteTheDatabase();
        MovieReviewDbHelper helper = new MovieReviewDbHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues moviewValues = createMovieReviewValues();
        db.insert(MovieContract.ReviewEntry.TABLE_NAME, null, moviewValues);

        cursor = db.rawQuery("SELECT display_title FROM reviews", null);
        String title = "";
        if (cursor != null) {
            if (cursor.moveToNext()) {
                title = cursor.getString(0);
            }
        }
        System.out.println("TITLE: " + title);
        assertEquals("A Clockwork Orange", title);


        cursor = db.rawQuery("SELECT summary_short FROM reviews", null);
        String sum = "";
        if (cursor != null) {
            if (cursor.moveToNext()) {
                sum = cursor.getString(0);
            }
        }
        assertEquals("Stanley Kubrick Film", sum);
        deleteTheDatabase();
        db.close();
    }

    static ContentValues createMovieReviewValues(){
        ContentValues movieReviewValues = new ContentValues();
        movieReviewValues.put(MovieContract.ReviewEntry._ID, 1);
        movieReviewValues.put(MovieContract.ReviewEntry.COLUMN_DISPLAY_TITLE, "A Clockwork Orange");
        movieReviewValues.put(MovieContract.ReviewEntry.COLUMN_SUMMARY_SHORT, "Stanley Kubrick Film");
        movieReviewValues.put(MovieContract.ReviewEntry.COLUMN_PUBLISH_DATE, "12/19/1971");

        return movieReviewValues;
    }

}
