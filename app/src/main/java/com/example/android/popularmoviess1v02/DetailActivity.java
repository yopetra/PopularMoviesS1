package com.example.android.popularmoviess1v02;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviess1v02.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    TextView mTitleTextView;
    ImageView mPosterImage;
    TextView mRatingTextView;
    TextView mReleaseDateTextView;
    TextView mRuntime;
    TextView mOverviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Integer movieId = intent.getExtras().getInt("movieId");

        mTitleTextView = (TextView) findViewById(R.id.tv_originalTitle);
        mPosterImage = (ImageView) findViewById(R.id.iv_poster_image);
        mRatingTextView = (TextView) findViewById(R.id.tv_user_rating);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
        mRuntime = (TextView) findViewById(R.id.tv_runtime);
        mOverviewTextView = (TextView) findViewById(R.id.tv_overview);

        getMovieDetails(movieId);
    }

    private void getMovieDetails(Integer movieId) {
        new FetchMovieDetailsTask().execute(movieId);
    }

    class FetchMovieDetailsTask extends AsyncTask<Integer, Void, String>{
        @Override
        protected String doInBackground(Integer... integers) {
            Integer movieId = integers[0];

            String movieDetailsString = null;
            URL movieDetailsUrl = NetworkUtils.buildMovieDetailsUrl(movieId);
            try {
                movieDetailsString = NetworkUtils.getResponseFromHttpUrl(movieDetailsUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return movieDetailsString;
        }

        @Override
        protected void onPostExecute(String s) {
            String title = null;
            String posterImageLink = null;
            String releaseDate = null;
            Integer runTime = null;
            Integer rating = null;


            String overview = null;

            if( s != null){
                try {
                    JSONObject jsonMovie = new JSONObject(s);
                    title = jsonMovie.getString("original_title");
                    posterImageLink = jsonMovie.getString("poster_path");
                    rating = jsonMovie.getInt("vote_average");
                    overview = jsonMovie.getString("overview");
                    releaseDate = jsonMovie.getString("release_date");
                    runTime = jsonMovie.getInt("runtime");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Log.d(TAG, "Error in postExecute");
            }



            mTitleTextView.setText(title);

            URL picUrl = NetworkUtils.buildPictureUrl(posterImageLink);
            if( picUrl != null ){
                Picasso.get().load(String.valueOf(picUrl)).resize(200,245).into(mPosterImage);
            }

            mRatingTextView.setText(rating + "/10");
            mReleaseDateTextView.setText(releaseDate.substring(0, 4));
            mRuntime.setText(runTime + "min");
            mOverviewTextView.setText(overview);
        }
    }
}
