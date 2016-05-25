# :black_large_square: NYTMR - NY Times Movie Reviews :black_large_square:

This is a simple application that makes use of the [NY Times Movie Review API](https://developer.nytimes.com).


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


**Tasks**

- [X] Build a new Android Studio Project with a main activity with a fragment. In the main activity's fragment put a listivew in its xml layout. 

- [X] Get an api key from the api above, and create a url to fetch 20 movie reviews. Put this url into a browser to view the returned JSON. Take the JSON to a JSON formatter on the web (Google it) and place the formatted JSON into a text file. Submit that here, separate from your Android Studio project. 

- [X] Create an AsyncTask to handle a network call to the api. The type parameters should be <Void, Void, String[]>. On the background thread, make the api call and retrieve the JSON. Log both the url used and the JSON returned.

- [X] Using org.json package unpack the JSON to get an List of movie titles. Display these in your listiview.

- [X] Attach an item click listener to the listview. When it is clicked, it should show a toast with the text in the list item.

- [X] Set up a menu item to refresh this list. When it is clicked, it should create an instance of the AsyncTask you made earlier and refresh the data. 
