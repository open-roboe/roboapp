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
import java.util.Objects;
import java.util.Random;

import it.halb.roboapp.dataLayer.RunningRegattaRepository;
import it.halb.roboapp.dataLayer.localDataSource.Boat;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.util.NavigationTarget;

public class MapViewModel extends AndroidViewModel {
    private final RunningRegattaRepository runningRegattaRepository;
    private final LiveData<List<Boat>> boats;
    private final LiveData<Regatta> regatta;

    private final LiveData<List<Buoy>> buoys;

    private final LiveData<List<Roboa>> robuoys;

    private final MutableLiveData<NavigationTarget> navigationTarget = new MutableLiveData<>(null);

    public MapViewModel(@NonNull Application application) {
        super(application);
        Log.d("VIEWMODEL_SCOPING_TEST", "constructor run");
        runningRegattaRepository = new RunningRegattaRepository(application);
        boats = runningRegattaRepository.getBoats();
        regatta = runningRegattaRepository.getRegatta();
        buoys = runningRegattaRepository.getBuoys();
        robuoys = runningRegattaRepository.getRoboas();
    }

    public LiveData<Regatta> getRegatta(){
        return regatta;
    }
    public LiveData<List<Boat>> getBoats() {
        return boats;
    }
    public LiveData<List<Buoy>> getBuoy() { return buoys; }
    public LiveData<List<Roboa>> getRoboa() { return robuoys; }

    public void setTarget(Buoy buoy){
        navigationTarget.setValue(new NavigationTarget(buoy));
    }

    public void setTarget(Boat boat){
        navigationTarget.setValue(new NavigationTarget(boat));
    }

    public void clearTarget(){
        navigationTarget.setValue(null);
    }

    /**
     * This livedata Location object updates every time the map focus should change.
     * When a boat or a buoy is clicked to set it as navigation target it focuses on its location,
     * If something goes wrong, or there is no target set, the default coordinates of 0.0 are returned.
     * When using this livedata, make sure to handle the case where the coordinates are 0.0, 0.0
     */
    public LiveData<Location> getMapFocusLocation(){
        return Transformations.map(navigationTarget, target -> {
            Location location = new Location("viewModel_transformation");
            //if there is no target set, return the default location, 0.0, 0.0
            if(target == null){
                return location;
            }
            if(target.isBuoy()){
                Objects.requireNonNull(buoys.getValue()).forEach(buoy -> {
                    if(buoy.getId().equals(target.getId())){
                        location.setLatitude(buoy.getLatitude());
                        location.setLongitude(buoy.getLongitude());
                    }
                });
            } else {
                Objects.requireNonNull(boats.getValue()).forEach(boat -> {
                    if(boat.getUsername().equals(target.getId())){
                        location.setLatitude(boat.getLatitude());
                        location.setLongitude(boat.getLongitude());
                    }
                });
            }
            return location;
        });
    }

    public LiveData<String> getNavigationTargetReadableName(){
        return Transformations.map(navigationTarget, target -> {
            if(target == null)
                return null;
            return target.getId();
        });
    }


    /**
     * TODO: calculate, by combining all the required livedata such as currentLocation and targetLocation
     *   with the sensors data, that should be put into the viewModel by MapFragment, and kept updated
     *
     * @return the orientation angle for the compass image.
     */
    public LiveData<Double> getCompassOrientation(){
        return null;
    }


}
