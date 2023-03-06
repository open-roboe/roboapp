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

    /**
     * Account contains the account information of the current logged user.
     * These information are very useful. for example: You can read account.isRaceOfficer
     * to decide if you want to enable some features in the UI that only race officers can use.
     *
     * Internally, the account LiveData is used to decide when to show the login activity:
     * If account is null, a redirect to the login activity is initiated.
     *
     * @return The account livedata object
     */
    public LiveData<Account> getAccount(){
        return account;
    }

    /**
     * Log the user in, and saves the user account info into an Account object
     *
     * You can get the account LiveData object with the method getAccount(). When there is no logged
     * user account is null. after a successful login account gets populated with the user account info.
     *
     * @param username the account username
     * @param password the account password
     * @param successCallback this callback will be executed if the login is successful
     * @param errorCallback this callback will be executed if the login fails
     */
    public void login(@NonNull String username,
                      @NonNull String password,
                      @NonNull SuccessCallback<Account> successCallback,
                      @NonNull ErrorCallback errorCallback){
        //perform login api request
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
                    //perform another api call to set up the account livedata object
                    loadAccount(successCallback, errorCallback);
                }
                else{
                    //TODO: get error details
                    errorCallback.error(response.code(), "");
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthToken> call, @NonNull Throwable t) {
                errorCallback.error(0, "network_error");
            }
        });
    }

    /**
     * Perform an api request to get the user data. When it succeeds, the Livedata Account object is
     * updated with the user data. Use the method getAccount() to get the account Livedata object.
     *
     * This method is called automatically by login().
     *
     * @param successCallback This callback will be executed if the operation is successful
     * @param errorCallback This callback will be executed if the operation failed
     */
    public void loadAccount(@Nullable SuccessCallback<Account> successCallback, @Nullable ErrorCallback errorCallback){
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
                if(successCallback != null)
                    successCallback.success(account);
            }

            @Override
            public void onError(int code, String detail) {
                if(errorCallback != null)
                    errorCallback.error(code, detail);
            }

            @Override
            public void onAuthError() {
                Executors.newSingleThreadExecutor().execute(accountDao::delete);
            }
        });
    }

}
