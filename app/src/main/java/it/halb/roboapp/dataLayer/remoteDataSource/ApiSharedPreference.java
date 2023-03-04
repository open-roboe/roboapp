package it.halb.roboapp.dataLayer.remoteDataSource;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Typesafe interface to a shared preference for the api configuration
 */
public class ApiSharedPreference {
    //TODO: pull this from gradle config
    private static final String DEFAULT_API_BASE_URL = "https://roboapp.halb.it/";
    private static final String PREFERENCES_FILENAME = "roboapp_api_preferences";

    private static final String KEY_API_BASE_URL = "api_base_url";
    private final Context ctx;

    public ApiSharedPreference(@NonNull Context ctx){
        this.ctx = ctx;
    }

    public void setApiBaseUrl(@NonNull String apiBaseUrl){
        SharedPreferences pref = ctx.getSharedPreferences(PREFERENCES_FILENAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_API_BASE_URL, apiBaseUrl);
        editor.commit();
    }

    @NonNull
    public String getApiBaseUrl(){
        SharedPreferences pref = ctx.getSharedPreferences(PREFERENCES_FILENAME, 0); // 0 - for private mode
        return pref.getString(KEY_API_BASE_URL, DEFAULT_API_BASE_URL);
    }
}
