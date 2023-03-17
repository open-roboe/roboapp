package it.halb.roboapp.dataLayer.localDataSource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface RoboaDao {

    @Insert
    void insert(Roboa roboa);

    @Update
    void update(Roboa roboa);

    @Query("DELETE FROM roboa_table")
    void delete();

    @Query("SELECT * FROM roboa_table LIMIT 50")
    LiveData<List<Roboa>> getAllRoboe();

    @Query("SELECT * FROM roboa_table where id = :id")
    LiveData<Roboa> getBuoy(int id);
}
