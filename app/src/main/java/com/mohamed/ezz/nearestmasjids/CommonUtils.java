package com.mohamed.ezz.nearestmasjids;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;

public class CommonUtils {

    public static boolean checkConnection(@NonNull Context context)
    {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
