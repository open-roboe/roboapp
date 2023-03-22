package it.halb.roboapp.dataLayer.localDataSource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BuoyDao {
    @Insert
    void insert(Buoy buoy);

    @Update
    void update(Buoy buoy);

    @Query("DELETE FROM buoy_table")
    void deleteAll();

    @Query("SELECT * FROM buoy_table where regattaName = :regattaName")
    LiveData<List<Buoy>> getBuoy(String regattaName);

    @Query("SELECT * FROM buoy_table LIMIT 50")
    LiveData<List<Buoy>> getAll();
}
