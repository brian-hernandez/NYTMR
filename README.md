# :black_large_square: NYTMR - NY Times Movie Reviews (PART 2):black_large_square:

This is a simple application that makes use of the [NY Times Movie Review API](https://developer.nytimes.com).

This is similar to the first part with the addition of SQLite DB Testing.


Written in Java using Android Studio.

**Compile SDK Version:** API 23: Android 6.0 (Marshmallow)

**Build Tools Version:** 23.0.3


**Directory Information:**

This directory contains the full implementation of a basic application for
the Android platform, demonstrating the basic facilities that applications
will use.

The files contained here:


**AndroidManifest.xml**

This XML file describes to the Android platform what your application can do.
It is a required file, and is the mechanism you use to show your application
to the user (in the app launcher's list), handle data types, etc.


**src/***

Under this directory is the Java source for for your application.


**src/main/java/com/cs454/nytmr/MainActivity.java**


This is the implementation of the "activity" feature described in
AndroidManifest.xml.  The path each class implementation is
{src/PACKAGE/CLASS.java}, where PACKAGE comes from the name in the <package>
tag and CLASS comes from the class in the <activity> tag.


**res/***

Under this directory are the resources for your application.


**HW 2 Tasks**

- [X] Implement a MovieContract class to hold column and table names for your movie data. It should have an inner class called "review."

- [X] Create an implementation of SQLiteOpenHelper.

- [X] Override onCreate in the above class to create a table of movie reviews. The table should be named 'reviews' and have these columns: _id, display_title, summary_short, and publish_date. 

- [X] Override onUpgrade in the above class to drop the reviews and call onCreate().

- [X] Subclass AndroidTestCase in your test (which is the Android Test folder) folder. Using a database from your SQLiteOpenHelper class, insert a row of data into your database. Then query the row and check if the values in the query match the values in the ContentValues object used to insert the data. Use an 'assert' statement to test if the query and insert matches. 
