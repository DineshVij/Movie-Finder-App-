package com.creativeDApps.movieFinder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Dinesh on 5/4/2016.
 */public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = "NetworkChangeReceiver";
    private boolean isConnected = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(LOG_TAG, "Received notification about network status");
        isNetworkAvailable(context);
        if(isConnected==true){

            Intent intent1=new Intent(context.getApplicationContext(),MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent1);
         }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        if (!isConnected) {
                            Log.v(LOG_TAG, "Now you are connected to Internet!");
         //                   Toast.makeText(context, "Internet available via Broadcast receiver", Toast.LENGTH_SHORT).show();
                            isConnected = true;
                            // do your processing here ---
                            // if you need to post any data to the server or get
                            // status
                            // update from the server
                        }
                        return true;
                    }
                }
            }
        }
        Log.v(LOG_TAG, "You are not connected to Internet!");
   //     Toast.makeText(context, "Internet NOT available via Broadcast receiver", Toast.LENGTH_SHORT).show();
        isConnected = false;
        return false;
    }

}