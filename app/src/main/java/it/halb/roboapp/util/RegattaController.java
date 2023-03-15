package it.halb.roboapp.util;

import android.graphics.Color;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class RegattaController {

    private static RegattaController instance;
    private MutableLiveData<Regatta> regatta;
    private MutableLiveData<List<Buoy>> buoys;

    private GoogleMap map;

    private RegattaController() {
        regatta = new MutableLiveData<>();
        buoys = new MutableLiveData<>();
    }

    public RegattaController(MutableLiveData<Regatta> regatta, MutableLiveData<List<Buoy>> buoys, GoogleMap map) {
        this.regatta = regatta;
        this.buoys = buoys;
        this.map = map;
    }

    public static RegattaController getInstance() {
        if (instance == null) {
            instance = new RegattaController();
        }
        return instance;
    }

    public static RegattaController getInstance(MutableLiveData<Regatta> regatta, MutableLiveData<List<Buoy>> buoys, GoogleMap map) {
        if (instance == null) {
            instance = new RegattaController(regatta, buoys, map);
        }
        return instance;
    }

    public void setCourse() {



        Marker juryMark = map.addMarker(new MarkerOptions()
                .position(regatta.getValue().getPosition())
                .title("Jury")
                .snippet("id:" + regatta.getValue().getName()));

        List list = buoys.getValue();
        for (int i = 0; i < list.size(); i++) {
            Buoy buoy = (Buoy) list.get(i);
            if (buoy == null) {

            } else {
                map.addMarker(new MarkerOptions()
                        .position(buoy.getPosition())
                        .title(buoy.getId()));
            }

        }
        Buoy StartBuoy = (Buoy) BuoyFactory.buoyFinder(list, Constants.StartMark);
        Buoy upBuoy = (Buoy) BuoyFactory.buoyFinder(list, Constants.UpMark);
        Buoy midLineBuoy = (Buoy) BuoyFactory.buoyFinder(list, Constants.MidLineStart);
        PolylineOptions opt = new PolylineOptions().add(regatta.getValue().getPosition(), StartBuoy.getPosition()).width(3f).color(Color.GRAY);
        map.addPolyline(opt);
        opt = new PolylineOptions().add(midLineBuoy.getPosition(), upBuoy.getPosition()).width(2f).color(Color.GRAY);
        map.addPolyline(opt);


        if (BuoyFactory.buoyFinder(list, Constants.BreakMark) != null) {
            Buoy breakMark = (Buoy) BuoyFactory.buoyFinder(list, Constants.BreakMark);
            opt = new PolylineOptions().add(upBuoy.getPosition(), breakMark.getPosition()).width(2f).color(Color.GRAY);
            map.addPolyline(opt);
        }

        if (BuoyFactory.buoyFinder(list, Constants.SecondUpMark) != null) {
            Buoy secondUpMark = BuoyFactory.buoyFinder(list, Constants.SecondUpMark);

            opt = new PolylineOptions().add(upBuoy.getPosition(), secondUpMark.getPosition()).width(2f).color(Color.GRAY);
            map.addPolyline(opt);
        }

        if (BuoyFactory.buoyFinder(list, Constants.GateMarkDx) != null) {
            Buoy dx = (Buoy) BuoyFactory.buoyFinder(list, Constants.GateMarkDx);
            Buoy sx = (Buoy) BuoyFactory.buoyFinder(list, Constants.GateMarkSx);
            opt = new PolylineOptions().add(dx.getPosition(), sx.getPosition()).width(2f).color(Color.GRAY);
            map.addPolyline(opt);
        }

        if(BuoyFactory.buoyFinder(list, Constants.TriangleMark) != null) {
            Buoy triangle = (Buoy) BuoyFactory.buoyFinder(list, Constants.TriangleMark);

            opt = new PolylineOptions().add(midLineBuoy.getPosition(), triangle.getPosition()).width(2f).color(Color.GRAY);
            map.addPolyline(opt);
            opt = new PolylineOptions().add(upBuoy.getPosition(), triangle.getPosition()).width(2f).color(Color.GRAY);
            map.addPolyline(opt);
        }


    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public void setRegatta(MutableLiveData<Regatta> regatta) {
        this.regatta = regatta;
    }

    public void setBuoys(MutableLiveData<List<Buoy>> buoys) {
        this.buoys = buoys;
    }



}
