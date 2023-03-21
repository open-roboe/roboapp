package it.halb.roboapp.util;

import junit.framework.TestCase;

import java.util.List;

import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class BuoyFactoryTest extends TestCase {

    public void testBuoyFactory(){

    }

    public void testRegattaStick() {
        Regatta regatta = new Regatta("regatta_name", "stick", 0, 10, 100.1, 10.1, 1000.0, 10.0, true, true);
        List<Buoy> buoys = BuoyFactory.regattaStick(regatta);
        assert ! buoys.isEmpty();

    }

    public void testRegattaTriangle() {
        Regatta regatta = new Regatta("regatta_name", "triangle", 0, 10, 100.1, 0, 1000.0, 10.0, false, false);
        List<Buoy> buoys = BuoyFactory.regattaTriangle(regatta);
        assert ! buoys.isEmpty();

    }
}