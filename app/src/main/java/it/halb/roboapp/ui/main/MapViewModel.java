package it.halb.roboapp.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.halb.roboapp.dataLayer.RunningRegattaRepository;
import it.halb.roboapp.dataLayer.localDataSource.Boat;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.util.NavigationTarget;

public class MapViewModel extends AndroidViewModel {
    private final RunningRegattaRepository runningRegattaRepository;
    private final LiveData<List<Boat>> boats;
    private final LiveData<Regatta> regatta;

    private final MutableLiveData<NavigationTarget> navigationTarget = new MutableLiveData<>(null);

    public MapViewModel(@NonNull Application application) {
        super(application);
        runningRegattaRepository = new RunningRegattaRepository(application);
        boats = runningRegattaRepository.getBoats();
        regatta = runningRegattaRepository.getRegatta();
    }

    public LiveData<Regatta> getRegatta(){
        return regatta;
    }
    public LiveData<List<Boat>> getBoats() {
        return boats;
    }

    public void setTarget(Buoy buoy){
        navigationTarget.setValue(new NavigationTarget(buoy.getId(), true));
    }
    public void setTarget(Boat boat){
        Log.d("aaaaaaaaaaaa", "target is now" + boat.getUsername());
        navigationTarget.setValue(new NavigationTarget(boat.getUsername(), false));
    }

}
