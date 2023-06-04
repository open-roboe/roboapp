package it.halb.roboapp.ui.main;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.ErrorCallback;
import it.halb.roboapp.dataLayer.RegattaRepository;
import it.halb.roboapp.dataLayer.SuccessCallback;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.util.BuoyFactory;
import it.halb.roboapp.util.Constants;

public class EditRegattaViewModel extends AndroidViewModel {

    public final String REGATTA_NAME = "regattaName";

    public final String COURSE_AXIS = "courseAxis";

    public final String COURSE_LENGTH = "courseLength";

    public final String START_LINE_LENGTH = "startLineLength";

    public final String STACCHETTO_DISTANCE = "stacchettoDistance";

    public final String BOLINA_DISTANCE = "bolinaDistance";

    public final String BUOY_STERN = "buoyStern";

    private MutableLiveData<Double> currentLat = new MutableLiveData<>(0.0);
    private MutableLiveData<Double> currentLon = new MutableLiveData<>(0.0);
    private MutableLiveData<Boolean> locationPermissions = new MutableLiveData<>(false);

    private final RegattaRepository regattaRepository;

    private MutableLiveData<HashMap<String, MutableLiveData<String>>> formFields = new MutableLiveData<>(new HashMap<String, MutableLiveData<String>>());

    private MutableLiveData<HashMap<String, MutableLiveData<String>>> formFieldsErrors = new MutableLiveData<>(new HashMap<String, MutableLiveData<String>>());

    private MutableLiveData<String> buoyStern = new MutableLiveData<>("");

    private MutableLiveData<String> regattaType = new MutableLiveData<>(Constants.stickRegatta);

    private MutableLiveData<Boolean> enableStacchettoDistance = new MutableLiveData<>(true);

    private MutableLiveData<Boolean> enableBolinaDistance = new MutableLiveData<>(true);

    private MutableLiveData<Boolean> enableBuoyStern = new MutableLiveData<>(true);

    private final String TAG = CreateRegattaViewModel.class.getSimpleName();

    private boolean formValid = true;

    public void setCurrentLocation(double lat, double lon){
        currentLat.setValue(lat);
        currentLon.setValue(lon);
    }

    public LiveData<Double> getCurrentLat(){
        return currentLat;
    }
    public LiveData<Double> getCurrentLon(){
        return currentLon;
    }

    public LiveData<Boolean> hasLocationPermissions(){
        return locationPermissions;
    }

    public void setLocationPermissions(boolean permissions){
        locationPermissions.setValue(permissions);
    }

    public boolean isFormValid() {
        return formValid;
    }

    public void setFormValid(boolean formValid) {
        this.formValid = formValid;
    }

    public LiveData<HashMap<String, MutableLiveData<String>>> getFormFields(Bundle bundle) {
        populateHashMap(bundle);
        return formFields;
    }

    public LiveData<HashMap<String, MutableLiveData<String>>> getFormFieldsErrors() {
        return formFieldsErrors;
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

    public EditRegattaViewModel(@NonNull Application application) {
        super(application);

        regattaRepository = new RegattaRepository(application);

        populateHashMaps("");
        populateHashMaps("Error");
    }

    private void populateHashMaps(String suffix) {
        HashMap<String, MutableLiveData<String>> map = new HashMap<>();
        if (suffix.equals("")) {
            map = formFields.getValue();
        }
        else {
            map = formFieldsErrors.getValue();
        }

        map.put("regattaName" + suffix, new MutableLiveData<String>(""));
        map.put("courseAxis" + suffix, new MutableLiveData<String>(""));
        map.put("courseLength" + suffix, new MutableLiveData<String>(""));
        map.put("startLineLength" + suffix, new MutableLiveData<String>(""));
        map.put("stacchettoDistance" + suffix, new MutableLiveData<String>(""));
        map.put("bolinaDistance" + suffix, new MutableLiveData<String>(""));
        map.put("buoyStern" + suffix, new MutableLiveData<String>(""));

        if (suffix.equals("")) {
            formFields.setValue(map);
        }
        else {
            formFieldsErrors.setValue(map);
        }
    }

    private void populateHashMap(Bundle bundle) {
        HashMap<String, MutableLiveData<String>> map = formFields.getValue();
        map.get("regattaName").setValue(bundle.getString("name"));
        /*map.put("courseAxis", new MutableLiveData<String>(""));
        map.put("courseLength", new MutableLiveData<String>(""));
        map.put("startLineLength", new MutableLiveData<String>(""));
        map.put("stacchettoDistance", new MutableLiveData<String>(""));
        map.put("bolinaDistance", new MutableLiveData<String>(""));
        map.put("buoyStern", new MutableLiveData<String>(""));*/
    }

    public void onRegattaNameTextChanged(CharSequence s, int start, int before, int count) {
        formFields.getValue().get("regattaName").setValue(s.toString());
        resetDisplayError(formFieldsErrors.getValue().get("regattaNameError"));
    }

    public void onCourseAxisTextChanged(CharSequence s, int start, int before, int count) {
        formFields.getValue().get("courseAxis").setValue(s.toString());
        resetDisplayError(formFieldsErrors.getValue().get("courseAxisError"));
    }

    public void onCourseLengthTextChanged(CharSequence s, int start, int before, int count) {
        formFields.getValue().get("courseLength").setValue(s.toString());
        resetDisplayError(formFieldsErrors.getValue().get("courseLengthError"));
    }

    public void onStartLineLengthTextChanged(CharSequence s, int start, int before, int count) {
        formFields.getValue().get("startLineLength").setValue(s.toString());
        resetDisplayError(formFieldsErrors.getValue().get("startLineLengthError"));
    }

    public void onStacchettoDistanceTextChanged(CharSequence s, int start, int before, int count) {
        formFields.getValue().get("stacchettoDistance").setValue(s.toString());
        resetDisplayError(formFieldsErrors.getValue().get("stacchettoDistanceError"));
    }

    public void onBolinaDistanceTextChanged(CharSequence s, int start, int before, int count) {
        formFields.getValue().get("bolinaDistance").setValue(s.toString());
        resetDisplayError(formFieldsErrors.getValue().get("bolinaDistanceError"));
    }

    public void onBuoySternTextChanged(CharSequence s, int start, int before, int count) {
        formFields.getValue().get("buoyStern").setValue(s.toString());
        resetDisplayError(formFieldsErrors.getValue().get("buoySternError"));
    }

    public void onRegattaTypeChanged(int index) {
        if (index == 0) {
            regattaType.setValue(Constants.stickRegatta);
            enableBuoyStern.setValue(true);
        }
        else {
            regattaType.setValue(Constants.triangleRegatta);
            enableBuoyStern.setValue(false);
            resetDisplayError(formFieldsErrors.getValue().get("buoySternError"));
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
        Log.d(TAG, "getBuoySternInfo: " + formFields.getValue().get("buoyStern").getValue());
        Boolean[] buoySternInfo = new Boolean[2];
        if (enableBuoyStern.getValue()== false || formFields.getValue().get("buoyStern").getValue().equals("")) {
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
        if (formFields.getValue().get("stacchettoDistance").getValue().equals("")) {
            optionalDistances[0] = 0.0;
        }
        else {
            optionalDistances[0] = Double.parseDouble(formFields.getValue().get("stacchettoDistance").getValue());
        }

        if (formFields.getValue().get("bolinaDistance").getValue().equals("")) {
            optionalDistances[1] = 0.0;
        }
        else {
            optionalDistances[1] = Double.parseDouble(formFields.getValue().get("bolinaDistance").getValue());
        }
        return optionalDistances;
    }

    public void resetDisplayError(MutableLiveData<String> field) {
        field.setValue("");
    }

    public void validateForm() {
        HashMap<String, MutableLiveData<String>> mapFormFields = formFields.getValue();
        HashMap<String, MutableLiveData<String>> mapFormFieldsErrors = formFieldsErrors.getValue();
        setFormValid(true);

        mapFormFields.forEach((k, v) -> {
            if (k.equals("regattaName")) {
                if (v.getValue().equals("")) {
                    mapFormFieldsErrors.get(k + "Error").setValue(getApplication().getString(R.string.textfield_missing_field_error));
                    setFormValid(false);
                }
            } else if (k.equals("stacchettoDistance")) {
                if (enableStacchettoDistance.getValue() == true && v.getValue().equals("")) {
                    mapFormFieldsErrors.get(k + "Error").setValue(getApplication().getString(R.string.textfield_missing_field_error));
                    setFormValid(false);
                } else if (enableStacchettoDistance.getValue() == true && Double.parseDouble(v.getValue()) <= 0) {
                    mapFormFieldsErrors.get(k + "Error").setValue(getApplication().getString(R.string.textfield_invalid_field_error));
                    setFormValid(false);
                }
            } else if (k.equals("bolinaDistance")) {
                if (enableBolinaDistance.getValue() == true && v.getValue().equals("")) {
                    mapFormFieldsErrors.get(k + "Error").setValue(getApplication().getString(R.string.textfield_missing_field_error));
                    setFormValid(false);
                } else if (enableBolinaDistance.getValue() == true && Double.parseDouble(v.getValue()) <= 0) {
                    mapFormFieldsErrors.get(k + "Error").setValue(getApplication().getString(R.string.textfield_invalid_field_error));
                    setFormValid(false);
                }
            }else if (k.equals("buoyStern")) {
                if (enableBuoyStern.getValue() == true && v.getValue().equals("")) {
                    mapFormFieldsErrors.get(k + "Error").setValue(getApplication().getString(R.string.textfield_missing_field_error));
                    setFormValid(false);
                }
            } else {
                if (v.getValue().equals("")) {
                    mapFormFieldsErrors.get(k + "Error").setValue(getApplication().getString(R.string.textfield_missing_field_error));
                    setFormValid(false);
                } else if (Double.parseDouble(v.getValue()) <= 0) {
                    mapFormFieldsErrors.get(k + "Error").setValue(getApplication().getString(R.string.textfield_invalid_field_error));
                    setFormValid(false);
                }
            }
        });
    }

    public void createRegatta(SuccessCallback<String> success, ErrorCallback error) {
        validateForm();
        Boolean[] buoySternInfo = getBuoySternInfo();
        Double[] optionalDistances = getOptionalDistances();
        if(isFormValid()) {

            //create regatta object
            Log.d(TAG, "createRegatta: " + buoySternInfo[0] + " " + buoySternInfo[1]);
            Regatta regatta = new Regatta(
                    formFields.getValue().get("regattaName").getValue(),
                    regattaType.getValue(),
                    (int) (new Date().getTime()),
                    Integer.parseInt(formFields.getValue().get("courseAxis").getValue()),
                    Double.parseDouble(formFields.getValue().get("startLineLength").getValue()),
                    optionalDistances[0],
                    Double.parseDouble(formFields.getValue().get("courseLength").getValue()) * 1000,
                    optionalDistances[1],
                    buoySternInfo[0],
                    buoySternInfo[1],
                    currentLat.getValue(),
                    currentLon.getValue()
            );
            Log.d(TAG, "" + regatta.toString());

            //create buoys objects
            List<Buoy> buoys = BuoyFactory.buildCourse(regatta);

            //repository call to create the regatta
            regattaRepository.insertRegatta(
                    regatta,
                    buoys,
                    v -> success.success(regatta.getName()),
                    error
            );

        }
    }
}
