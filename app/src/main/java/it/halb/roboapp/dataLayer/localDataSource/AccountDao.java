package it.halb.roboapp.dataLayer.localDataSource;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@androidx.room.Dao
public interface AccountDao {
    @Insert
    void insert(Account account);

    @Update
    void update(Account account);

    @Query("DELETE FROM account_table")
    void delete();

    @Query("SELECT * FROM account_table LIMIT 1")
    LiveData<Account> getAccount();
}
