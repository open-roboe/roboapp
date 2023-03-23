package it.halb.roboapp.dataLayer.localDataSource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BoatDao {

    @Insert
    void insert(Boat boat);

    @Update
    void update(Boat boat);

    @Query("DELETE FROM boat_table")
    void deleteAll();

    @Query("SELECT r.* FROM boat_table r, running_status_table rs WHERE r.regattaName = rs.runningRegattaName")
    LiveData<List<Boat>> getRunningBoats();

    @Query("SELECT * FROM boat_table LIMIT 50")
    LiveData<List<Boat>> getAll();
}
