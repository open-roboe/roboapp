package it.halb.roboapp.ui.main;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import it.halb.roboapp.dataLayer.RunningRegattaRepository;

public class LoadViewModel extends AndroidViewModel {
    private final RunningRegattaRepository runningRegatta;

    public LoadViewModel(@NonNull Application application) {
        super(application);
        runningRegatta = new RunningRegattaRepository(application);
    }

    public void declareRegattaToRun(@NonNull String name){
        runningRegatta.declareRegattaToRun(name);
    }
}
