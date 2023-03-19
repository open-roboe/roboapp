package it.halb.roboapp.ui.main;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import it.halb.roboapp.dataLayer.RunningRegattaRepository;
import it.halb.roboapp.dataLayer.SuccessCallback;

public class RunRegattaViewModel extends AndroidViewModel {
    private final RunningRegattaRepository runningRegatta;

    public RunRegattaViewModel(@NonNull Application application) {
        super(application);
        runningRegatta = new RunningRegattaRepository(application);
    }

    public void declareRegattaToRun(@NonNull String name,
                                    SuccessCallback<Void> success
    ){
        runningRegatta.declareRegattaToRun(
                name,
                success,
                ((code, details) -> {
                    setErrorState();
                })
        );
    }

    public void setErrorState(){
        //TODO: update livedata, used view to display stuff
    }
}
