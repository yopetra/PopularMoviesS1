package com.example.android.popularmoviess1v02.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class OpenMovieJsonUtils {
    public static JSONArray getSimpleMovieStringFromJson(String movieJsonString) throws JSONException {

        final String OMJ_MESSAGE_CODE = "cod";
        final String OMJ_LIST = "results";
        final String OMJ_IMAGE_POSTER = "poster_path";
        final String OMJ_MOVIE_ID = "id";

        JSONArray jsonMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonString);

        JSONArray movieArray = movieJson.getJSONArray(OMJ_LIST);

        jsonMovieData = new JSONArray();

        for(int i = 0; i < movieArray.length(); i++){

            JSONObject currentMovieItem =  movieArray.getJSONObject(i);
            String posterPicture = currentMovieItem.getString(OMJ_IMAGE_POSTER);
            Integer posterId = currentMovieItem.getInt(OMJ_MOVIE_ID);

            JSONObject jsonMovieItem = new JSONObject();
            jsonMovieItem.put("posterPic", posterPicture);
            jsonMovieItem.put("posterId", posterId);

            jsonMovieData.put(jsonMovieItem);
        }

        return jsonMovieData;
    }
}
