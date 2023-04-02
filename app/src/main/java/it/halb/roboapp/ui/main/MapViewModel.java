package it.halb.roboapp.ui.main;

import android.app.Application;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

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

    private final LiveData<List<Buoy>> buoys;

    private final MutableLiveData<NavigationTarget> navigationTarget = new MutableLiveData<>(null);

    public MapViewModel(@NonNull Application application) {
        super(application);
        runningRegattaRepository = new RunningRegattaRepository(application);
        boats = runningRegattaRepository.getBoats();
        regatta = runningRegattaRepository.getRegatta();
        buoys = runningRegattaRepository.getBuoys();
    }

    public LiveData<Regatta> getRegatta(){
        return regatta;
    }
    public LiveData<List<Boat>> getBoats() {
        return boats;
    }
    public LiveData<List<Buoy>> getBuoy() { return buoys; }

    public void setTarget(Buoy buoy){
        navigationTarget.setValue(new NavigationTarget(buoy.getId(), true));
    }

    //va bene così? NavigationTarget viene sovrascritto dal secondo setTarget
    public void setTarget(Boat boat){
        Log.d("aaaaaaaaaaaa", "target is now " + boat.getUsername());
        navigationTarget.setValue(new NavigationTarget(boat.getUsername(), false));
    }



    public LiveData<Location> getTargetLocation(){
        return Transformations.map(navigationTarget, (target) -> {
            Location location = new Location("");
            if(target == null){
                location.setLatitude(1.0);
                location.setLongitude(1.0);
                return location;
            }
            if(target.isBuoy()){
                //a.setLatitude(buoys.getValue().get(target.getId()).getLatitude());
                location.setLatitude(5.0);
                location.setLongitude(5.0);
            } else {
                boats.getValue().forEach(boat -> {
                    if(boat.getUsername().equals(target.getId())){
                        location.setLatitude(boat.getLatitude());
                        location.setLongitude(boat.getLongitude());
                    }
                });

            }
            return location;
        });
    }

}
