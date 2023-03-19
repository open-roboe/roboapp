package it.halb.roboapp.dataLayer.localDataSource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "running_status_table")
public class RunningStatus {
    private String error;

    private double lat;
    private double lon;
    private int lastUpdate;

    private String runningRegattaName;

    public RunningStatus(String runningRegattaName) {
        this.runningRegattaName = runningRegattaName;
    }

    @Nullable
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getRunningRegattaName() {
        return runningRegattaName;
    }

    public void setRunningRegattaName(String runningRegattaName) {
        this.runningRegattaName = runningRegattaName;
    }
}
