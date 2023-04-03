package it.halb.roboapp.util;

public class NavigationTarget {
    private String id;
    private boolean isBuoy;

    public NavigationTarget(String id, boolean isBuoy) {
        this.id = id;
        this.isBuoy = isBuoy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isBuoy() {
        return isBuoy;
    }

    public void setBuoy(boolean buoy) {
        isBuoy = buoy;
    }
}
