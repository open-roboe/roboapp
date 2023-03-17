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

import java.util.Date;

import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class CreateRegattaViewModel extends AndroidViewModel {

    private MutableLiveData<String> regattaType = new MutableLiveData<>("stick");

    private MutableLiveData<Boolean> isStacchettoChecked = new MutableLiveData<>(true);

    private MutableLiveData<String> stacchettoDistance = new MutableLiveData<>("");

    private MutableLiveData<Boolean> enableStacchettoDistance = new MutableLiveData<>(true);

    private MutableLiveData<Boolean> isBolinaChecked = new MutableLiveData<>(true);

    private MutableLiveData<String> bolinaDistance = new MutableLiveData<>("");

    private MutableLiveData<Boolean> enableBolinaDistance = new MutableLiveData<>(true);

    private MutableLiveData<Boolean> enableBuoyStern = new MutableLiveData<>(true);

    private final String TAG = CreateRegattaViewModel.class.getSimpleName();

    public LiveData<Boolean> getEnableStacchettoDistance() {
        return enableStacchettoDistance;
    }

    public LiveData<String> getStacchettoDistance() {
        return stacchettoDistance;
    }

    public LiveData<Boolean> getEnableBolinaDistance() {
        return enableBolinaDistance;
    }

    public LiveData<String> getBolinaDistance() {
        return bolinaDistance;
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
            onStacchettoDistanceValueChanged("0", 0, 0, 0);
        }
        Log.d(TAG, "setStacchettoNew: " + isStacchettoChecked.getValue());
    }

    public void onStacchettoDistanceValueChanged(CharSequence s, int start, int before, int count) {
        stacchettoDistance.setValue(s.toString());
    }

    public void setBolina() {
        Log.d(TAG, "setBolinaOld: " + isBolinaChecked.getValue());
        if (isBolinaChecked.getValue() == false) {
            isBolinaChecked.setValue(true);
            enableBolinaDistance.setValue(true);
        } else {
            isBolinaChecked.setValue(false);
            enableBolinaDistance.setValue(false);
            onBolinaDistanceValueChanged("0", 0, 0, 0);
        }
        Log.d(TAG, "setBolinaNew: " + isBolinaChecked.getValue());
    }

    public void onBolinaDistanceValueChanged(CharSequence s, int start, int before, int count) {
        bolinaDistance.setValue(s.toString());
    }

    public void createRegatta(String name, int courseAxis, double courseLength, double startLineLength) {
        if (enableBuoyStern.getValue()== false) {
            boolean bottonBuoy = false;
            boolean gate = false;
        }
        //Regatta regatta = new Regatta(name, regattaType.getValue(), (int)(new Date().getTime()), courseAxis, startLineLength, stacchettoDistance.getValue(), courseLength, bolinaDistance.getValue(), 0, 0);
    }
}
