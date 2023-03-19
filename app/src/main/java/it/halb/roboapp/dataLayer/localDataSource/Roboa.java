package it.halb.roboapp.dataLayer.localDataSource;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

@Entity(tableName = "Roboa_table")
public class Roboa {

    @PrimaryKey
    private int id;
    private String name;
    private String status;
    private double eta; //Estimate Time Arriving
    private String timeStamp;
    private double latitude;
    private double longitude;
    private double latitudeDestination;
    private double longitudeDestination;
    private boolean isActive;

    public Roboa(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, eta, timeStamp, latitude, longitude, latitudeDestination, longitudeDestination, isActive);
    }

    public boolean isActive() {
        return isActive;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public double getEta() {
        return eta;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitudeDestination() {
        return latitudeDestination;
    }

    public double getLongitudeDestination() {
        return longitudeDestination;
    }

    public LatLng getPosition()
    {
        return new LatLng(this.getLatitude(),this.getLongitude());
    }

    public LatLng getDestination()
    {
        return new LatLng(this.getLatitudeDestination(),this.getLongitudeDestination());
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEta(double eta) {
        this.eta = eta;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitudeDestination(double latitudeDestination) {
        this.latitudeDestination = latitudeDestination;
    }

    public void setLongitudeDestination(double longitudeDestination) {
        this.longitudeDestination = longitudeDestination;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
