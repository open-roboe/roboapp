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
import it.halb.roboapp.dataLayer.remoteDataSource.ApiBaseUrlPreference;
import it.halb.roboapp.dataLayer.remoteDataSource.ApiClient;

public class Repository {
    private final ApiClient apiClient;
    private final AccountDao accountDao;
    private final RegattaDao regattaDao;
    private final LiveData<Account> account;
    private final LiveData<List<Regatta>> regattas;

    public Repository(Application application){
        //init local datasource
        Database database = Database.getInstance(application);
        accountDao = database.accountDao();
        regattaDao = database.regattaDao();
        account = accountDao.getAccount();
        regattas = regattaDao.getAllRegattas();
        //init apiBaseUrl shared preference
        ApiBaseUrlPreference apiBaseUrlPreference = new ApiBaseUrlPreference(application.getApplicationContext());
        //init remote data source
        apiClient = new ApiClient(
                apiBaseUrlPreference.getApiBaseUrl()
                , "jwt");
    }

    public void insertAccount(Account account){
        Executors.newSingleThreadExecutor().execute(() -> {
            accountDao.insert(account);
        });
    }
    public LiveData<Account> getAccount(){
        return account;
    }

    public LiveData<List<Regatta>> getAllRegattas(){
        return regattas;
    }

    public void deleteRegatta(Regatta regatta){
        Executors.newSingleThreadExecutor().execute(() -> {
            regattaDao.delete(regatta);
        });
    }

    public void insertRegatta(Regatta regatta){
        Executors.newSingleThreadExecutor().execute(() -> {
            regattaDao.insert(regatta);
        });
    }
}
