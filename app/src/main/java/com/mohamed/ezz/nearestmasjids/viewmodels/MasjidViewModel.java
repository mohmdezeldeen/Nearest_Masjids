package com.mohamed.ezz.nearestmasjids.viewmodels;

import android.app.Application;
import android.util.Log;

import com.mohamed.ezz.nearestmasjids.database.AppDatabase;
import com.mohamed.ezz.nearestmasjids.models.Masjid;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MasjidViewModel extends AndroidViewModel {
    private static final String TAG = MasjidViewModel.class.getSimpleName();

    private LiveData<List<Masjid>> Masjids;

    public MasjidViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the Masjids from the DataBase");
        Masjids = database.masjidDao().loadAllMasjids();
    }

    public LiveData<List<Masjid>> getMasjids() {
        return Masjids;
    }
}

