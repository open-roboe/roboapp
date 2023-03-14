package it.halb.roboapp.util;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class BuoyFactory {

    private static Regatta race;
    private static List<Buoy> buoys;
    private static LatLng mid;

    public static List<Buoy> regattaStick(Regatta race, LatLng position) {

        race.setLatLng(position);

        double left = windDirection(race.getWindDirection(), -90);

        buoys = new ArrayList<Buoy>();

        standardBuoys();

        if (race.isBottonBuoy()) {

            Buoy bottonMark = newMark(Constants.BottomMark, race.getName(), mid,
                    race.getCourseLength() / 10, race.getWindDirection());
            buoys.add(bottonMark);
            if (race.isGate()) {

                Buoy gateMarkSx = newMark(Constants.GateMarkSx, race.getName(), bottonMark.getPosition(),
                        race.getStartLineLen() / 4, windDirection(race.getWindDirection(), -90));
                buoys.add(gateMarkSx);

                Buoy gateMarkDx = newMark(Constants.GateMarkDx, race.getName(), bottonMark.getPosition(),
                        race.getStartLineLen() / 4, windDirection(race.getWindDirection(), 90));
                buoys.add(gateMarkDx);

            }
        }
        return buoys;
    }

    //distanze tutte in Km
    public static List<Buoy> regattaTriangle(Regatta race, LatLng position) {

        race.setLatLng(position);

        double left = windDirection(race.getWindDirection(), -90);

        buoys = new ArrayList<Buoy>();

        standardBuoys();

        //questi due 45 dovranno esere variabili
        int degreeTriangle = windDirection(race.getWindDirection(), -45);
        Buoy triangleBuoy = newMark(Constants.TriangleMark, race.getName(),
                buoyFinder(buoys,Constants.UpMark).getPosition(),
                race.getCourseLength() * Math.cos(Math.toRadians(45)), degreeTriangle);



       return buoys;

    }

    private static Buoy newMark(String id, String raceID, LatLng startLocation, double distance, int degree) {

        double lon1 = startLocation.longitude;
        double lat1 = startLocation.latitude;
        double azimuth = Math.toRadians(degree);
        double R = 6371;

        //chiamata al server per calcolare la lat2 e long 2
        /*x2 = x1 + d * cos(direction)
        y2 = y1 + d * sin(direction)
        */

        double lat2 = lat1 + Math.toDegrees(distance / R) * Math.cos(azimuth);
        double lon2 = lon1 + Math.toDegrees(distance / R) * Math.sin(azimuth);

        LatLng markLocation = new LatLng(lat2, lon2);

        return new Buoy(id, raceID, lat2, lon2);
    }

    private static int windDirection(int degree, int difference) {
        int newDegree = (degree + degree);

        if (newDegree > 359) {
            newDegree = (short) (newDegree - 360);
        } else if (newDegree < 0) {
            newDegree = (short) (newDegree + 360);
        }

        return newDegree;
    }

    private static void standardBuoys()
    {
        Buoy midLine = newMark(Constants.MidLineStart, race.getName(), race.getPosition(),
                race.getStartLineLen(), race.getWindDirection()
        );


        buoys.add(midLine);
        LatLng mid = midLine.getPosition();
        Buoy upWindMark = newMark(Constants.UpMark, race.getName(), mid, race.getCourseLength(),
                race.getWindDirection());
        buoys.add(upWindMark);
        Buoy startMark = newMark(Constants.StartMark, race.getName(), race.getPosition(), race.getStartLineLen(),
                race.getWindDirection());

        buoys.add(startMark);

        if (race.getSecondMarkDistance() != 0) {
            Buoy secondUpMark = newMark(Constants.SecondUpMark, race.getName(), mid, race.getSecondMarkDistance(),
                    race.getWindDirection());
            buoys.add(secondUpMark);

        }

        if (race.getBreakDistance() != 0) {
            Buoy breakMark = newMark(Constants.BreakMark, race.getName(), mid, race.getBreakDistance(),
                    windDirection(race.getWindDirection(), 90));
            buoys.add(breakMark);
        }

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
