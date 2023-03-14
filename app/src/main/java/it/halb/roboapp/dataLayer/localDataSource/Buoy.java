package it.halb.roboapp.dataLayer.localDataSource;


import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "buoy_table",primaryKeys = {"regattaName", "id"})
public class Buoy {
    @NonNull
    private String regattaName;
    @NonNull
    private int id;
    private double latitude;
    private double longitude;
    private String description;

public Buoy(int id, double latitude, double longitude, String description) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getRegattaName() {
        return regattaName;
    }

    public void setRegattaName(@NonNull String regattaName) {
        this.regattaName = regattaName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
