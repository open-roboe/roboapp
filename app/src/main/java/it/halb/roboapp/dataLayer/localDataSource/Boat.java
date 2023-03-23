package it.halb.roboapp.dataLayer.localDataSource;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity(tableName = "boat_table")
public class Boat {

    @NonNull
    @PrimaryKey
    private String username;

    private String regattaName;
    private boolean isRaceOfficer;
    private double latitude;
    private double longitude;

    private long lastUpdate;

    @Override
    public int hashCode() {
        return Objects.hash(username, regattaName, isRaceOfficer, latitude, longitude, lastUpdate);
    }

    public Boat(@NonNull String username, String regattaName, boolean isRaceOfficer, double latitude, double longitude, long lastUpdate) {
        this.username = username;
        this.regattaName = regattaName;
        this.isRaceOfficer = isRaceOfficer;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastUpdate = lastUpdate;
    }
    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public String getUsername() {
        return username;
    }
    public void setIsRaceOfficer(boolean isRaceOfficer) {
        this.isRaceOfficer = isRaceOfficer;
    }
    public boolean isRaceOfficer() {
        return isRaceOfficer;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public String getRegattaName() {
        return regattaName;
    }

    public void setRegattaName(String regattaName) {
        this.regattaName = regattaName;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }



}
