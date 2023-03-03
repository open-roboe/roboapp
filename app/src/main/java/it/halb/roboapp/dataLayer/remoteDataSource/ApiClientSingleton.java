package it.halb.roboapp.dataLayer.remoteDataSource;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import it.halb.roboapp.dataLayer.remoteDataSource.scheme.api.RoboaApi;
import it.halb.roboapp.dataLayer.remoteDataSource.scheme.api.AccountApi;
import it.halb.roboapp.dataLayer.remoteDataSource.scheme.api.CourseApi;
import it.halb.roboapp.dataLayer.remoteDataSource.scheme.api.PollingApi;
import it.halb.roboapp.dataLayer.remoteDataSource.scheme.model.AuthToken;
import it.halb.roboapp.dataLayer.remoteDataSource.scheme.model.UserResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientSingleton {
    private static ApiClientSingleton instance;
    private Context context; //this is very probably a memory leak, but it's fine
    private Retrofit retrofit;
    private OauthHttBearerInterceptor oauth;
    private UserResponse currentUser; //the user that is currently logged in the app

    public UserResponse getCurrentUser(){ return currentUser; }

    public void setCurrentUser(UserResponse currentUser) { this.currentUser = currentUser; }

    public PollingApi getPollingApi(){
        return retrofit.create(PollingApi.class);
    }

    public CourseApi getCourseApi(){
        return retrofit.create(CourseApi.class);
    }

    public AccountApi getAccountApi(){
        return retrofit.create(AccountApi.class);
    }

    public RoboaApi getRoboaApi(){
        return retrofit.create(RoboaApi.class);
    }

    public void login(String username, String password, LoginCallback loginCallback){
        getAccountApi().loginApiAccountAuthPost(username, password, "password", null, null, null).enqueue(
                new Callback<AuthToken>() {
                    @Override
                    public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                        if(response.isSuccessful() && response.body() != null){
                            String token = response.body().getAccessToken();
                            setAuthToken(token);
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
                }
        );
    }

    /**
     * Clear stored credentials.
     *
     * This method will not call the login activity, this is responsibility of the caller
     */
    public void logout(){
        //clear storage, except for baseurl
        StoredData storedData = new StoredData(context);
        String baseUrl =  storedData.getApiBaseUrl();
        storedData.deleteAll();
        storedData.setApiBaseUrl(baseUrl);
        //clear current user
        currentUser = null;
    }

    public boolean authRequired(){
        //no stored jwt
        StoredData storedData = new StoredData(context);
        return (storedData.getJwt() == null);
    }

    public void updateBaseUrl(String baseUrl){
        StoredData storedData = new StoredData(context);
        String oldUrl = storedData.getApiBaseUrl();
        //if the new url is different
        if(!oldUrl.equals(baseUrl)){
            //update storage
            storedData.setApiBaseUrl(baseUrl);
            //recreate retrofit client
            initializeRetrofit();
        }
    }

    public String getBaseUrl(){
        StoredData storedData = new StoredData(context);
        return storedData.getApiBaseUrl();
    }

    private void setAuthToken(String token){
        StoredData storedData = new StoredData(context);
        storedData.setJwt(token);
        oauth.setBearerToken(token);
    }

    private void initializeRetrofit(){
        //initialize logging interceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(oauth)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


    /**
     * Singleton instance getter
     * @param context android app context
     * @return The ApiClientSingleton instance shared between activities
     */
    public static ApiClientSingleton getInstance(Context context){
        if(instance == null){
            instance = new ApiClientSingleton(context.getApplicationContext());
        }
        return instance;
    }

    private ApiClientSingleton(Context context){
        //save received context
        this.context = context;

        //initialize oauth retrofit interceptor
        oauth = new OauthHttBearerInterceptor("Bearer");

        //configure oauth token if there is a saved token
        StoredData storedData = new StoredData(context);
        if(storedData.getJwt() != null)
            oauth.setBearerToken(storedData.getJwt());

        //initialize retrofit
        initializeRetrofit();
    }
}