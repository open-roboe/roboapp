package it.halb.roboapp.ui.main;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import it.halb.roboapp.dataLayer.localDataSource.Boat;


public class BoatInfoViewModel extends AndroidViewModel {

    private final LiveData<List<Boat>> boats = new LiveData<List<Boat>>() {};

    public BoatInfoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Boat>> getBoats() {
        return boats;
    }


}
