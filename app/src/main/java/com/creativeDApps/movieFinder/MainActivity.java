package com.creativeDApps.movieFinder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button b1;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16;
    String content, content2, content3, content4, content5, content6, content7, content8;
    ImageView iv1,iv2,iv3,iv4,iv5,iv6,iv7,iv8;
    android.widget.SearchView searchView;
    String searchContent,searchContent2;
    SqlController sqlController;

    String serverUrl = "http://www.omdbapi.com/?";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        t1 = (TextView) findViewById(R.id.movieName1);
        t2 = (TextView) findViewById(R.id.actorName1);

        t3 = (TextView) findViewById(R.id.movieName2);
        t4 = (TextView) findViewById(R.id.actorName2);

        t5 = (TextView) findViewById(R.id.movieName3);
        t6 = (TextView) findViewById(R.id.actorName3);

        t7 = (TextView) findViewById(R.id.movieName4);
        t8 = (TextView) findViewById(R.id.actorName4);

        t9 = (TextView) findViewById(R.id.movieName5);
        t10 = (TextView) findViewById(R.id.actorName5);

        t11 = (TextView) findViewById(R.id.movieName6);
        t12 = (TextView) findViewById(R.id.actorName6);

        t13 = (TextView) findViewById(R.id.movieName7);
        t14 = (TextView) findViewById(R.id.actorName7);

        t15 = (TextView) findViewById(R.id.movieName8);
        t16 = (TextView) findViewById(R.id.actorName8);

        iv1 = (ImageView) findViewById(R.id.imageView0);
        iv2 = (ImageView) findViewById(R.id.imageView1);
        iv3 = (ImageView) findViewById(R.id.imageView2);
        iv4 = (ImageView) findViewById(R.id.imageView3);
        iv5 = (ImageView) findViewById(R.id.imageView4);
        iv6 = (ImageView) findViewById(R.id.imageView5);
        iv7 = (ImageView) findViewById(R.id.imageView6);
        iv8 = (ImageView) findViewById(R.id.imageView7);
        //Click Listeners
        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        t3.setOnClickListener(this);
        t4.setOnClickListener(this);
        t5.setOnClickListener(this);
        t6.setOnClickListener(this);
        t7.setOnClickListener(this);
        t8.setOnClickListener(this);
        t9.setOnClickListener(this);
        t10.setOnClickListener(this);
        t11.setOnClickListener(this);
        t12.setOnClickListener(this);
        t13.setOnClickListener(this);
        t14.setOnClickListener(this);
        t15.setOnClickListener(this);
        t16.setOnClickListener(this);

        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);
        iv6.setOnClickListener(this);
        iv7.setOnClickListener(this);
        iv8.setOnClickListener(this);

        if (!haveNetworkConnection()){
            Toast.makeText(this,"Network Not Available",Toast.LENGTH_LONG).show();

        }
        else {
            // serverArray= new String[]{serverUrl, serverUrl2};
            if (content != null) content = null;
            if (content2 != null) content2 = null;
            if (content3 != null) content3 = null;
            if (content4 != null) content4 = null;
            if (content5 != null) content5 = null;
            if (content6 != null) content6 = null;
            if (content7 != null) content7 = null;
            if (content8 != null) content8 = null;


            searchView = (android.widget.SearchView) findViewById(R.id.searchView);
            searchView.setQueryHint("Search the Movie");


            searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // TODO Auto-generated method stub

                }
            });


            //*** setOnQueryTextListener ***
            searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    // TODO Auto-generated method stub
                    if (query != null) {
                        //      "t=captain&y=2016&plot=full&r=json"
                        String value = "s=" + query + "&plot=full&r=json";
                        if (searchContent != null) searchContent = null;
                        URI uri_search = null;
                        try {
                            uri_search = new URI(value.replace(" ", "+").replace(":", "%3A"));
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        //  Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG).show();
                        new backgroundSearchTask().execute(serverUrl + uri_search);

                        //  Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG).show();
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // TODO Auto-generated method stub

//                Toast.makeText(getBaseContext(), newText,
                    //              Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            new backgroundTask().execute(serverUrl);

           }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(
          //    new NetworkChangeReceiver(),
           // new IntentFilter(
             //    ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView0:
            case R.id.actorName1:
            case R.id.movieName1:
                if (content!=null) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("value",content);
                startActivity(intent);
        }
                else Toast.makeText(getApplicationContext(),"Issue in fetching info",Toast.LENGTH_LONG).show();
        break;
            case R.id.imageView1:
            case R.id.actorName2:
            case R.id.movieName2:
                if (content2!=null) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("value",content2);
                    startActivity(intent);
                }
                else Toast.makeText(getApplicationContext(),"Issue in fetching info",Toast.LENGTH_LONG).show();
                break;
            case R.id.imageView2:
            case R.id.actorName3:
            case R.id.movieName3:
                if (content3!=null) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("value",content3);
                    startActivity(intent);
                }
                else Toast.makeText(getApplicationContext(),"Issue in fetching info",Toast.LENGTH_LONG).show();
                break;
            case R.id.imageView3:
            case R.id.actorName4:
            case R.id.movieName4:
                if (content4!=null) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("value",content4);
                    startActivity(intent);
                }
                else Toast.makeText(getApplicationContext(),"Issue in fetching info",Toast.LENGTH_LONG).show();
                break;
            case R.id.imageView4:
            case R.id.actorName5:
            case R.id.movieName5:
                if (content5!=null) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("value",content5);
                    startActivity(intent);
                }
                else Toast.makeText(getApplicationContext(),"Issue in fetching info",Toast.LENGTH_LONG).show();
                break;
            case R.id.imageView5:
            case R.id.actorName6:
            case R.id.movieName6:
                if (content6!=null) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("value",content6);
                    startActivity(intent);
                }
                else Toast.makeText(getApplicationContext(),"Issue in fetching info",Toast.LENGTH_LONG).show();
                break;
            case R.id.imageView6:
            case R.id.actorName7:
            case R.id.movieName7:
                if (content7!=null) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("value",content7);
                    startActivity(intent);
                }
                else Toast.makeText(getApplicationContext(),"Issue in fetching info",Toast.LENGTH_LONG).show();
                break;
            case R.id.imageView7:
            case R.id.actorName8:
            case R.id.movieName8:
                if (content8!=null) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("value",content8);
                    startActivity(intent);
                }
                else Toast.makeText(getApplicationContext(),"Issue in fetching info",Toast.LENGTH_LONG).show();
                break;
        }

    }


    private class backgroundTask extends AsyncTask<String, Void, Void> {
        TextView actualJson = (TextView) findViewById(R.id.textView2);
        String Error = null;
        private final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Oh wait kr Kaka");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... urls) {
           String[] values={"t=captain&y=2016&plot=full&r=json","t=X-Men&y=2016&plot=full&r=json",
                   "t=Independence+day&y=2016&plot=full&r=json","t=Batman&y=2016&plot=full&r=json",
                   "t=Tarzan&y=2016&plot=full&r=json","t=The+Jungle+Book&y=2016&plot=full&r=json",
                   "t=1920+London&y=2016&plot=full&r=json","t=Baaghi&y=2016&plot=full&r=json",

           };
            for(int i=0;i<values.length;i++){
            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(urls[0]+values[i]);
            //    if (url.equals(serverUrl)){
              //      urlChecking=0;
            //    }
             //   else if (url.equals(serverUrl2)){
              //      urlChecking=1;
              //  }

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // connection.setDoOutput(true);
                // connection.setRequestMethod("GET");
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);


                    if(i==0){      if (content == null)   content = stringBuilder.toString();}
                    if(i==1){ if (content2 == null) content2 = stringBuilder.toString();}
                    if(i==2){      if (content3 == null)   content3 = stringBuilder.toString();}
                    if(i==3){ if (content4 == null) content4 = stringBuilder.toString();}
                    if(i==4){      if (content5 == null)   content5 = stringBuilder.toString();}
                    if(i==5){ if (content6 == null) content6 = stringBuilder.toString();}
                    if(i==6){      if (content7 == null)   content7 = stringBuilder.toString();}
                    if(i==7){ if (content8 == null) content8 = stringBuilder.toString();}

                }

            } catch (Exception e) {
                Error = e.getMessage();
            } finally {
                try {
                    if(bufferedReader != null)
                        bufferedReader.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            if (Error != null) {
               Toast.makeText(getApplicationContext(),"Error Found,Check your Network",Toast.LENGTH_LONG).show();

              // actualJson.setText("Error :" + Error);
            } else {

                ArrayList<String> arrayList = new ArrayList<String>();

           //     actualJson.setText(content + "\n" +content2+ "\n" +content3+ "\n" +content4+ "\n" +content5+ "\n" +content6+ "\n" +content7
             //           + "\n" +content8);
                try {
                    JSONObject jsonObject=new JSONObject(content);
                    String Tittle=jsonObject.getString("Title").toString();
                    String Actors=jsonObject.getString("Actors").toString();

                    String poster=jsonObject.getString("Poster").toString();
                    if (poster!=null){arrayList.add(poster);}

                    JSONObject jsonObject2=new JSONObject(content2);
                    String Tittle2=jsonObject2.getString("Title").toString();
                    String Actors2=jsonObject2.getString("Actors").toString();

                    String poster2=jsonObject2.getString("Poster").toString();
                    if (poster2!=null){arrayList.add(poster2);}

                    JSONObject jsonObject3=new JSONObject(content3);
                    String Tittle3=jsonObject3.getString("Title").toString();
                    String Actors3=jsonObject3.getString("Actors").toString();
                    String poster3=jsonObject3.getString("Poster").toString();
                    if (poster3!=null){arrayList.add(poster3);}

                    JSONObject jsonObject4=new JSONObject(content4);
                    String Tittle4=jsonObject4.getString("Title").toString();
                    String Actors4=jsonObject4.getString("Actors").toString();
                    String poster4=jsonObject4.getString("Poster").toString();
                    if (poster4!=null){arrayList.add(poster4);}

                    JSONObject jsonObject5=new JSONObject(content5);
                    String Tittle5=jsonObject5.getString("Title").toString();
                    String Actors5=jsonObject5.getString("Actors").toString();

                    String poster5=jsonObject5.getString("Poster").toString();
                    if (poster5!=null){arrayList.add(poster5);}

                    JSONObject jsonObject6=new JSONObject(content6);
                    String Tittle6=jsonObject6.getString("Title").toString();
                    String Actors6=jsonObject6.getString("Actors").toString();
                    String poster6=jsonObject6.getString("Poster").toString();
                    if (poster6!=null){arrayList.add(poster6);}

                    JSONObject jsonObject7=new JSONObject(content7);
                    String Tittle7=jsonObject7.getString("Title").toString();
                    String Actors7=jsonObject7.getString("Actors").toString();
                    String poster7=jsonObject7.getString("Poster").toString();
                    if (poster7!=null){arrayList.add(poster7);}

                    JSONObject jsonObject8=new JSONObject(content8);
                    String poster8=jsonObject8.getString("Poster").toString();
                    String Tittle8=jsonObject8.getString("Title").toString();
                    String Actors8=jsonObject8.getString("Actors").toString();
                    if (poster8!=null){arrayList.add(poster8);}



                    for(int i=0;i<arrayList.size();i++) {
                  //      Toast.makeText(getApplicationContext(),arrayList.get(i).toString(),Toast.LENGTH_LONG).show();
                        if (i==0)new downloadImage(iv1).execute(arrayList.get(i));
                        if (i==1)new downloadImage(iv2).execute(arrayList.get(i));
                        if (i==2)new downloadImage(iv3).execute(arrayList.get(i));
                        if (i==3)new downloadImage(iv4).execute(arrayList.get(i));
                        if (i==4)new downloadImage(iv5).execute(arrayList.get(i));
                        if (i==5)new downloadImage(iv6).execute(arrayList.get(i));
                        if (i==6)new downloadImage(iv7).execute(arrayList.get(i));
                        if (i==7)new downloadImage(iv8).execute(arrayList.get(i));
                    }
                    t1.setText(Tittle.toString());t2.setText(Actors.toString());
                    t3.setText(Tittle2.toString());t4.setText(Actors2.toString());
                    t5.setText(Tittle3.toString());t6.setText(Actors3.toString());
                    t7.setText(Tittle4.toString());t8.setText(Actors4.toString());
                    t9.setText(Tittle5.toString());t10.setText(Actors5.toString());
                    t11.setText(Tittle6.toString());t12.setText(Actors6.toString());
                    t13.setText(Tittle7.toString());t14.setText(Actors7.toString());
                    t15.setText(Tittle8.toString());t16.setText(Actors8.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                }

        }
            }

    private class downloadImage extends AsyncTask<String,Void,Bitmap>{
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

    private class backgroundSearchTask extends AsyncTask<String, Void, Void> {

        TextView parsedJson = (TextView) findViewById(R.id.textView4);
        String Error3 = null;
        private final ProgressDialog progressDialog1 = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            progressDialog1.setMessage("Wait Please");
            progressDialog1.show();
        }

        @Override
        protected Void doInBackground(String... urls) {
                BufferedReader bufferedReader1 = null;
                try {
                    URL url1 = new URL(urls[0]);

                    HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
                    bufferedReader1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
                    String line1 = null;
                    StringBuilder stringBuilder1 = new StringBuilder();
                    while ((line1 = bufferedReader1.readLine()) != null) {
                        stringBuilder1.append(line1);
                        if (searchContent == null)searchContent = stringBuilder1.toString();

                    }

                } catch (Exception e) {
                    Error3 = e.getMessage();
                } finally {
                    try {
                        if(bufferedReader1 != null)
                            bufferedReader1.close();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog1.dismiss();
            ArrayList<String> arrayList=new ArrayList<String>();
            if (Error3 != null) {
                Toast.makeText(getApplicationContext(),"Please enter the correct name",Toast.LENGTH_LONG).show();
            } else {
                sqlController=new SqlController(getApplicationContext());
                sqlController.open();
                Cursor deleteCurs=sqlController.readData();
                if (deleteCurs!=null){
                    sqlController.deleteAll();
                }
                try {
                    JSONObject jsonObject2=new JSONObject(searchContent);
                    {JSONArray jsonMain=jsonObject2.optJSONArray("Search");
                        if(jsonMain!=null)for (int i=0;i<jsonMain.length();i++){
                        JSONObject jsonChild=jsonMain.getJSONObject(i);
                        String title_search=jsonChild.optString("Title");
                        String search_year=jsonChild.optString("Year");


                     sqlController.insertData(title_search,search_year);
                    }
                    sqlController.close();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), (CharSequence) e,Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                final SqlController sqlController2=new SqlController(getApplicationContext());
                sqlController2.open();
                SimpleCursorAdapter adapter = null;

                final Cursor cursor=sqlController2.readData();
                if (cursor.getCount()>0) {
                    String name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME));
                    String[] from = {DBHelper.NAME, DBHelper.YEAR};
                    int[] to = {R.id.name_list,R.id.year_list};
                    adapter = new SimpleCursorAdapter(getApplicationContext(),
                            R.layout.list_view_value, cursor, from, to);
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Movie List");
                builder.setAdapter(adapter,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        TextView test= (TextView) adapter.getItem(which);
                        cursor.close();
                        sqlController2.close();

                        SqlController sqlController3=new SqlController(MainActivity.this);
                        sqlController3.open();
                        Cursor cursor3=sqlController3.readSingleCol(which+1);
                        if(cursor3.getCount() > 0) {


                            String search_year_value = String.valueOf
                                    (cursor3.getString(cursor3.getColumnIndexOrThrow(DBHelper.YEAR)));
                            String search_name_value = String.valueOf
                                    (cursor3.getString(cursor3.getColumnIndexOrThrow(DBHelper.NAME)));
                            String query="t="+search_name_value+"&y="+search_year_value+
                                    "&plot=full&r=json";
                            URI uri = null;
                            try {
                                uri = new URI(query.replace(" ", "+").replace(":", "%3A"));
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }


                            if (searchContent2!=null) searchContent2=null;
                            TextView test=(TextView)findViewById(R.id.test);
                            test.setText(String.valueOf(serverUrl+uri));
                                new searchValue().execute(serverUrl+uri);

                            sqlController3.close();
                            cursor3.close();// String serverUrl_searchValue = "http://www.omdbapi.com/?";
                            //"t=captain&y=2016&plot=full&r=json",
                        }

                    }
                });
                if (cursor.getCount()>0) {
                    builder.show();
                }else Toast.makeText(getApplicationContext(),"Please enter the correct name",Toast.LENGTH_LONG).show();

                //    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
              //  intent.putExtra("value", searchContent);
               // startActivity(intent);

            }
        }




    }
   private class searchValue extends AsyncTask<String, Void, Void> {

        String Error2 = null;
        private final ProgressDialog progressDialog2 = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            progressDialog2.setMessage("Oh wait kr Kaka");
            progressDialog2.show();
        }

        @Override
        protected Void doInBackground(String... urls) {
            BufferedReader bufferedReader2 = null;
            try {
                URL url2 = new URL(urls[0]);

                HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
                bufferedReader2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                String line2 = null;
                StringBuilder stringBuilder2 = new StringBuilder();
                while ((line2 = bufferedReader2.readLine()) != null) {
                    stringBuilder2.append(line2);
                    if (searchContent2 == null)searchContent2 = stringBuilder2.toString();

                }

            } catch (Exception e) {
                Error2 = e.getMessage();
            } finally {
                try {
                    if(bufferedReader2 != null)
                        bufferedReader2.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog2.dismiss();
            if (Error2 != null) {
            Toast.makeText(getApplicationContext(),String.valueOf(Error2),Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(MainActivity.this,searchContent2,Toast.LENGTH_LONG).show();
             Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("value",searchContent2);
                startActivity(intent);


            }
        }




    }

}

