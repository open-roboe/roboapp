package it.halb.roboapp.dataLayer.remoteDataSource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URL;

import it.halb.roboapp.dataLayer.remoteDataSource.scheme.api.AccountApi;
import it.halb.roboapp.dataLayer.remoteDataSource.scheme.api.CourseApi;
import it.halb.roboapp.dataLayer.remoteDataSource.scheme.api.PollingApi;
import it.halb.roboapp.dataLayer.remoteDataSource.scheme.api.RoboaApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private Retrofit retrofit;
    private final OauthHttBearerInterceptor oauth;

    public PollingApi getPollingApi(){
        return retrofit.create(PollingApi.class);
    }

    public CourseApi getCourseApi(){
        return retrofit.create(CourseApi.class);
    }

    public AccountApi getAccountApi(){
        return retrofit.create(AccountApi.class);
    }

    public RoboaApi getRoboaApi() {
        return retrofit.create(RoboaApi.class);
    }

    /**
     * Initialize an api client, with a given baseUrl and jwt token.
     * If you want to change baseUrl or jwt, destroy this client and instantiate a new one
     *
     * @param baseUrl Must be a valid url, ending with a slash
     * @param authToken OAuth token, received from a previous authentication flow
     */
    public ApiClient(@NonNull String baseUrl, @Nullable String authToken){
        //validate the baseUrl string
        if(!isValidUrl(baseUrl))
            throw new IllegalArgumentException("Invalid baseurl");

        //initialize oauth retrofit interceptor
        oauth = new OauthHttBearerInterceptor("Bearer");

        //initialize auth token
        setAuthToken(authToken);

        //initialize retrofit
        initializeRetrofit(baseUrl);
    }

    /**
     * configure OAuth token if it is a non null string.
     * calling this method after initialization will change the auth token for all the successive
     * api calls in the session
     * @param authToken the auth token. With the current api backend this is a jwt, but it could be any string
     */
    public void setAuthToken(@Nullable String authToken){
        if(authToken != null)
            oauth.setBearerToken(authToken);
    }

    public String getAuthToken(){
        return oauth.getBearerToken();
    }

    private void initializeRetrofit(@NonNull String baseUrl){
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
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    /**
     * Check that a string is a valid url, that can be used for an api client
     * @param url the url string
     * @return true if the string is valid
     */
    public static boolean isValidUrl(String url){
        try{
            new URL(url).toURI();
            return url.endsWith("/");
        }
        catch (Exception e){
            return false;
        }
    }

}
