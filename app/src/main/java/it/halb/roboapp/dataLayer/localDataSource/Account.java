package it.halb.roboapp.dataLayer.localDataSource;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Account stores the account information of the current logged user.
 * These information are very useful. for example: You can read account.isRaceOfficer
 * to decide if you want to enable some features in the UI that only race officers can use.
 *
 * Internally, the account LiveData is used to decide when to show the login activity:
 * If account is null, a redirect to the login activity is initiated.
 *
 */
@Entity(tableName = "account_table")
public class Account {
    @PrimaryKey
    @NonNull
    private String username;
    private String authToken;
    private boolean isAdmin;
    private boolean isRaceOfficer;

    public Account(@NonNull String username, String authToken, boolean isAdmin, boolean isRaceOfficer) {
        this.username = username;
        this.authToken = authToken;
        this.isAdmin = isAdmin;
        this.isRaceOfficer = isRaceOfficer;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isRaceOfficer() {
        return isRaceOfficer;
    }

    public void setRaceOfficer(boolean raceOfficer) {
        isRaceOfficer = raceOfficer;
    }

    public String toString(){
        return "username: " + username + " authToken: " + authToken + " isAdmin: " + isAdmin + " isRaceOfficer: " + isRaceOfficer;
    }
}
