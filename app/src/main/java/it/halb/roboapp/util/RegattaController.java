package it.halb.roboapp.util;

import android.graphics.Color;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class RegattaController {

    private static RegattaController instance;
    private LiveData<Regatta> regatta;
    private LiveData<List<Buoy>> buoys;
    private GoogleMap map;

    private RegattaController() {
        regatta = new MutableLiveData<>();
        buoys = new MutableLiveData<>();
    }

    public RegattaController(LiveData<Regatta> regatta, LiveData<List<Buoy>> buoys, GoogleMap map) {
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
        //cambia l'immagine del marker con il file speedboat_yellow1.png
        map.addMarker(new MarkerOptions().position(regatta.getValue().getPosition())
                .title("Jury")
                .snippet("id:" + regatta.getValue().getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.speedboat_yellow3)).anchor(0.5f, 0.5f)).setRotation(regatta.getValue().getWindDirection());

        List list = buoys.getValue();
        for (int i = 0; i < list.size(); i++) {
            Buoy buoy = (Buoy) list.get(i);
            if (buoy == null || buoy.getId().equals(Constants.MidLineStart)) {

            } else {
                if(buoy.getId().equals(Constants.BottomMark) && regatta.getValue().isGate() == true)
                {

                }else
                {
                    map.addMarker(new MarkerOptions()
                            .position(buoy.getPosition())
                            .title(buoy.getId()));
                }
            }
        }
        buildLine();
    }

    private void buildLine() {

        List list = buoys.getValue();

        Buoy StartBuoy = BuoyFactory.buoyFinder(list, Constants.StartMark);
        Buoy upBuoy = BuoyFactory.buoyFinder(list, Constants.UpMark);
        Buoy midLineBuoy = BuoyFactory.buoyFinder(list, Constants.MidLineStart);

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
            Buoy dx = BuoyFactory.buoyFinder(list, Constants.GateMarkDx);
            Buoy sx = BuoyFactory.buoyFinder(list, Constants.GateMarkSx);
            opt = new PolylineOptions().add(dx.getPosition(), sx.getPosition()).width(2f).color(Color.GRAY);
            map.addPolyline(opt);
        }

        if(BuoyFactory.buoyFinder(list, Constants.TriangleMark) != null) {
            Buoy triangle = BuoyFactory.buoyFinder(list, Constants.TriangleMark);
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
