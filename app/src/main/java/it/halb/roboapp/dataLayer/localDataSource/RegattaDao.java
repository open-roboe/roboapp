package it.halb.roboapp.dataLayer.localDataSource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RegattaDao {
    @Insert
    void insert(Regatta regatta);

    @Update
    void update(Regatta regatta);

    @Delete
    void delete(Regatta regatta);

    @Query("SELECT * FROM regatta_table LIMIT 50")
    LiveData<List<Regatta>> getAllRegattas();

    @Query("SELECT * FROM regatta_table WHERE name = :name")
    LiveData<Regatta> getRegatta(String name);

    //@Query("SELECT * FROM regatta_table WHERE name = (SELECT runningRegattaName FROM running_status_table LIMIT 1)")
    @Query("SELECT r.* FROM regatta_table r, running_status_table rs WHERE r.name = rs.runningRegattaName")
    LiveData<Regatta> getRunningRegatta();

}
