package it.halb.roboapp.dataLayer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import it.halb.roboapp.dataLayer.localDataSource.Account;
import it.halb.roboapp.dataLayer.localDataSource.AccountDao;
import it.halb.roboapp.dataLayer.localDataSource.Database;
import it.halb.roboapp.dataLayer.remoteDataSource.ApiClient;
import it.halb.roboapp.dataLayer.remoteDataSource.ApiSharedPreference;
import it.halb.roboapp.dataLayer.remoteDataSource.LoginCallback;
import it.halb.roboapp.dataLayer.remoteDataSource.scheme.model.AuthToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void login(@NonNull String username, @NonNull String password, @NonNull LoginCallback loginCallback){
        apiClient.getAccountApi().loginApiAccountAuthPost(
                username,
                password,
                "password",
                null,
                null,
                null
        ).enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                if(response.isSuccessful() && response.body() != null){
                    String token = response.body().getAccessToken();
                    //update the auth token for the current api client session
                    apiClient.setAuthToken(token);
                    //TODO: set retrofit data
                    loginCallback.onSuccess();
                }
                else{
                    //TODO: get error details
                    loginCallback.onError(response.code(), "");
                }
            }

            @Override
            public void onFailure(Call<AuthToken> call, Throwable t) {
                loginCallback.onError(0, "network_error");
            }
        });
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
