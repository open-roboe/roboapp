package it.halb.roboapp.util;

import static it.halb.roboapp.util.Constants.DEFAULT_API_BASE_URL;
import static it.halb.roboapp.util.Constants.KEY_API_BASE_URL;
import static it.halb.roboapp.util.Constants.SHARED_PREFERENCES_FILENAME;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

/**
 * Utility class to store and retrieve application preferences,
 * offering a typesafe interface to the SharedPreferences API
 */
public class SharedPreferenceUtil {

    private final Application application;

    public SharedPreferenceUtil(@NonNull Application application){
        this.application = application;
    }

    public void setApiBaseUrl(@NonNull String apiBaseUrl){
        SharedPreferences pref = application.getSharedPreferences(SHARED_PREFERENCES_FILENAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_API_BASE_URL, apiBaseUrl);
        editor.commit();
    }

    @NonNull
    public String getApiBaseUrl(){
        SharedPreferences pref = application.getSharedPreferences(SHARED_PREFERENCES_FILENAME, 0); // 0 - for private mode
        return pref.getString(KEY_API_BASE_URL, DEFAULT_API_BASE_URL);
    }
}
