package it.halb.roboapp.util;

import it.halb.roboapp.dataLayer.localDataSource.Boat;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;

public class NavigationTarget {
    private final String id;
    private final Class<?> targetClass;

    public NavigationTarget(Boat boat){
        this(boat.getUsername(), Boat.class);
    }
    public NavigationTarget(Buoy buoy){
        this(buoy.getId(), Buoy.class);
    }
    private NavigationTarget(String id, Class<?> targetClass) {
        this.id = id;
        this.targetClass = targetClass;
    }

    public String getId() {
        return id;
    }


    public boolean isBuoy() {
        return targetClass.equals(Buoy.class);
    }
}
