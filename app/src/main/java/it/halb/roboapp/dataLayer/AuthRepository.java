package it.halb.roboapp.dataLayer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import it.halb.roboapp.dataLayer.localDataSource.Account;
import it.halb.roboapp.dataLayer.localDataSource.AccountDao;
import it.halb.roboapp.dataLayer.localDataSource.Database;
import it.halb.roboapp.dataLayer.remoteDataSource.ApiCallbackLambda;
import it.halb.roboapp.dataLayer.remoteDataSource.ApiClient;
import it.halb.roboapp.util.SharedPreferenceUtil;
import it.halb.roboapp.dataLayer.remoteDataSource.converters.AccountConverter;

public class AuthRepository {
    private ApiClient apiClient;
    private final AccountDao accountDao;
    private final LiveData<Account> account;
    private final SharedPreferenceUtil sharedPreferenceUtil;

    private static AuthRepository instance;

    private AuthRepository(Application application){
        //init local datasource
        Database database = Database.getInstance(application);
        accountDao = database.accountDao();
        account = accountDao.getAccount();
        //init data used by remote data source
        sharedPreferenceUtil = new SharedPreferenceUtil(application);
        //init remote data source
        initApiClient();
    }

    private void initApiClient(){
        String apiBaseUrl = sharedPreferenceUtil.getApiBaseUrl();
        String authToken = null;
        if(account.getValue() != null)
            authToken = account.getValue().getAuthToken();
        //init remote data source
        apiClient = new ApiClient(apiBaseUrl, authToken);
    }

    public static AuthRepository getInstance(Application application){
        if(instance == null){
            instance = new AuthRepository(application);
        }
        return instance;
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
                username, password,
                "password", null, null, null
        ).enqueue(new ApiCallbackLambda<>(
                //success
                data -> {
                    if(data == null){
                        errorCallback.error(1, "unexpected_state");
                        return;
                    }
                    String token = data.getAccessToken();
                    apiClient.setAuthToken(token);
                    loadAccount(successCallback, errorCallback);
                },

                //error
                errorCallback::error
        ));
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
        //perform api request
        apiClient.getAccountApi().getLoggedUserApiAccountGet(
                /* no parameters required for this method */
        ).enqueue(new ApiCallbackLambda<>(
                //success
                data -> {
                    //convert the api response to a local data source object
                    AccountConverter converter = new AccountConverter();
                    Account account = converter.convertFromDto(data);
                    account.setAuthToken(
                            apiClient.getAuthToken()
                    );
                    //update the local data source with the account data
                    Database.databaseWriteExecutor.execute(() -> accountDao.insert(account));
                    //update the callback if exists
                    if(successCallback != null)
                        successCallback.success(account);
                },

                //error
                (code, detail) -> {
                    if(errorCallback != null)
                        errorCallback.error(code, detail);
                },

                //auth error
                () -> {
                    Database.databaseWriteExecutor.execute(accountDao::delete);
                }
        ));
    }


    /**
     * Set the api base url for the api endpoint. This is a synchronous operation
     *
     * @param url the base url for the api endpoint
     * @return whether the operation was successful. if false, the url was invalid
     */
    public boolean setApiBaseUrl(@Nullable String url){
        if(url != null && ApiClient.isValidUrl(url)){
            //save the new url
            sharedPreferenceUtil.setApiBaseUrl(url);
            //reload api client, to apply the url changes
            initApiClient();
            return true;
        }
        return false;
    }

    /**
     * This is a synchronous operation
     *
     * @return the url string used for all the api requests
     */
    @NonNull
    public String getApiBaseUrl(){
        return sharedPreferenceUtil.getApiBaseUrl();
    }

}
