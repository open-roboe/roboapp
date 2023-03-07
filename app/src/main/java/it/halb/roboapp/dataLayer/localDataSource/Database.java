package it.halb.roboapp.dataLayer.localDataSource;

import static it.halb.roboapp.util.Constants.ROBOAPP_DATABASE_NAME;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Account.class, Regatta.class}, version=1)
public abstract class Database extends RoomDatabase {
    private static Database instance;
    public abstract AccountDao accountDao();
    public abstract RegattaDao regattaDao();

    @NonNull
    public static synchronized Database getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, ROBOAPP_DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
