package com.creativeDApps.movieFinder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main2Activity extends AppCompatActivity {

    TextView t5,relDate,actors,runtime_tv,genre_tv,director_tv,writer_tv,plot_tv;
    ImageView ImageView_act2;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView test=(TextView)findViewById(R.id.test);

        ImageView_act2=(ImageView)findViewById(R.id.imageView_act2);

        Intent intent=getIntent();
        String contentValue=intent.getStringExtra("value");
        t5=(TextView)findViewById(R.id.textView5);
        relDate=(TextView)findViewById(R.id.relDate);
        actors=(TextView)findViewById(R.id.actors);
        runtime_tv=(TextView)findViewById(R.id.runTime);
        genre_tv=(TextView)findViewById(R.id.genre);
        director_tv=(TextView)findViewById(R.id.director);
        writer_tv=(TextView)findViewById(R.id.writer);
        plot_tv=(TextView)findViewById(R.id.plot);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);

        if (contentValue!=null){
            JSONObject jsonObject= null;
            try {
                jsonObject = new JSONObject(contentValue);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String poster=jsonObject.getString("Poster").toString();
                new downloadImage(ImageView_act2).execute(poster);
                String Title=jsonObject.getString("Title").toString();
                t5.setText(Title);
                String releasedDate=jsonObject.getString("Released").toString();
                relDate.setText("Relased Date: "+releasedDate);
                String Actors=jsonObject.getString("Actors").toString();
                actors.setText("Starring :"+Actors);
                String rating_value=jsonObject.getString("imdbRating").toString();
                if (!isNumeric(rating_value)) {
                    ratingBar.setVisibility(View.INVISIBLE);

                }else {

                    float value = Float.parseFloat(rating_value) / 2;
                    ratingBar.setRating(value);

                }
                    String runTime_values=jsonObject.getString("Runtime").toString();
                runtime_tv.setText("Runtime: "+runTime_values);
                String genre_value=jsonObject.getString("Genre");
                genre_tv.setText("Genre: "+genre_value);
                String director_value=jsonObject.getString("Director");
                director_tv.setText("Director: "+director_value);
                String writer_value=jsonObject.getString("Writer");
                writer_tv.setText("Writer: "+writer_value);
                String plot_value=jsonObject.getString("Plot");
                plot_tv.setText("Description: "+plot_value);

                }
            catch (JSONException e) {
                e.printStackTrace();
            }
    }
}
    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    private class downloadImage extends AsyncTask<String,Void,Bitmap> {
        ImageView mpBitmap;
        public downloadImage(ImageView mpBitmap) {
            this.mpBitmap=mpBitmap;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bp=null;
            BufferedReader bufferedReader = null;
            InputStream inputStream;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // connection.setDoOutput(true);
                // connection.setRequestMethod("GET");
                bp= BitmapFactory.decodeStream(connection.getInputStream());


            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return bp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mpBitmap.setImageBitmap(bitmap);
        }
    }

}
