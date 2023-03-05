package it.halb.roboapp.dataLayer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.concurrent.Executors;

import it.halb.roboapp.dataLayer.localDataSource.Account;
import it.halb.roboapp.dataLayer.localDataSource.AccountDao;
import it.halb.roboapp.dataLayer.localDataSource.Database;
import it.halb.roboapp.dataLayer.remoteDataSource.ApiCallback;
import it.halb.roboapp.dataLayer.remoteDataSource.ApiClient;
import it.halb.roboapp.dataLayer.remoteDataSource.ApiSharedPreference;
import it.halb.roboapp.dataLayer.remoteDataSource.converters.AccountConverter;
import it.halb.roboapp.dataLayer.remoteDataSource.scheme.model.AuthToken;
import it.halb.roboapp.dataLayer.remoteDataSource.scheme.model.UserResponse;
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

    public void login(@NonNull String username, @NonNull String password, @NonNull RepositoryCallback<Void> loginCallback){
        apiClient.getAccountApi().loginApiAccountAuthPost(
                username,
                password,
                "password",
                null,
                null,
                null
        ).enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(@NonNull Call<AuthToken> call, @NonNull Response<AuthToken> response) {
                if(response.isSuccessful() && response.body() != null){
                    String token = response.body().getAccessToken();
                    //update the auth token for the current api client session
                    apiClient.setAuthToken(token);
                    //perform other api calls to set up the account
                    postLoginSetup(loginCallback);
                }
                else{
                    //TODO: get error details
                    loginCallback.onError(response.code(), "");
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthToken> call, @NonNull Throwable t) {
                loginCallback.onError(0, "network_error");
            }
        });
    }

    /**
     * Perform some api calls to set up the account after a successful authentication
     */
    private void postLoginSetup(@NonNull RepositoryCallback<Void> callback){
        loadAccount(new RepositoryCallback<Account>() {
            @Override
            public void onSuccess(Account data) {

                callback.onSuccess(null);
            }

            @Override
            public void onError(int code, String detail) {
                callback.onError(code, detail);
            }
        });
    }

    public LiveData<Account> getAccount(){
        return account;
    }

    /**
     * Perform an api request to get the account data. when it succeeds, the livedata returned by
     * getAccount will update.
     * The success of this operation can be checked with the Repository callback onSuccess or onError methods
     */
    public void loadAccount(@Nullable RepositoryCallback<Account> callback){
        apiClient.getAccountApi().getLoggedUserApiAccountGet().enqueue(new ApiCallback<UserResponse>() {
            @Override
            public void onSuccess(UserResponse data) {
                //convert the api response to a local data source object
                AccountConverter converter = new AccountConverter();
                Account account = converter.convertFromDto(data);
                account.setAuthToken(
                        apiClient.getAuthToken()
                );
                //update the local data source with the account data
                Executors.newSingleThreadExecutor().execute(() -> accountDao.insert(account));
                //update the callback if exists
                if(callback != null)
                    callback.onSuccess(account);
            }

            @Override
            public void onError(int code, String detail) {
                if(callback != null)
                    callback.onError(code, detail);
            }

            @Override
            public void onAuthError() {
                Executors.newSingleThreadExecutor().execute(accountDao::delete);
            }
        });
    }

}
