package it.halb.roboapp.dataLayer.localDataSource;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.android.gms.maps.model.LatLng;

@Entity(tableName = "Roboa_table")
public class Roboa {

    @PrimaryKey
    @NonNull
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
}
