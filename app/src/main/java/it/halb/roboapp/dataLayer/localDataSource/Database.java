package it.halb.roboapp.dataLayer.localDataSource;

import static it.halb.roboapp.util.Constants.ROBOAPP_DATABASE_NAME;
import static it.halb.roboapp.util.Constants.ROBOAPP_DATABASE_VERSION;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Account.class, Regatta.class, Buoy.class, Boat.class, Roboa.class, RunningStatus.class}, version= ROBOAPP_DATABASE_VERSION, exportSchema = false)
public abstract class Database extends RoomDatabase {
    private static Database instance;
    public abstract AccountDao accountDao();
    public abstract RegattaDao regattaDao();
    public abstract BuoyDao buoyDao();
    public abstract BoatDao boatDao();
    public abstract RoboaDao roboaDao();
    public abstract RunningStatusDao RunningStatusDao();
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

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
