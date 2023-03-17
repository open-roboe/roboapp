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
    void delete();

    @Query("SELECT * FROM Roboa_table LIMIT 50")
    LiveData<List<Roboa>> getAllRoboe();


}
