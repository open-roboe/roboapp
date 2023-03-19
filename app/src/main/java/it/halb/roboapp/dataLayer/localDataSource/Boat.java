package it.halb.roboapp.dataLayer.localDataSource;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "boat_table")
public class Boat {

    @NonNull
    @PrimaryKey
    private String username;
    private String role;
    private double latitude;
    private double longitude;

    public Boat(@NonNull String username, String role, double latitude, double longitude) {
        this.username = username;
        this.role = role;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
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
    public String getRole() {
        return role;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }




}
