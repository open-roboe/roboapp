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

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class CreateRegattaViewModel extends AndroidViewModel {

    private MutableLiveData<String> regattaName = new MutableLiveData<>("");

    private MutableLiveData<String> courseAxis = new MutableLiveData<>("");

    private MutableLiveData<String> courseLength = new MutableLiveData<>("");

    private MutableLiveData<String> startLineLength = new MutableLiveData<>("");

    private MutableLiveData<String> stacchettoDistance = new MutableLiveData<>("");

    private MutableLiveData<String> bolinaDistance = new MutableLiveData<>("");

    private MutableLiveData<String> buoyStern = new MutableLiveData<>("");

    private MutableLiveData<String> regattaNameError = new MutableLiveData<>("");

    private MutableLiveData<String> courseAxisError = new MutableLiveData<>("");

    private MutableLiveData<String> courseLengthError = new MutableLiveData<>("");

    private MutableLiveData<String> startLineLengthError = new MutableLiveData<>("");

    private MutableLiveData<String> stacchettoDistanceError = new MutableLiveData<>("");

    private MutableLiveData<String> bolinaDistanceError = new MutableLiveData<>("");

    private MutableLiveData<String> buoySternError = new MutableLiveData<>("");

    private MutableLiveData<String> regattaType = new MutableLiveData<>("stick");

    private MutableLiveData<Boolean> enableStacchettoDistance = new MutableLiveData<>(true);

    private MutableLiveData<Boolean> enableBolinaDistance = new MutableLiveData<>(true);

    private MutableLiveData<Boolean> enableBuoyStern = new MutableLiveData<>(true);

    private final String TAG = CreateRegattaViewModel.class.getSimpleName();

    public LiveData<String> getRegattaName() {
        return regattaName;
    }

    public LiveData<String> getCourseAxis() {
        return courseAxis;
    }

    public LiveData<String> getCourseLength() {
        return courseLength;
    }

    public LiveData<String> getStartLineLength() {
        return startLineLength;
    }

    public LiveData<String> getStacchettoDistance() {
        return stacchettoDistance;
    }

    public LiveData<String> getBolinaDistance() {
        return bolinaDistance;
    }

    public LiveData<String> getBuoyStern() {
        return buoyStern;
    }

    public LiveData<String> getRegattaNameError() {
        return regattaNameError;
    }

    public LiveData<String> getCourseAxisError() {
        return courseAxisError;
    }

    public LiveData<String> getCourseLengthError() {
        return courseLengthError;
    }

    public LiveData<String> getStartLineLengthError() {
        return startLineLengthError;
    }

    public LiveData<String> getStacchettoDistanceError() {
        return stacchettoDistanceError;
    }

    public LiveData<String> getBolinaDistanceError() {
        return bolinaDistanceError;
    }

    public LiveData<String> getBuoySternError() {
        return buoySternError;
    }

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

    public void onRegattaNameTextChanged(CharSequence s, int start, int before, int count) {
        regattaName.setValue(s.toString());
        resetDisplayError(regattaNameError);
    }

    public void onCourseAxisTextChanged(CharSequence s, int start, int before, int count) {
        courseAxis.setValue(s.toString());
        resetDisplayError(courseAxisError);
    }

    public void onCourseLengthTextChanged(CharSequence s, int start, int before, int count) {
        courseLength.setValue(s.toString());
        resetDisplayError(courseLengthError);
    }

    public void onStartLineLengthTextChanged(CharSequence s, int start, int before, int count) {
        startLineLength.setValue(s.toString());
        resetDisplayError(startLineLengthError);
    }

    public void onStacchettoDistanceTextChanged(CharSequence s, int start, int before, int count) {
        stacchettoDistance.setValue(s.toString());
        resetDisplayError(stacchettoDistanceError);
    }

    public void onBolinaDistanceTextChanged(CharSequence s, int start, int before, int count) {
        bolinaDistance.setValue(s.toString());
        resetDisplayError(bolinaDistanceError);
    }

    public void onBuoySternTextChanged(CharSequence s, int start, int before, int count) {
        buoyStern.setValue(s.toString());
        resetDisplayError(buoySternError);
    }

    public void onRegattaTypeChanged(int index) {
        if (index == 0) {
            regattaType.setValue("stick");
            enableBuoyStern.setValue(true);
        }
        else {
            regattaType.setValue("triangle");
            enableBuoyStern.setValue(false);
        }
    }

    public void setStacchetto() {
        if (enableStacchettoDistance.getValue() == false) {
            enableStacchettoDistance.setValue(true);
        } else {
            enableStacchettoDistance.setValue(false);
        }
    }

    public void setBolina() {
        if (enableBolinaDistance.getValue() == false) {
            enableBolinaDistance.setValue(true);
        } else {
            enableBolinaDistance.setValue(false);
        }
    }

    public Boolean[] getBuoySternInfo() {
        Log.d(TAG, "getBuoySternInfo: " + buoyStern.getValue());
        Boolean[] buoySternInfo = new Boolean[2];
        if (enableBuoyStern.getValue()== false || buoyStern.getValue().equals("")) {
            buoySternInfo[0] = false;
            buoySternInfo[1] = false;
        }
        else {
            buoySternInfo[0] = !(buoyStern.getValue().equals("None"));
            buoySternInfo[1] = buoyStern.getValue().equals("Gate");
        }
        return buoySternInfo;
    }

    public Double[] getOptionalDistances() {
        Double[] optionalDistances = new Double[2];
        if (stacchettoDistance.getValue().equals("")) {
            optionalDistances[0] = 0.0;
        }
        else {
            optionalDistances[0] = Double.parseDouble(stacchettoDistance.getValue());
        }

        if (bolinaDistance.getValue().equals("")) {
            optionalDistances[1] = 0.0;
        }
        else {
            optionalDistances[1] = Double.parseDouble(bolinaDistance.getValue());
        }
        return optionalDistances;
    }

    public void resetDisplayError(MutableLiveData<String> field) {
        field.setValue("");
    }

    public boolean validateForm() {
        boolean isValid = true;
        if (regattaName.getValue().equals("")) {
            regattaNameError.setValue(getApplication().getString(R.string.textfield_missing_field_error));
            isValid = false;
        }
        if (courseAxis.getValue().equals("")) {
            courseAxisError.setValue(getApplication().getString(R.string.textfield_missing_field_error));
            isValid = false;
        } else if (Double.parseDouble(courseAxis.getValue()) <= 0) {
            courseAxisError.setValue(getApplication().getString(R.string.textfield_invalid_field_error));
            isValid = false;
        }
        if (courseLength.getValue().equals("")) {
            courseLengthError.setValue(getApplication().getString(R.string.textfield_missing_field_error));
            isValid = false;
        } else if (Double.parseDouble(courseLength.getValue()) <= 0) {
            courseLengthError.setValue(getApplication().getString(R.string.textfield_invalid_field_error));
            isValid = false;
        }
        if (startLineLength.getValue().equals("")) {
            startLineLengthError.setValue(getApplication().getString(R.string.textfield_missing_field_error));
            isValid = false;
        }
        else if (Double.parseDouble(startLineLength.getValue()) <= 0) {
            startLineLengthError.setValue(getApplication().getString(R.string.textfield_invalid_field_error));
            isValid = false;
        }
        if (enableStacchettoDistance.getValue() == true && stacchettoDistance.getValue().equals("")) {
            stacchettoDistanceError.setValue(getApplication().getString(R.string.textfield_missing_field_error));
            isValid = false;
        } else if ( enableStacchettoDistance.getValue() == true && Double.parseDouble(stacchettoDistance.getValue()) <= 0) {
            stacchettoDistanceError.setValue(getApplication().getString(R.string.textfield_invalid_field_error));
            isValid = false;
        }
        if (enableBolinaDistance.getValue() == true && bolinaDistance.getValue().equals("")) {
            bolinaDistanceError.setValue(getApplication().getString(R.string.textfield_missing_field_error));
            isValid = false;
        }
        else if (enableBolinaDistance.getValue() == true && Double.parseDouble(bolinaDistance.getValue()) <= 0) {
            bolinaDistanceError.setValue(getApplication().getString(R.string.textfield_invalid_field_error));
            isValid = false;
        }
        if (enableBuoyStern.getValue() == true && buoyStern.getValue().equals("")) {
            buoySternError.setValue(getApplication().getString(R.string.textfield_missing_field_error));
            isValid = false;
        }
        return isValid;
    }
    public void createRegatta() {
        if (validateForm()) {
            Boolean[] buoySternInfo = getBuoySternInfo();
            Double[] optionalDistances = getOptionalDistances();

            Log.d(TAG, "createRegatta: " + buoySternInfo[0] + " " + buoySternInfo[1]);

            Regatta regatta = new Regatta(regattaName.getValue(), regattaType.getValue(), (int) (new Date().getTime()), Integer.parseInt(courseAxis.getValue()), Double.parseDouble(startLineLength.getValue()), optionalDistances[0], Double.parseDouble(courseLength.getValue())*1000, optionalDistances[1], buoySternInfo[0], buoySternInfo[1], 0, 0);
            Log.d(TAG, "" + regatta.toString());
        }
    }
}
