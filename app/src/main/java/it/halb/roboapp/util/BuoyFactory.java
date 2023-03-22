package it.halb.roboapp.util;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class BuoyFactory {

    private static Regatta regatta;
    private static List<Buoy> buoys;
    private static LatLng mid;
    private static int left;

    public static List<Buoy> buildCourse(Regatta race)
    {
        regatta = race;
        left = windDirection(race.getWindDirection(), -90);
        buoys = new ArrayList<Buoy>();
        standardBuoys();
        switch (regatta.getType())
        {
            case Constants.stickRegatta:
                regattaStick();
                return buoys;
            case Constants.triangleRegatta:
                regattaTriangle();
                return buoys;
            default:
                return null;
        }
    }

    //imposta boe di: partenza, meta linea di partenza(che è solo un punto util),boa di bolina,
    // eventaulmente boe di seconda bolina e di stacchetto
    private static void standardBuoys()
    {
        Buoy midLine = newBuoy(Constants.MidLineStart, regatta.getName(), regatta.getPosition(),
                regatta.getStartLineLen()/2, left
        );

        buoys.add(midLine);
        mid = midLine.getPosition();

        Buoy upWindMark = newBuoy(Constants.UpMark, regatta.getName(), mid, regatta.getCourseLength(),
                regatta.getWindDirection());
        buoys.add(upWindMark);
        Buoy startMark = newBuoy(Constants.StartMark, regatta.getName(), regatta.getPosition(), regatta.getStartLineLen(),
                left);

        buoys.add(startMark);

        if (regatta.getSecondMarkDistance() != 0) {
            Buoy secondUpMark = newBuoy(Constants.SecondUpMark, regatta.getName(), mid, regatta.getSecondMarkDistance(),
                    regatta.getWindDirection());
            buoys.add(secondUpMark);
        }

        if (regatta.getBreakDistance() != 0) {
            Buoy breakMark = newBuoy(Constants.BreakMark, regatta.getName(), upWindMark.getPosition(), regatta.getBreakDistance(),
                    left);
            buoys.add(breakMark);
        }
    }
    private static void regattaStick() {

        if (regatta.isBottonBuoy()) {

            Buoy bottonMark = newBuoy(Constants.BottomMark, regatta.getName(), mid,
                    regatta.getCourseLength() / 10, regatta.getWindDirection());

            buoys.add(bottonMark);
            if (regatta.isGate()) {

                Buoy gateMarkSx = newBuoy(Constants.GateMarkSx, regatta.getName(), bottonMark.getPosition(),
                        regatta.getStartLineLen() / 4, windDirection(regatta.getWindDirection(), -90));
                buoys.add(gateMarkSx);

                Buoy gateMarkDx = newBuoy(Constants.GateMarkDx, regatta.getName(), bottonMark.getPosition(),
                        regatta.getStartLineLen() / 4, windDirection(regatta.getWindDirection(), 90));
                buoys.add(gateMarkDx);
            }
        }
    }

    //distanze tutte in Km
    private static void regattaTriangle() {

        //questi due 45 dovranno essere variabili
        int degreeTriangle = windDirection(regatta.getWindDirection(), -135);

        Buoy triangleBuoy = newBuoy(Constants.TriangleMark, regatta.getName(),
                buoyFinder(buoys,Constants.UpMark).getPosition(),
                regatta.getCourseLength() * Math.cos(Math.toRadians(45)), degreeTriangle);

        buoys.add(triangleBuoy);
    }

    private static Buoy newBuoy(String id, String raceID, LatLng startLocation, double distance, int degree) {

        double lon1 = startLocation.longitude;
        double lat1 = startLocation.latitude;
        double azimuth = Math.toRadians(degree);
        double R = 6371000; // km

        double lat2 = lat1 + Math.toDegrees(distance / R) * Math.cos(azimuth);
        double lon2 = lon1 + Math.toDegrees(distance / R) * Math.sin(azimuth);

        return new Buoy(id, raceID, lat2, lon2);
    }

    private static int windDirection(int degree, int difference) {

        int newDegree = (degree + difference);

        if (newDegree > 359) {
            newDegree = (newDegree - 360);
        } else if (newDegree < 0) {
            newDegree =  (newDegree + 360);
        }
        return newDegree;
    }

    public static Buoy buoyFinder(List<Buoy> list, String id)
    {
        for (Buoy b : list) {
            if(b.getId().equals(id))
            {
                return b;
            }
        }
        return null;
    }
}
