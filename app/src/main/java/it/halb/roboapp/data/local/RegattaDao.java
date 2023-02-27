package it.halb.roboapp.data.local;

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
}
