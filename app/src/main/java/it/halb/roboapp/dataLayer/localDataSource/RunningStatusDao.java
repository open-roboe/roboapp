package it.halb.roboapp.dataLayer.localDataSource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import it.halb.roboapp.R;

@Dao
public interface RunningStatusDao {
    @Insert
    void insert(RunningStatus runningStatus);

    @Update
    void update(RunningStatus runningStatus);


    @Query("UPDATE running_status_table SET lat=:lat, lon=:lon, lastUpdate=:lastUpdate")
    void updateLocation(double lat, double lon, long lastUpdate);

    @Query("UPDATE running_status_table SET error=:error")
    void updateError(String error);

    @Query("DELETE FROM running_status_table")
    void delete();


    @Query("SELECT * FROM running_status_table LIMIT 1")
    LiveData<RunningStatus> getRunningStatus();
}
