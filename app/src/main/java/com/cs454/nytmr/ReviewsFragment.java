package com.cs454.nytmr;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Brian on 5/24/16.
 */
public class ReviewsFragment extends Fragment {

    private ArrayAdapter<String> mMovieAdapter;
    private String[] a;

    public ReviewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Allows fragment to handle menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.reviewsfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchReviewsTask reviewsTask = new FetchReviewsTask();
            reviewsTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] data = {
                "Pulp Fiction",
                "Lord of the Rings",
                "Cloud with a Chance of Meatballs",
                "Trainspotting",
                "2001: A Space Odyssey"
        };

        List<String> movieList = new ArrayList<>(Arrays.asList(data));

        mMovieAdapter = new ArrayAdapter<String>(
                getActivity(),// The current context (this activity)
                R.layout.list_item_reviews, // The name of the layout ID.
                R.id.list_item_reviews_textview, // The ID of the textview to populate.
                movieList);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_reviews);
        listView.setAdapter(mMovieAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //String mov = mMovieAdapter.getItem(position);
                String mov = a[position];
                Toast.makeText(getActivity(), mov, Toast.LENGTH_LONG).show();

            }
        });

        return rootView;
    }

    public class FetchReviewsTask extends AsyncTask<Void, Void, String[]> {

        private final String LOG_TAG = FetchReviewsTask.class.getSimpleName();

        private String[] getMovieDataFromJson(String reviewsJsonStr, int x)
                throws JSONException {

            final String OWM_RESULTS = "results";
            final String OWM_DISPLAY_TITLE = "display_title";
            final String OWM_SUMMARY_SHORT = "summary_short";
            final String OWM_HEADLINE = "headline";

            JSONObject reviewsJson = new JSONObject(reviewsJsonStr);
            JSONArray reviewsArray = reviewsJson.getJSONArray(OWM_RESULTS);

            String[] resultStrs = new String[x];
            a = new String[x];

            for (int i = 0; i < reviewsArray.length(); i++) {
                String title;
                String headline;
                String summary;

                JSONObject movie = reviewsArray.getJSONObject(i);
                title = movie.getString(OWM_DISPLAY_TITLE);
                headline = movie.getString(OWM_HEADLINE);
                summary = movie.getString(OWM_SUMMARY_SHORT);
                //a[i] = headline;
                a[i] = summary;
                resultStrs[i] = "Film " + (i + 1) + ": " + title;
            }

            for (String s : resultStrs) {
                Log.v(LOG_TAG, "Movie entry: " + s);
            }

            return resultStrs;
        }


        @Override
        protected String[] doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String reviewJsonStr = null;

            int offset = 20;

            try {
                System.out.println("I'm inside!");
                final String NY_TIMES_URL =
                        "http://api.nytimes.com/svc/movies/v2/reviews/search.json?";
                final String OFFSET_PARAM = "offset";
                final String API_PARAM = "api-key";

                Uri builtUri = Uri.parse(NY_TIMES_URL).buildUpon()
                        .appendQueryParameter(OFFSET_PARAM, Integer.toString(offset))
                        .appendQueryParameter(API_PARAM, BuildConfig.NYTIMES_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                reviewJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Review JSON string: " + reviewJsonStr);


            } catch (IOException e) {
                Log.e(LOG_TAG, "Error: " + e, e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getMovieDataFromJson(reviewJsonStr, 20);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                mMovieAdapter.clear();
                for (String dayForecastStr : result) {
                    mMovieAdapter.add(dayForecastStr);
                }
            }
        }
    }
}
