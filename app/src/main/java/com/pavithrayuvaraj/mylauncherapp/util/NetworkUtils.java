package com.pavithrayuvaraj.mylauncherapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NetworkUtils {
    /**
     * Method returns whether the device is connected to internet or not
     * @param context       App context
     * @return              true, if network is available
     *                      false, otherwise
     */
    public static boolean isInternetConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm != null) {
            return cm.getActiveNetwork() != null && cm.getNetworkCapabilities(cm.getActiveNetwork()) != null;
        }
        return false;
    }
}
