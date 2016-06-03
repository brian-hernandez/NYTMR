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
        super.setUp();
        context = getContext();
    }

    /*
        This tests initializing and opening/closing database
     */
    public void testCreateDb() throws Throwable {
        SQLiteDatabase db = new MovieReviewDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
        assertEquals(false, db.isOpen());
    }

    /*
        This test uses the ContentValues created with createMovieReviewValues() to check
        if the insert and query result with the same value.
     */
    public void testInsertQuery() {
        Cursor cursor = null;
        deleteTheDatabase();
        MovieReviewDbHelper helper = new MovieReviewDbHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Inserting a row of data into the database
        ContentValues moviewValues = createMovieReviewValues();
        db.insert(MovieContract.ReviewEntry.TABLE_NAME, null, moviewValues);

        cursor = db.rawQuery("SELECT * FROM reviews", null);
        String id;
        String name;
        String summary;
        String pubDate;

        cursor.moveToFirst();
        do {
            id = cursor.getString(0);
            name = cursor.getString(1);
            summary = cursor.getString(2);
            pubDate = cursor.getString(3);
            System.out.println("Test 1 info: " + id + " | " + name + " | " + summary + " | " + pubDate + " ");
        } while (cursor.moveToNext());

        assertEquals("1", id);
        assertEquals("A Clockwork Orange", name);
        assertEquals("Stanley Kubrick Film", summary);
        assertEquals("12/19/1971", pubDate);


//        // Testing the display_title db column
//        cursor = db.rawQuery("SELECT display_title FROM reviews", null);
//        String title = "";
//        if (cursor != null) {
//            if (cursor.moveToNext()) {
//                title = cursor.getString(0);
//            }
//        }
//        System.out.println("TITLE: " + title);
//        // TEST: display_title
//        assertEquals("A Clockwork Orange", title);
//
//        // Testing the summary_short db column
//        cursor = db.rawQuery("SELECT summary_short FROM reviews", null);
//        String summ = "";
//        if (cursor != null) {
//            if (cursor.moveToNext()) {
//                summ = cursor.getString(0);
//            }
//        }
//        // TEST: summary_short
//        assertEquals("Stanley Kubrick Film", summ);
//
//        // Testing the publish_date db column

        deleteTheDatabase();
        db.close();
    }

    /*
        This tests the insert/select queries by executing an SQL Insert query.
     */
    public void testInsertQuery2() {
        Cursor cursor = null;
        deleteTheDatabase();
        SQLiteDatabase db = new MovieReviewDbHelper(this.mContext).getWritableDatabase();
        db.execSQL("INSERT INTO reviews (_ID, display_title, summary_short, publish_date) VALUES(1, \"Pulp Fiction\", \"Quentin Tarantino Film\", \"01/01/1996\");");

        cursor = db.rawQuery("SELECT * FROM reviews", null);
        String id;
        String title;
        String summary;
        String pubDate;

        cursor.moveToFirst();
        do {
            id = cursor.getString(0);
            title = cursor.getString(1);
            summary = cursor.getString(2);
            pubDate = cursor.getString(3);
            System.out.println("Test 2 info: " + id + " | " + title + " | " + summary + " | " + pubDate + " ");
        } while (cursor.moveToNext());

        assertEquals("1", id);
        assertEquals("Pulp Fiction", title);
        assertEquals("Quentin Tarantino Film", summary);
        assertEquals("01/01/1996", pubDate);

        deleteTheDatabase();
        db.close();
    }


    /*
        ContentValues used to test db insert/select queries.
     */

    static ContentValues createMovieReviewValues() {
        ContentValues movieReviewValues = new ContentValues();
        //movieReviewValues.put(MovieContract.ReviewEntry._ID, 1);
        movieReviewValues.put(MovieContract.ReviewEntry.COLUMN_DISPLAY_TITLE, "A Clockwork Orange");
        movieReviewValues.put(MovieContract.ReviewEntry.COLUMN_SUMMARY_SHORT, "Stanley Kubrick Film");
        movieReviewValues.put(MovieContract.ReviewEntry.COLUMN_PUBLISH_DATE, "12/19/1971");

        return movieReviewValues;
    }

}
