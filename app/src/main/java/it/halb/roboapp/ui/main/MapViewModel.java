package it.halb.roboapp.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class MapViewModel extends AndroidViewModel {

    private final MutableLiveData<Regatta> race = new MutableLiveData<>();


    public MapViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Regatta> getRace() {
        return race;
    }

}
