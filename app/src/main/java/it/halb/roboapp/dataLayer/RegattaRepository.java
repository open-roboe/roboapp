package it.halb.roboapp.dataLayer;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import it.halb.roboapp.dataLayer.localDataSource.Account;
import it.halb.roboapp.dataLayer.localDataSource.AccountDao;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.BuoyDao;
import it.halb.roboapp.dataLayer.localDataSource.Database;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.dataLayer.localDataSource.RegattaDao;
import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.dataLayer.localDataSource.RoboaDao;
import it.halb.roboapp.dataLayer.remoteDataSource.ApiClient;
import it.halb.roboapp.util.SharedPreferenceUtil;

public class RegattaRepository implements RegattaInterface {
    private final ApiClient apiClient;
    private final AccountDao accountDao;
    private final RegattaDao regattaDao;
    private final BuoyDao buoyDao;
    private final RoboaDao roboaDao;
    private final LiveData<Account> account;
    private final LiveData<List<Regatta>> regattas;

    //TODO: transform into singleton
    public RegattaRepository(Application application){
        //init local datasource
        Database database = Database.getInstance(application);
        accountDao = database.accountDao();
        regattaDao = database.regattaDao();
        buoyDao = database.buoyDao();
        roboaDao = database.roboaDao();
        account = accountDao.getAccount();
        regattas = regattaDao.getAllRegattas();
        //init data used by remote data source
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(application);
        String apiBaseUrl = sharedPreferenceUtil.getApiBaseUrl();
        String authToken = null;
        if(account.getValue() != null)
            authToken = account.getValue().getAuthToken();
        //init remote data source
        apiClient = new ApiClient(apiBaseUrl, authToken);
    }

    public LiveData<List<Regatta>> getAllRegattas(){
        return regattas;
    }

    public void loadAllRegattas(SuccessCallback<List<Regatta>> successCallback, ErrorCallback errorCallback){
        //mock
        successCallback.success(getAllRegattas().getValue());
    }

    public void deleteRegatta(Regatta regatta, SuccessCallback<Void> successCallback, ErrorCallback errorCallback){
        //mock
        Database.databaseWriteExecutor.execute(() -> regattaDao.delete(regatta));
        //TODO: add constraint key, or manually delete buoys from here
    }

    @ParametersAreNonnullByDefault
    public void insertRegatta(
            Regatta regatta,
            List<Buoy> buoys,
            SuccessCallback<Void> successCallback,
            ErrorCallback errorCallback
    ){
        //mock
        Database.databaseWriteExecutor.execute(() ->{
            regattaDao.insert(regatta);
            buoyDao.insert(buoys);
        });
        successCallback.success(null);
    }

    public void updateBuoy(Buoy buoy){
        Database.databaseWriteExecutor.execute(() -> {
            buoyDao.update(buoy);
        });
    }

    public void insertRoboa(Roboa roboa){
        Database.databaseWriteExecutor.execute(() -> {
            roboaDao.insert(roboa);
        });
    }

    public void updateRoboa(Roboa roboa){
        Database.databaseWriteExecutor.execute(() -> {
            roboaDao.update(roboa);
        });
    }
}
