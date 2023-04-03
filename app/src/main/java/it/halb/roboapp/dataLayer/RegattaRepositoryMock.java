package it.halb.roboapp.dataLayer;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Random;

import javax.annotation.ParametersAreNonnullByDefault;

import it.halb.roboapp.dataLayer.localDataSource.Account;
import it.halb.roboapp.dataLayer.localDataSource.AccountDao;
import it.halb.roboapp.dataLayer.localDataSource.Boat;
import it.halb.roboapp.dataLayer.localDataSource.BoatDao;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.BuoyDao;
import it.halb.roboapp.dataLayer.localDataSource.Database;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.dataLayer.localDataSource.RegattaDao;
import it.halb.roboapp.util.SharedPreferenceUtil;
import it.halb.roboapp.dataLayer.remoteDataSource.ApiClient;

public class RegattaRepositoryMock implements RegattaInterface {
    private final AccountDao accountDao;
    private final RegattaDao regattaDao;
    private final BuoyDao buoyDao;
    private final BoatDao boatDao;
    private final LiveData<Account> account;
    private final LiveData<List<Regatta>> regattas;

    public RegattaRepositoryMock(Application application){
        //init local datasource
        Database database = Database.getInstance(application);
        accountDao = database.accountDao();
        regattaDao = database.regattaDao();
        buoyDao = database.buoyDao();
        boatDao = database.boatDao();

        account = accountDao.getAccount();
        regattas = regattaDao.getAllRegattas();
        //init data used by remote data source
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(application);
        String apiBaseUrl = sharedPreferenceUtil.getApiBaseUrl();
        String authToken = null;
        if(account.getValue() != null)
            authToken = account.getValue().getAuthToken();
        //init remote data source
        ApiClient apiClient = new ApiClient(apiBaseUrl, authToken);
    }

    public LiveData<List<Regatta>> getAllRegattas(){
        return regattas;
    }

    public void loadAllRegattas(SuccessCallback<List<Regatta>> successCallback, ErrorCallback errorCallback){
        successCallback.success(getAllRegattas().getValue());
    }

    public void deleteRegatta(Regatta regatta, SuccessCallback<Void> successCallback, ErrorCallback errorCallback){
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
        int r = new Random().nextInt();
        Boat b = new Boat(
                "boat-"+ r,
                regatta.getName(),
                r%2 == 0,
                regatta.getLatitude() + 0.0001,
                regatta.getLongitude(),
                System.currentTimeMillis() /1000
        );
        r = new Random().nextInt();
        Boat b2 = new Boat(
                "boat-"+ r,
                regatta.getName(),
                r%2 == 0,
                regatta.getLatitude() + 0.0001,
                regatta.getLongitude(),
                System.currentTimeMillis() /1000
        );

        Database.databaseWriteExecutor.execute(() ->{
            regattaDao.insert(regatta);
            buoyDao.insert(buoys);
            //mock
            boatDao.insert(b);
            boatDao.insert(b2);
        });
        successCallback.success(null);
    }
}
