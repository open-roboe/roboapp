package it.halb.roboapp.ui.main;

import android.app.Application;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class CreateRegattaViewModel extends AndroidViewModel {

    private MutableLiveData<String> regattaType = new MutableLiveData<>("stick");

    private MutableLiveData<Boolean> isStacchettoChecked = new MutableLiveData<>(true);

    private MutableLiveData<Boolean> enableStacchettoDistance = new MutableLiveData<>(true);

    private MutableLiveData<Boolean> isBolinaChecked = new MutableLiveData<>(true);

    private MutableLiveData<Boolean> enableBolinaDistance = new MutableLiveData<>(true);

    private MutableLiveData<Boolean> enableBuoyStern = new MutableLiveData<>(true);

    private final String TAG = CreateRegattaViewModel.class.getSimpleName();

    public LiveData<Boolean> getEnableStacchettoDistance() {
        return enableStacchettoDistance;
    }

    public LiveData<Boolean> getEnableBolinaDistance() {
        return enableBolinaDistance;
    }

    public LiveData<Boolean> getEnableBuoyStern() {
        return enableBuoyStern;
    }

    public CreateRegattaViewModel(@NonNull Application application) {

        super(application);
    }

    public void onRegattaTypeChanged(int a) {
        if (a == 0) {
            regattaType.setValue("stick");
            enableBuoyStern.setValue(true);
        }
        else {
            regattaType.setValue("triangle");
            enableBuoyStern.setValue(false);
        }
    }

    public void setStacchetto() {
        Log.d(TAG, "setStacchettoOld: " + isStacchettoChecked.getValue());
        if (isStacchettoChecked.getValue() == false) {
            isStacchettoChecked.setValue(true);
            enableStacchettoDistance.setValue(true);
        } else {
            isStacchettoChecked.setValue(false);
            enableStacchettoDistance.setValue(false);
        }
        Log.d(TAG, "setStacchettoNew: " + isStacchettoChecked.getValue());
    }

    public void setBolina() {
        Log.d(TAG, "setBolinaOld: " + isBolinaChecked.getValue());
        if (isBolinaChecked.getValue() == false) {
            isBolinaChecked.setValue(true);
            enableBolinaDistance.setValue(true);
        } else {
            isBolinaChecked.setValue(false);
            enableBolinaDistance.setValue(false);
        }
        Log.d(TAG, "setBolinaNew: " + isBolinaChecked.getValue());
    }
}
