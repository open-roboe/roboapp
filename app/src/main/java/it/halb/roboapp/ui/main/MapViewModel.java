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
        //a.setUsername("aaaaaaaaa");
        //b.setUsername("bbbbbbbbb");
        //c.setUsername("ccccccccc");
        ArrayList<Boat> list = new ArrayList<Boat>();
        list.add(a);
        list.add(b);
        list.add(c);
        boats.setValue(list);

    }

}
