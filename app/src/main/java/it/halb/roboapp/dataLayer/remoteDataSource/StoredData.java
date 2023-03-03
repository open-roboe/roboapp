package it.halb.roboapp.dataLayer.remoteDataSource;

import android.content.Context;
import android.content.SharedPreferences;

public class StoredData {
    private static String DEFAULT_API_BASE_URL = "https://roboapp.halb.it/";
    private Context ctx;
    private boolean isAdmin;
    private boolean isSuperAdmin;
    private String username;
    private String jwt;
    private String apiBaseUrl;

    public StoredData(Context ctx){
        this.ctx = ctx;
        pull();
    }

    private void pull(){
        SharedPreferences pref = ctx.getSharedPreferences("MyPref", 0); // 0 - for private mode
        isAdmin = pref.getBoolean("is_admin", false);
        isSuperAdmin = pref.getBoolean("is_super_admin", false);
        username = pref.getString("username", null);
        jwt = pref.getString("jwt", null);
        apiBaseUrl = pref.getString("api_base_url", DEFAULT_API_BASE_URL);
    }
    
    private void push(){
        SharedPreferences pref = ctx.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("is_admin", isAdmin);
        editor.putBoolean("is_super_admin", isSuperAdmin);
        editor.putString("username", username);
        editor.putString("jwt", jwt);
        editor.putString("api_base_url", apiBaseUrl);
        editor.commit();
    }

    /*
        Useful for logouts.
        save the apiBaseUrl, then call this method, then
        restored the saved apibaseurl with setApiBaseUrl(String);
     */
    public void deleteAll(){
        SharedPreferences pref = ctx.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    //getters

    public boolean isAdmin() {
        pull();
        return isAdmin;
    }

    public boolean isSuperAdmin() {
        pull();
        return isSuperAdmin;
    }

    public String getUsername() {
        pull();
        return username;
    }

    public String getJwt() {
        pull();
        return jwt;
    }

    public String getApiBaseUrl() {
        pull();
        return apiBaseUrl;
    }


    //setters

    public void setAll(String jwt, String username, String apiServer, boolean isAdmin, boolean isSuperAdmin){
        this.jwt = jwt;
        this.username = username;
        this.apiBaseUrl = apiServer;
        this.isAdmin = isAdmin;
        this.isSuperAdmin = isSuperAdmin;
        push();
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
        push();
    }

    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
        push();
    }

    public void setUsername(String username) {
        this.username = username;
        push();
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
        push();
    }

    public void setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
        push();
    }

}
