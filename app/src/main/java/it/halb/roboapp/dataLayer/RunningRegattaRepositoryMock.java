package it.halb.roboapp.dataLayer;

import static it.halb.roboapp.util.Constants.OUT_OF_SYNC_AFTER_SECONDS;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.Date;
import java.util.List;
import java.util.Random;

import it.halb.roboapp.dataLayer.localDataSource.Account;
import it.halb.roboapp.dataLayer.localDataSource.AccountDao;
import it.halb.roboapp.dataLayer.localDataSource.Boat;
import it.halb.roboapp.dataLayer.localDataSource.BoatDao;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.BuoyDao;
import it.halb.roboapp.dataLayer.localDataSource.Database;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.dataLayer.localDataSource.RegattaDao;
import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.dataLayer.localDataSource.RoboaDao;
import it.halb.roboapp.dataLayer.localDataSource.RunningStatus;
import it.halb.roboapp.dataLayer.localDataSource.RunningStatusDao;

/**
 * Mock RunningRegatta Repository.
 * This Repository does not connect to the apis but it behaves exactly like it does,
 * Simulating real data such as boats and their movements.
 *
 * It's useful for instrumental tests and development.
 *
 */
public class RunningRegattaRepositoryMock implements RunningRegattaInterface {

    private final AccountDao accountDao;
    private final RegattaDao regattaDao;

    private final BuoyDao buoyDao;
    private final BoatDao boatDao;
    private final RoboaDao roboaDao;
    private final RunningStatusDao runningStatusDao;
    private final LiveData<Account> account;
    private final LiveData<Regatta> regatta;

    private final LiveData<List<Buoy>> buoyList;
    private final LiveData<List<Boat>> boatList;
    private final LiveData<List<Roboa>> roboaList;
    private final LiveData<RunningStatus> runningStatus;

    //TODO: remove, use hilt instead
    public static RunningRegattaInterface instance;


    private RunningRegattaRepositoryMock(Application application){
        Log.d("REPO_SCOPING", "constructor run");
        //init local datasource
        Database database = Database.getInstance(application);
        accountDao = database.accountDao();
        regattaDao = database.regattaDao();
        buoyDao = database.buoyDao();
        boatDao = database.boatDao();
        roboaDao = database.roboaDao();
        runningStatusDao = database.RunningStatusDao();


        account = accountDao.getAccount();
        //all this data can be null, and WILL be null if
        //a running regatta is not set
        runningStatus = runningStatusDao.getRunningStatus();
        regatta = regattaDao.getRunningRegatta();
        buoyList = buoyDao.getRunningBuoys();
        boatList = boatDao.getRunningBoats();
        roboaList = roboaDao.getAll();
    }

    public static synchronized RunningRegattaInterface getInstance(Application application){
        if(instance == null){
            instance = new RunningRegattaRepositoryMock(application);
        }
        return instance;
    }

    public LiveData<Regatta> getRegatta(){
        return regatta;
    }

    public LiveData<List<Buoy>> getBuoys(){
        return buoyList;
    }

    public LiveData<List<Boat>> getBoats(){
        return boatList;
    }

    public LiveData<List<Roboa>> getRoboas(){
        return roboaList;
    }

    public LiveData<RunningStatus> getStatus(){ return runningStatus; }

    public LiveData<String> getError(){
        return Transformations.map(runningStatus, RunningStatus::getError);
    }

    public LiveData<Date> getLastUpdate(){
        return Transformations.map(runningStatus, r ->{
            return new Date(r.getLastUpdate());
        });
    }
    public LiveData<Location> getCurrentLocation(){
        return Transformations.map(runningStatus, r ->{
            Location l = new Location("lat_lon_deserialized");
            Log.d("RUNNINGREGATTA", "transforming lat: "+ r.getLat()+ "lon: "+ r.getLon());
            l.setLatitude(r.getLat());
            l.setLongitude(r.getLon());
            return l;
        });
    }

    public LiveData<Boolean> isInSync(){
        return Transformations.map(runningStatus, r ->{
            long now = System.currentTimeMillis()/1000;
            long delta = now - r.getLastUpdate();
            return delta > OUT_OF_SYNC_AFTER_SECONDS;
        });
    }


    public void declareRegattaToRun(@NonNull String name,
                                    SuccessCallback<Void> success, ErrorCallback error){
        //define the new running regatta
        RunningStatus runningStatus = new RunningStatus(name);

        //clear previous data, and set the initial data
        Database.databaseWriteExecutor.execute(() -> {
            runningStatusDao.delete();
            runningStatusDao.insert(runningStatus);
        });
        //mock
        success.success(null);
    }


    /**
     * The mock version of this method does not make calls to the apis, instead if will
     * simulate the response data such as boats and their movements.
     *
     * Check out the interface for the documentation on the real behaviour
     */
    public void poll(Double lat, Double lon){
        Log.d("RUNNINGREGATTA", "received lat: "+ lat + "lon: "+ lon);
        Database.databaseWriteExecutor.execute(() -> {
            //TODO: mock boat data
            runningStatusDao.updateLocation(lat, lon, System.currentTimeMillis()/1000);
        });
    }

    public void setError(String error_code){
        Database.databaseWriteExecutor.execute(() -> {
            runningStatusDao.updateError(error_code);
        });
    }

}
