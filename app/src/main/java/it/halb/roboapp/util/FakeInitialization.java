package it.halb.roboapp.util;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.ui.main.MapViewModel;
import it.halb.roboapp.ui.main.RoboaInfoFragment;

public class FakeInitialization {

    private static MapViewModel mapViewModel;
    public static boolean flag = false;
    private static Roboa fakeRoboa;
    private static Roboa fakeRoboa2;
    private static Roboa fakeRoboa3;
    private static List<Roboa> fakeRoboaList;
    private List<Buoy> buoyList;

    public FakeInitialization() {
    }

    public static List<Roboa> fakeInitialization(List<Buoy> buoyList) {


        //
        //
        //
        //fake initialization
        fakeRoboa = new Roboa(123);
        fakeRoboa.setName("Roboa1");
        fakeRoboa.setActive(true);
        fakeRoboa.setStatus("not moving");
        fakeRoboa2 = new Roboa(456);
        fakeRoboa2.setName("Roboa2");
        fakeRoboa2.setActive(false);
        fakeRoboa2.setStatus("not moving");
        fakeRoboa3 = new Roboa(789);
        fakeRoboa3.setName("Roboa3");
        fakeRoboa3.setActive(true);
        fakeRoboa3.setStatus("not moving");

        fakeRoboaList = new ArrayList<>();
        fakeRoboaList.add(fakeRoboa);
        fakeRoboaList.add(fakeRoboa2);
        fakeRoboaList.add(fakeRoboa3);
        //
        //
        //
        //

        if(buoyList != null) {
            for (Buoy buoy : buoyList) {
                Log.d("A", buoy.getId() + "");
                Log.d("B", buoy.getBindedRobuoy() + "");


                if(buoy.getBindedRobuoy() != null) {
                    if (buoy.getBindedRobuoy().equals(fakeRoboa.getId() + "")) {
                        fakeRoboa.setBindedBuoy(buoy.getId());
                    }
                    if (buoy.getBindedRobuoy().equals(fakeRoboa2.getId() + "")) {
                        fakeRoboa2.setBindedBuoy(buoy.getId());
                    }
                    if (buoy.getBindedRobuoy().equals(fakeRoboa3.getId() + "")) {
                        fakeRoboa3.setBindedBuoy(buoy.getId());
                    }
                }
            }
        }




        flag = true;
        return fakeRoboaList;
    }

    public static List<Roboa> getFakeRoboaList() {
        return fakeRoboaList;
    }

}
