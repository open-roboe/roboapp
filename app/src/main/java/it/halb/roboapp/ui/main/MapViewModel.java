package it.halb.roboapp.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import it.halb.roboapp.dataLayer.RunningRegattaRepository;
import it.halb.roboapp.dataLayer.localDataSource.Boat;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class MapViewModel extends AndroidViewModel {
    private final RunningRegattaRepository runningRegattaRepository;
    private final MutableLiveData<Regatta> race = new MutableLiveData<>();
    private final MutableLiveData<List<Boat>> boats = new MutableLiveData<List<Boat>>() {};
    //private final LiveData<List<Boat>> boats;
    public MapViewModel(@NonNull Application application) {
        super(application);
        runningRegattaRepository = new RunningRegattaRepository(application);
        //boats = runningRegattaRepository.getBoats();
    }

    public MutableLiveData<Regatta> getRace() {
        return race;
    }
    public LiveData<List<Boat>> getBoats() {
        return boats;
    }








    public void fakeData(){
        Boat a = new Boat("aaaaa", "b", true,34.56, 5555.5, 8888);
        Boat b = new Boat("eeee", "b", true,78.43, 56.65, 77777);
        Boat c = new Boat("iiiii", "b", true, 54.89, 32.23, 999999);

        ArrayList<Boat> list = new ArrayList<Boat>();
        list.add(a);
        list.add(b);
        list.add(c);
        /*
        Boat d = new Boat("a", "b", true, 4.5, 0.000, 22);
        Boat e = new Boat("a", "b", false, 4.3, 0.000, 2222);
        Boat f = new Boat("a", "b", true, 1.2, 0.000, 2);


        list.add(d);
        list.add(e);
        list.add(f);
        Boat g = new Boat("a", "b", false, 0.000, 0.2222, 99);
        Boat h = new Boat("a", "b", true, 0.000, 0.2222, 55);
        Boat i = new Boat("a", "b", false, 0.000, 0.2222, 34);

        list.add(g);
        list.add(h);
        list.add(i);
        Boat l = new Boat("a", "b", true, 0.000, 0.2222, 43);
        Boat m = new Boat("a", "b", false, 0.000, 0.2222, 32);
        Boat n = new Boat("a", "b", true, 0.000, 0.2222, 21);

        list.add(l);
        list.add(m);
        list.add(n);
        Boat p = new Boat("a", "b", false, 0.000, 0.2222, 35);
        Boat q = new Boat("a", "b", true, 0.000, 0.2222, 32);
        Boat r = new Boat("a", "b", false, 0.000, 0.2222, 21);


        list.add(p);
        list.add(q);
        list.add(r);
         */


        boats.setValue(list);

    }

}
