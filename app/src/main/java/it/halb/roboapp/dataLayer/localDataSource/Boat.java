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
