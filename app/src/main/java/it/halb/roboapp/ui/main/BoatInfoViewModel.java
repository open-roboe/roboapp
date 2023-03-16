package it.halb.roboapp.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class BoatInfoViewModel extends AndroidViewModel {

    private final MutableLiveData<Regatta> boats = new MutableLiveData<>();

    public BoatInfoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Regatta> getBoats() {
        return boats;
    }


}
