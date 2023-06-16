package it.halb.roboapp.util;

import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class EditingRegatta {

    private static String name;
    private static String type;
    private static int creationDate;
    private static int windDirection;
    private static double startLineLen;
    private static double breakDistance;
    private static double courseLength;
    private static double secondMarkDistance;
    private static boolean bottonBuoy;
    private static boolean gate;
    private static double longitude;
    private static double latitude;

    private static EditingRegatta regatta;

    private EditingRegatta() {
    }

    public static EditingRegatta  getInstance() {
        if (regatta == null) {
            regatta = new EditingRegatta();
        }
        return regatta;
    }

    public static void setName(String name) {
        EditingRegatta.name = name;
    }

    public static String getName() {
        return name;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        EditingRegatta.type = type;
    }

    public static int getCreationDate() {
        return creationDate;
    }

    public static void setCreationDate(int creationDate) {
        EditingRegatta.creationDate = creationDate;
    }

    public static int getWindDirection() {
        return windDirection;
    }

    public static void setWindDirection(int windDirection) {
        EditingRegatta.windDirection = windDirection;
    }

    public static double getStartLineLen() {
        return startLineLen;
    }

    public static void setStartLineLen(double startLineLen) {
        EditingRegatta.startLineLen = startLineLen;
    }

    public static double getBreakDistance() {
        return breakDistance;
    }

    public static void setBreakDistance(double breakDistance) {
        EditingRegatta.breakDistance = breakDistance;
    }

    public static double getCourseLength() {
        return courseLength;
    }

    public static void setCourseLength(double courseLength) {
        EditingRegatta.courseLength = courseLength;
    }

    public static double getSecondMarkDistance() {
        return secondMarkDistance;
    }

    public static void setSecondMarkDistance(double secondMarkDistance) {
        EditingRegatta.secondMarkDistance = secondMarkDistance;
    }

    public static boolean isBottonBuoy() {
        return bottonBuoy;
    }

    public static void setBottonBuoy(boolean bottonBuoy) {
        EditingRegatta.bottonBuoy = bottonBuoy;
    }

    public static boolean isGate() {
        return gate;
    }

    public static void setGate(boolean gate) {
        EditingRegatta.gate = gate;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        EditingRegatta.longitude = longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        EditingRegatta.latitude = latitude;
    }


}

