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

public class Repository {
    private AccountDao accountDao;
    private RegattaDao regattaDao;
    private LiveData<Account> account;
    private LiveData<List<Regatta>> regattas;

    public Repository(Application application){
        Database database = Database.getInstance(application);
        accountDao = database.accountDao();
        regattaDao = database.regattaDao();
        account = accountDao.getAccount();
        regattas = regattaDao.getAllRegattas();
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
        //deprecated, and does not work
        //new InsertRegattaAsyncTask(regattaDao).doInBackground(regatta);

        //deprecated, but at least it works
        /*
        AsyncTask.execute(() -> {
            regattaDao.insert(regatta);
        });
         */

        //test
        Executors.newSingleThreadExecutor().execute(() -> {
            regattaDao.insert(regatta);
        });
    }
}
