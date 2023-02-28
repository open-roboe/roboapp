package it.halb.roboapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;

import it.halb.roboapp.data.local.Account;
import it.halb.roboapp.data.local.AccountDao;
import it.halb.roboapp.data.local.Database;
import it.halb.roboapp.data.local.Regatta;
import it.halb.roboapp.data.local.RegattaDao;

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
    public void update(Account account){

    }
    public void delete(Account account){

    }

    public LiveData<Account> getAccount(){
        return account;
    }

    public LiveData<List<Regatta>> getAllRegattas(){
        return regattas;
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
