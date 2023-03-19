package it.halb.roboapp.util;

import junit.framework.TestCase;

import java.util.List;

import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class BuoyFactoryTest extends TestCase {

    public void testBuoyFactory(){
        Regatta regatta = new Regatta("regatta_name", "stick", 0, 10, 10.1, 10.1, 10.0, 10.0, true, true);
        List<Buoy> buoys = BuoyFactory.regattaStick(regatta);
        assert ! buoys.isEmpty();
    }

    public void testRegattaStick() {

    }

    public void testRegattaTriangle() {
    }
}