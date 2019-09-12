package com.mohamed.ezz.nearestmasjids.services;

import android.os.AsyncTask;
import android.util.Log;

import com.mohamed.ezz.nearestmasjids.database.AppDatabase;
import com.mohamed.ezz.nearestmasjids.models.Masjid;
import com.mohamed.ezz.nearestmasjids.models.MyResult;

import java.io.IOException;
import java.util.List;

import androidx.annotation.Nullable;
import retrofit2.Call;


public class ApiService {
    private static final String TAG = ApiService.class.getSimpleName();

    public static String fetchDataFromServer(AppDatabase db, double latitude, double longitude, long radius) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MyResult> result = apiInterface.getData(latitude, longitude, radius);
        try {
            MyResult body = result.clone().execute().body();
            if (body != null && body.getMasjidList() != null) {
                cacheToDataBase(db, body.getMasjidList());
                return "جارى الحفظ";
            } else {
                Log.d(TAG, "error_no_updates");
                return "لا توجد مساجد قريبة";
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to Connect: " + e.getMessage());
            return "Failed to Connect: " + e.getMessage();
        }
    }

    private static void cacheToDataBase(AppDatabase db, List<Masjid> masjidList) {
        if (db != null) {
            new DeleteOldInsertNew(db, masjidList).execute();
        }
    }

    public static class DeleteOldInsertNew extends AsyncTask<Void, Void, Void> {
        AppDatabase db;
        List<Masjid> masjidList;

        public DeleteOldInsertNew(AppDatabase db, List<Masjid> masjidList) {
            this.db = db;
            this.masjidList = masjidList;
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            db.masjidDao().deleteAll();
            for (Masjid masjid : masjidList) {
                db.masjidDao().insertMasjid(masjid);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
