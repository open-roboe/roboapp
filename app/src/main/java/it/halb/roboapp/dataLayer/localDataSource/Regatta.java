package it.halb.roboapp.dataLayer.localDataSource;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "regatta_table")
public class Regatta {
    @PrimaryKey
    @NonNull
    private String name;
    private String type;
    private int creationDate;

    private double windDirection;

    private double startLineLen;

    private double breakDistance;

    private double courseLength;

    private double secondMarkDistance;

    private boolean bottonBuoy;

    private boolean gate;

    private double longitude;

    private double latitude;


    public Regatta(@NonNull String name, String type, int creationDate, double windDirection, double startLineLen, double breakDistance, double courseLength, double secondMarkDistance, boolean bottonBuoy, boolean gate) {
        this.name = name;
        this.type = type;
        this.creationDate = creationDate;
        this.windDirection = windDirection;
        this.startLineLen = startLineLen;
        this.breakDistance = breakDistance;
        this.courseLength = courseLength;
        this.secondMarkDistance = secondMarkDistance;
        this.bottonBuoy = bottonBuoy;
        this.gate = gate;
    }

    @Ignore
    public Regatta(@NonNull String name, String type, int creationDate) {
        this.name = name;
        this.type = type;
        this.creationDate = creationDate;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCreationDate() {
        return creationDate;
    }


    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public double getStartLineLen() {
        return startLineLen;
    }

    public void setStartLineLen(double startLineLen) {
        this.startLineLen = startLineLen;
    }

    public double getBreakDistance() {
        return breakDistance;
    }

    public void setBreakDistance(double breakDistance) {
        this.breakDistance = breakDistance;
    }

    public double getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(double courseLength) {
        this.courseLength = courseLength;
    }

    public double getSecondMarkDistance() {
        return secondMarkDistance;
    }

    public void setSecondMarkDistance(double secondMarkDistance) {
        this.secondMarkDistance = secondMarkDistance;
    }

    public boolean isBottonBuoy() {
        return bottonBuoy;
    }

    public void setBottonBuoy(boolean bottonBuoy) {
        this.bottonBuoy = bottonBuoy;
    }

    public boolean isGate() {
        return gate;
    }

    public void setGate(boolean gate) {
        this.gate = gate;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


}