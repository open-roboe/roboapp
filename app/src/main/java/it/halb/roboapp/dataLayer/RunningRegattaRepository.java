package it.halb.roboapp.dataLayer;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

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

public class RunningRegattaRepository {

    private final AccountDao accountDao;
    private final RegattaDao regattaDao;

    private final BuoyDao buoyDao;
    private final BoatDao boatDao;
    private final RoboaDao roboaDao;

    private final LiveData<Account> account;
    private LiveData<Regatta> regatta;

    private LiveData<List<Buoy>> buoyList;
    private LiveData<List<Boat>> boatList;
    private LiveData<List<Roboa>> roboaList;


    public RunningRegattaRepository(Application application){
        //init local datasource
        Database database = Database.getInstance(application);
        accountDao = database.accountDao();
        regattaDao = database.regattaDao();
        buoyDao = database.buoyDao();
        boatDao = database.BoatDao();
        roboaDao = database.RoboaDao();

        account = accountDao.getAccount();
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

    public void getCurrentPosition(){

    }

    /**
     * define class status:
     * lastupdate, lasterror, offline(deriva da lastupdate)
     */
    public void getStatus(){}

    /**
     * Declare that you want to run the provided regatta.
     * This method will set the Regatta attribute, and delete all the other attributes.
     *
     * If this operation is successful, you can actually run the regatta by launching the RunningRegattaService
     *
     * @param name the name id of the regatta you want to run
     */
    public void declareRegattaToRun(@NonNull String name,
                                    SuccessCallback<Void> success, ErrorCallback error){
        //mock
        regatta = regattaDao.getRegatta(name);
        buoyList = buoyDao.getBuoy(name);
        boatList = null;
        roboaList = null;
        Log.d("RUNNINGREGATTA", "declared id: "+ name);
        //TODO:
        // make api request to specific regatta, same request that poll() will make.
        // in case of errors call errorcallback
        // in case of success call successcallback. loadFragment will start the service
        success.success(null);

    }

    /**
     * Completely reset the running regatta, removing every object.
     * called by declareRegattaToRun()
     *
     */
    private void resetRunningRegatta(){

    }

    /**
     * This method is called periodically by the RunningRegattaService.
     * It updates the running regatta information.
     */
    public void poll(Double lat, Double lon){
        Log.d("RUNNINGREGATTA", "received lat: "+ lat + "lon: "+ lon);
        //mock update only status
    }

    public void setError(String error_code){
        //mock
        //TODO: create runningregatta retrofit model and Dao, containing:
        //  currenterror, lastupdate, currentlan, currentlat
    }

}
