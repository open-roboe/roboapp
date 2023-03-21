package it.halb.roboapp.ui.main;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.halb.roboapp.dataLayer.RunningRegattaRepository;
import it.halb.roboapp.dataLayer.localDataSource.Boat;


public class BoatInfoViewModel extends AndroidViewModel {

    //private final LiveData<List<Boat>> boats;
    private final LiveData<List<Boat>> boats = new MutableLiveData<List<Boat>>() {};

    private final RunningRegattaRepository runningRegattaRepository;

    public BoatInfoViewModel(@NonNull Application application) {
        super(application);
        runningRegattaRepository = new RunningRegattaRepository(application);
        //boats = runningRegattaRepository.getAllBoats();
    }

    public LiveData<List<Boat>> getBoats() {
        return boats;
    }


}
