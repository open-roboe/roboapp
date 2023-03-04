package it.halb.roboapp.dataLayer;

import android.app.Application;

import androidx.lifecycle.LiveData;

import it.halb.roboapp.dataLayer.localDataSource.Account;
import it.halb.roboapp.dataLayer.localDataSource.AccountDao;
import it.halb.roboapp.dataLayer.localDataSource.Database;
import it.halb.roboapp.dataLayer.remoteDataSource.ApiClient;
import it.halb.roboapp.dataLayer.remoteDataSource.ApiSharedPreference;

public class AuthRepository {
    private final ApiClient apiClient;
    private final AccountDao accountDao;
    private final LiveData<Account> account;

    public AuthRepository(Application application){
        //init local datasource
        Database database = Database.getInstance(application);
        accountDao = database.accountDao();
        account = accountDao.getAccount();
        //init data used by remote data source
        ApiSharedPreference apiSharedPreference = new ApiSharedPreference(application.getApplicationContext());
        String apiBaseUrl = apiSharedPreference.getApiBaseUrl();
        String authToken = null;
        if(account.getValue() != null)
            authToken = account.getValue().getAuthToken();
        //init remote data source
        apiClient = new ApiClient(apiBaseUrl, authToken);
    }

    public LiveData<Account> getAccount(){
        return account;
    }

    public void login(){
        //TODO. parameters: username, password, callback
        //on success: save user data, call callback success

        //this could be a good idea: when a login is successful, make a call to
        //retrieve regattas, as a final test that everything is ok.
        //this has the bonus that when we redirect to the regattas page,
        //they have already been loaded so there wont be flashes of empty list

        //a better pattern could be load the list fragment, and show a placeholder
        //if this is the first time it has been shown. But it requires more design work
        //and complexity

    }
}
