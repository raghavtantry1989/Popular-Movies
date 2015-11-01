package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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

public class MainActivity extends AppCompatActivity {

    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        GridView grid = (GridView) findViewById(R.id.gridViewMovies);
        //movieAdapter = new MovieAdapter(MainActivity.this, Arrays.asList(Movie.movies));
        movieAdapter = new MovieAdapter(MainActivity.this, new ArrayList<Movie>());
        grid.setAdapter(movieAdapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movieAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("title", movie.getTitle());
                intent.putExtra("poster_url", movie.getPoster());
                intent.putExtra("overview", movie.getOverview());
                intent.putExtra("release_date", movie.getReleaseDate());
                intent.putExtra("vote_avg", movie.getVoteAverage());
                startActivity(intent);
            }
        });

        DownloadMovieData getData = new DownloadMovieData();
        getData.execute("");
    }

    public class DownloadMovieData extends AsyncTask<String, Void, Movie[]> {
        private final String API_KEY ="";
        private final String LOG_TAG = DownloadMovieData.class.getSimpleName();

        private Movie[] getMovieDataFromJson(String movieJsonStr) throws JSONException {

            final String MV_DB_LIST = "results";
            final String MV_DB_TITLE = "original_title";
            final String MV_DB_RELEASE_DATE = "release_date";
            final String MV_DB_POSTER = "poster_path";
            final String MV_DB_VOTE_AVG= "vote_average";
            final String MV_DB_OVERVIEW = "overview";

            JSONObject movieDataComplete = new JSONObject(movieJsonStr);
            JSONArray resultsArray = movieDataComplete.getJSONArray(MV_DB_LIST);
            Movie[] results = new Movie[resultsArray.length()];

            for(int i=0; i< resultsArray.length();i++){
                JSONObject currentElement = resultsArray.getJSONObject(i);
                String title = currentElement.getString(MV_DB_TITLE);
                String date = currentElement.getString(MV_DB_RELEASE_DATE);
                String poster = currentElement.getString(MV_DB_POSTER);
                Double vote_avg = currentElement.getDouble(MV_DB_VOTE_AVG);
                String overview = currentElement.getString(MV_DB_OVERVIEW);


                results[i]=new Movie(title,date,poster,vote_avg,overview);
            }

            for (Movie result : results){
                Log.d("Title", result.getTitle());
            }

            return results;
        }

        @Override
        protected Movie[] doInBackground(String... params) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String movieJsonStr = null;

            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            String sort_order = pref.getString("sort_order", "");

            Log.d("Preferences",sort_order);
            String sort_by="";
            String vote_count="";
            if(sort_order=="popularity") {
                sort_by  = "popularity.desc";
                vote_count="0";
            }
            else if(sort_order.equals("ratings")){
                sort_by = "vote_average.desc";
                vote_count="1000";
            }

            try{
                //URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="+API_KEY);

                final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/discover/movie";
                final String SORT_PARAM = "sort_by";
                final String MIN_VOTE_COUNT = "vote_count.gte";
                final String API_KEY_PARAM = "api_key";


                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM,sort_by)
                        .appendQueryParameter(MIN_VOTE_COUNT,vote_count)
                        .appendQueryParameter(API_KEY_PARAM,API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                Log.d(LOG_TAG,"URL"+ url.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while((line = reader.readLine())!= null){
                    buffer.append(line + "\n");
                }

                if(buffer.length() == 0){
                    return null;
                }

                movieJsonStr = buffer.toString();
                Log.d(LOG_TAG,movieJsonStr);
                try {
                    return getMovieDataFromJson(movieJsonStr);
                }
                catch (JSONException e){
                    Log.e(LOG_TAG,"Json Exception "+ e.getMessage());
                }
            }
            catch(IOException e){
                Log.e(LOG_TAG,e.getMessage());
                return null;
            }
            finally {
                if(urlConnection!= null){
                    urlConnection.disconnect();
                }
                if(reader != null){
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG,e.getMessage());
                    }
                }
            }

            return null;

        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            super.onPostExecute(movies);
            if(movies != null){
                movieAdapter.clear();
                movieAdapter.addAll(movies);
            }
            else{
                Log.e(LOG_TAG, "Custom Error");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
