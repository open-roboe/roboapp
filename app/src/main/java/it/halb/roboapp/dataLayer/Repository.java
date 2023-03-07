package it.halb.roboapp.dataLayer;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;

import it.halb.roboapp.dataLayer.localDataSource.Account;
import it.halb.roboapp.dataLayer.localDataSource.AccountDao;
import it.halb.roboapp.dataLayer.localDataSource.Database;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.dataLayer.localDataSource.RegattaDao;
import it.halb.roboapp.util.SharedPreferenceUtil;
import it.halb.roboapp.dataLayer.remoteDataSource.ApiClient;

public class Repository {
    private final ApiClient apiClient;
    private final AccountDao accountDao;
    private final RegattaDao regattaDao;
    private final LiveData<Account> account;
    private final LiveData<List<Regatta>> regattas;

    //TODO: transform into singleton
    public Repository(Application application){
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

    public void logout(){
        Executors.newSingleThreadExecutor().execute(accountDao::delete);
    }

    public void insertAccount(Account account){
        Executors.newSingleThreadExecutor().execute(() -> accountDao.insert(account));
    }
    public LiveData<Account> getAccount(){
        return account;
    }

    public LiveData<List<Regatta>> getAllRegattas(){
        return regattas;
    }

    public void deleteRegatta(Regatta regatta){
        Executors.newSingleThreadExecutor().execute(() -> regattaDao.delete(regatta));
    }

    public void insertRegatta(Regatta regatta){
        Executors.newSingleThreadExecutor().execute(() -> regattaDao.insert(regatta));
    }
}
