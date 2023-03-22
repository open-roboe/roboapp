package it.halb.roboapp.dataLayer;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import it.halb.roboapp.dataLayer.localDataSource.Account;
import it.halb.roboapp.dataLayer.localDataSource.AccountDao;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Database;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.dataLayer.localDataSource.RegattaDao;
import it.halb.roboapp.util.SharedPreferenceUtil;
import it.halb.roboapp.dataLayer.remoteDataSource.ApiClient;

public class RegattaRepository {
    private final ApiClient apiClient;
    private final AccountDao accountDao;
    private final RegattaDao regattaDao;
    private final LiveData<Account> account;
    private final LiveData<List<Regatta>> regattas;

    //TODO: transform into singleton
    public RegattaRepository(Application application){
        //init local datasource
        Database database = Database.getInstance(application);
        accountDao = database.accountDao();
        regattaDao = database.regattaDao();

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

    /**
     * Return a LiveData list of all the regattas. To update this list, call loadAllRegattas()
     *
     * Note that a Regatta object does not include the associated buoys, boats, or roboas.
     * If you want to get that data you must use the RunningRegattaRepository repository
     *
     */
    public LiveData<List<Regatta>> getAllRegattas(){
        return regattas;
    }

    /**
     * TODO
     * Perform an api request to get the list of all the regattas. When it succeeds, the Livedata allRegattas list is
     * updated with the new data. Use the method getAllRegattas() to get the Livedata List.
     */
    public void loadAllRegattas(SuccessCallback<List<Regatta>> successCallback, ErrorCallback errorCallback){
        //mock
        successCallback.success(getAllRegattas().getValue());
    }

    /**
     * TODO
     */
    public void deleteRegatta(Regatta regatta, SuccessCallback<Void> successCallback, ErrorCallback errorCallback){
        //mock
        Database.databaseWriteExecutor.execute(() -> regattaDao.delete(regatta));
    }

    /**
     * Create a regatta, both locally and remotely in the api server.
     * In case of error the local copy will be automatically deleted, and th ErrorCallback will execute.
     */
    public void insertRegatta(
            Regatta regatta,
            List<Buoy> buoys,
            SuccessCallback<Void> successCallback,
            ErrorCallback errorCallback
    ){
        //mock
        Database.databaseWriteExecutor.execute(() ->{
            regattaDao.insert(regatta);
            //TODO: insert all buoys. requires DAO method
        });
        successCallback.success(null);
    }
}
