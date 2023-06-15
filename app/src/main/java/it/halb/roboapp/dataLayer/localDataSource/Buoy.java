package it.halb.roboapp.dataLayer.localDataSource;


import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

@Entity(tableName = "buoy_table", primaryKeys = {"regattaName", "id"})
public class Buoy {
    @NonNull
    private String regattaName;
    @NonNull
    private String id;
    private double latitude;
    private double longitude;
    private String bindedRobuoy;


    public Buoy(@NonNull String id, @NonNull String regattaName, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.regattaName = regattaName;
        this.bindedRobuoy = null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(regattaName, id, latitude, longitude);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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

    public LatLng getPosition()
    {
        return new LatLng(this.getLatitude(),this.getLongitude());
    }

    public String getBindedRobuoy() {
        return bindedRobuoy;
    }

    public void setBindedRobuoy(String bindedRobuoy) {
        this.bindedRobuoy = bindedRobuoy;
    }

    @NonNull
    @Override
    public String toString() {
        return "Buoy{" +
                "regattaName='" + regattaName + '\'' +
                ", id='" + id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
