package com.mohamed.ezz.nearestmasjids.database;

import com.mohamed.ezz.nearestmasjids.models.Masjid;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MasjidDao {
    @Query("SELECT * FROM masjid ORDER BY id")
    LiveData<List<Masjid>> loadAllMasjids();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMasjid(Masjid masjid);

    @Query("DELETE FROM masjid")
    void deleteAll();

}
