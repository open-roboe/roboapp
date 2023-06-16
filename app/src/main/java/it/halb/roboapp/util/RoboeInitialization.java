package it.halb.roboapp.util;

import java.util.List;

import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.ui.main.MapViewModel;

public class RoboeInitialization {

    public RoboeInitialization() {
    }

    //i need to create for the firts time the robuoys
    //the other times they are taken from the database
    public static void roboeInitialization(List<Roboa> robuoyList, MapViewModel model) {
        //initialization: if there are no robuoys, i create 3 robuoys
        if (robuoyList != null) {
            if (robuoyList.size() == 0) {
                Roboa fakeRoboa = new Roboa(123);
                fakeRoboa.setName("roboa n°1");
                fakeRoboa.setActive(true);
                fakeRoboa.setStatus("not moving");

                Roboa fakeRoboa2 = new Roboa(456);
                fakeRoboa2.setName("roboa n°2");
                fakeRoboa2.setActive(false);
                fakeRoboa2.setStatus("not moving");

                Roboa fakeRoboa3 = new Roboa(789);
                fakeRoboa3.setName("roboa n°3");
                fakeRoboa3.setActive(true);
                fakeRoboa3.setStatus("not moving");

                model.insertRoboa(fakeRoboa);
                model.insertRoboa(fakeRoboa2);
                model.insertRoboa(fakeRoboa3);
            }
        }
    }
}
