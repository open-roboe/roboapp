package it.halb.roboapp.dataLayer.localDataSource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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

    @Delete
    void delete(Buoy buoy);

    @Query("SELECT * FROM buoy_table where regattaName = :regattaName")
    LiveData<List<Buoy>> getBouy(String regattaName);
}
