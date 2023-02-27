package it.halb.roboapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

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

    //TODO: replace asynctask with something better
    //https://developer.android.com/training/data-storage/room/async-queries#guava-livedata
    public void insertAccount(Account account){
        new InsertAccountAsyncTask(accountDao).doInBackground(account);
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

    }

    private static class InsertAccountAsyncTask extends AsyncTask<Account, Void, Void> {

        private final AccountDao accountDao;

        private InsertAccountAsyncTask(AccountDao accountDao){
            this.accountDao = accountDao;
        }
        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.insert(accounts[0]);
            return null;
        }
    }

    private static class InsertRegattaAsyncTask extends AsyncTask<Regatta, Void, Void> {

        private final RegattaDao regattaDao;

        private InsertRegattaAsyncTask(RegattaDao regattaDao){
            this.regattaDao = regattaDao;
        }
        @Override
        protected Void doInBackground(Regatta... regattas) {
            regattaDao.insert(regattas[0]);
            return null;
        }
    }

}
