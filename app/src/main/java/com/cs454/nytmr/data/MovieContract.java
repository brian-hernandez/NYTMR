package com.cs454.nytmr.data;

import android.provider.BaseColumns;

/**
 * Created by Brian on 6/2/16.
 */
public class MovieContract {

    /*
        Inner class that defines the contents of the reviews table
        Implementing BaseColumns takes care of _ID
     */
    public static final class ReviewEntry implements BaseColumns{

        public static final String TABLE_NAME = "reviews";
        public static final String COLUMN_DISPLAY_TITLE = "display_title";
        public static final String COLUMN_SUMMARY_SHORT = "summary_short";
        public static final String COLUMN_PUBLISH_DATE = "publish_date";

    }
}
