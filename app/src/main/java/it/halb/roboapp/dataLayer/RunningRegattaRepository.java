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

public class RunningRegattaRepository {

    private final AccountDao accountDao;
    private final RegattaDao regattaDao;

    private final BuoyDao buoyDao;
    private final BoatDao boatDao;
    private final RoboaDao roboaDao;
    private final RunningStatusDao runningStatusDao;
    private final LiveData<Account> account;
    private LiveData<Regatta> regatta;

    private LiveData<List<Buoy>> buoyList;
    private LiveData<List<Boat>> boatList;
    private LiveData<List<Roboa>> roboaList;
    private LiveData<RunningStatus> runningStatus;


    public RunningRegattaRepository(Application application){
        //init local datasource
        Database database = Database.getInstance(application);
        accountDao = database.accountDao();
        regattaDao = database.regattaDao();
        buoyDao = database.buoyDao();
        boatDao = database.BoatDao();
        roboaDao = database.RoboaDao();
        runningStatusDao = database.RunningStatusDao();


        account = accountDao.getAccount();
        //all this data can be null, and WILL be null if
        //a running regatta is not set
        runningStatus = runningStatusDao.getRunningStatus();
        regatta = regattaDao.getRunningRegatta();
        buoyList = buoyDao.getAll();
        boatList = boatDao.getAll();
        roboaList = roboaDao.getAll();
    }

    /**
     * Return the regatta currently running
     */
    public LiveData<Regatta> getRegatta(){
        return regatta;
    }

    public LiveData<List<Buoy>> getAllBuoys(){
        return buoyList;
    }

    public LiveData<List<Boat>> getAllBoats(){
        return boatList;
    }

    public LiveData<List<Roboa>> getAllRoboas(){
        return roboaList;
    }

    public LiveData<RunningStatus> getStatus(){ return runningStatus; }

    /**
     * During its sync process, several things can go wrong.
     * When something goes wrong it will update the error String livedata with an
     * internationalized description of the error. Internationalized means that you can
     * directly print it in the ui
     *
     * @return A string containing a localized description of an error
     */
    public LiveData<String> getError(){
        return Transformations.map(runningStatus, RunningStatus::getError);
    }

    /**
     * @return the last time the app successfully synced state with the server
     */
    public LiveData<Date> getLastUpdate(){
        return Transformations.map(runningStatus, r ->{
            return new Date(r.getLastUpdate());
        });
    }
    public LiveData<Location> getLocation(){
        return Transformations.map(runningStatus, r ->{
            Location l = new Location("what is a provider? lol");
            l.setLatitude(r.getLat());
            l.setLongitude(r.getLon());
            return l;
        });
    }

    /**
     * @return true if the app managed to sync state with the server in the last few seconds
     */
    public LiveData<Boolean> isInSync(){
        return Transformations.map(runningStatus, r ->{
            long now = System.currentTimeMillis()/1000;
            long delta = now - r.getLastUpdate();
            return delta > OUT_OF_SYNC_AFTER_SECONDS;
        });
    }


    /**
     * Declare that you want to run the provided regatta.
     * This method will set the Regatta attribute, and delete all the other attributes.
     *
     * If this operation is successful, you can actually run the regatta by launching the RunningRegattaService
     *
     * @param name the name of the regatta you want to run
     */
    public void declareRegattaToRun(@NonNull String name,
                                    SuccessCallback<Void> success, ErrorCallback error){

        //define the new running regatta
        RunningStatus runningStatus = new RunningStatus(name);

        //clear previous data, and set the initial data
        Database.databaseWriteExecutor.execute(() -> {
            runningStatusDao.delete();
            buoyDao.deleteAll();
            boatDao.deleteAll();
            roboaDao.deleteAll();
            runningStatusDao.insert(runningStatus);
        });

        //mock
        Log.d("RUNNINGREGATTA", "declared id: "+ name);
        //TODO:
        // make api request to specific regatta, same request that poll() will make.
        // in case of errors call errorcallback
        // in case of success call successcallback. loadFragment will start the service
        success.success(null);
    }


    /**
     * This method is called periodically by the RunningRegattaService.
     * It updates the running regatta information.
     */
    public void poll(Double lat, Double lon){
        Log.d("RUNNINGREGATTA", "received lat: "+ lat + "lon: "+ lon);
        //mock update only status
        Database.databaseWriteExecutor.execute(() -> {
            //TODO: update only on success
            runningStatusDao.updateLocation(lat, lon, System.currentTimeMillis()/1000);
        });
    }

    /**
     * Set the error message that will be displayed in real time in the app.
     *
     * @param error_code an internationalized string describing the error
     */
    public void setError(String error_code){
        Database.databaseWriteExecutor.execute(() -> {
            runningStatusDao.updateError(error_code);
        });
    }

}
