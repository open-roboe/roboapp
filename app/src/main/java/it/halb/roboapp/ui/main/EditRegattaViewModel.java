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
import java.util.Set;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.ErrorCallback;
import it.halb.roboapp.dataLayer.RegattaRepository;
import it.halb.roboapp.dataLayer.SuccessCallback;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.util.BuoyFactory;
import it.halb.roboapp.util.Constants;
import it.halb.roboapp.util.EditingRegatta;

public class EditRegattaViewModel extends AndroidViewModel {

    private MutableLiveData<Double> currentLat = new MutableLiveData<>(0.0);
    private MutableLiveData<Double> currentLon = new MutableLiveData<>(0.0);
    private MutableLiveData<Boolean> locationPermissions = new MutableLiveData<>(false);

    private final RegattaRepository regattaRepository;

    private MutableLiveData<HashMap<String, MutableLiveData<String>>> formFields = new MutableLiveData<>(new HashMap<String, MutableLiveData<String>>());

    private MutableLiveData<HashMap<String, MutableLiveData<String>>> formFieldsErrors = new MutableLiveData<>(new HashMap<String, MutableLiveData<String>>());

    private MutableLiveData<Bundle> bundle  = new MutableLiveData<>(null);

    private MutableLiveData<String> regattaType = new MutableLiveData<>(Constants.stickRegatta);

    private MutableLiveData<Boolean> checkStacchettoButton = new MutableLiveData<>(true);

    private MutableLiveData<Boolean> enableStacchettoDistance = new MutableLiveData<>(true);

    private MutableLiveData<Boolean> checkBolinaButton = new MutableLiveData<>(true);

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

    public void setBundle(Bundle bundle) {
        this.bundle.setValue(bundle);
        populateHashMaps();
    }

    public LiveData<HashMap<String, MutableLiveData<String>>> getFormFields() {
        return formFields;
    }

    public LiveData<HashMap<String, MutableLiveData<String>>> getFormFieldsErrors() {
        return formFieldsErrors;
    }

    public LiveData<Boolean> getCheckStacchettoButton() {
        return checkStacchettoButton;
    }

    public LiveData<Boolean> getEnableStacchettoDistance() {
        return enableStacchettoDistance;
    }

    public LiveData<Boolean> getCheckBolinaButton() {
        return checkBolinaButton;
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

        populateHashMap("");
        populateHashMap(getApplication().getString(R.string.error));
    }

    private void populateHashMaps() {
        HashMap<String, MutableLiveData<String>> map = formFields.getValue();


        map.put(getApplication().getString(R.string.regatta_name), new MutableLiveData<String>(bundle.getValue().get(getApplication().getString(R.string.name)).toString()));
        map.put(getApplication().getString(R.string.regatta_course_axis), new MutableLiveData<String>(bundle.getValue().get(getApplication().getString(R.string.wind_direction)).toString()));
        map.put(getApplication().getString(R.string.regatta_course_length), new MutableLiveData<String>(bundle.getValue().get(getApplication().getString(R.string.course_length)).toString()));
        map.put(getApplication().getString(R.string.regatta_start_line_length), new MutableLiveData<String>(bundle.getValue().get(getApplication().getString(R.string.start_line_length)).toString()));
        if (bundle.getValue().get(getApplication().getString(R.string.type)).equals(Constants.triangleRegatta)) {
            regattaType.setValue(Constants.triangleRegatta);
            enableBuoyStern.setValue(false);
        }
        if (bundle.getValue().get(getApplication().getString(R.string.break_distance)).equals(0.0)) {
            enableStacchettoDistance.setValue(false);
            checkStacchettoButton.setValue(false);
        }
        else {
            enableStacchettoDistance.setValue(true);
            checkStacchettoButton.setValue(true);
            map.put(getApplication().getString(R.string.regatta_stacchetto_distance), new MutableLiveData<String>(bundle.getValue().get(getApplication().getString(R.string.break_distance)).toString()));
        }
        if (bundle.getValue().get(getApplication().getString(R.string.second_mark_distance)).equals(0.0)) {
            enableBolinaDistance.setValue(false);
            checkBolinaButton.setValue(false);
        }
        else {
            enableBolinaDistance.setValue(true);
            checkBolinaButton.setValue(true);
            map.put(getApplication().getString(R.string.regatta_bolina_distance), new MutableLiveData<String>(bundle.getValue().get(getApplication().getString(R.string.second_mark_distance)).toString()));
        }
        if (bundle.getValue().get(getApplication().getString(R.string.botton_buoy)).equals(false)) {
            map.put(getApplication().getString(R.string.regatta_buoy_stern), new MutableLiveData<String>(getApplication().getString(R.string.regatta_buoy_stern_none)));
        }
        else if (bundle.getValue().get(Constants.regattaGate).equals(false)) {
            map.put(getApplication().getString(R.string.regatta_buoy_stern), new MutableLiveData<String>(getApplication().getString(R.string.regatta_buoy_stern_single)));
        }
        else {
            map.put(getApplication().getString(R.string.regatta_buoy_stern), new MutableLiveData<String>(getApplication().getString(R.string.regatta_buoy_stern_gate)));
        }

        formFields.setValue(map);

    }

    private void populateHashMap(String suffix) {
        HashMap<String, MutableLiveData<String>> map = new HashMap<>();
        if (suffix.equals("")) {
            map = formFields.getValue();
        }
        else {
            map = formFieldsErrors.getValue();
        }

        map.put(getApplication().getString(R.string.regatta_name) + suffix, new MutableLiveData<String>(""));
        map.put(getApplication().getString(R.string.regatta_course_axis) + suffix, new MutableLiveData<String>(""));
        map.put(getApplication().getString(R.string.regatta_course_length) + suffix, new MutableLiveData<String>(""));
        map.put(getApplication().getString(R.string.regatta_start_line_length) + suffix, new MutableLiveData<String>(""));
        map.put(getApplication().getString(R.string.regatta_stacchetto_distance) + suffix, new MutableLiveData<String>(""));
        map.put(getApplication().getString(R.string.regatta_bolina_distance) + suffix, new MutableLiveData<String>(""));
        map.put(getApplication().getString(R.string.regatta_buoy_stern) + suffix, new MutableLiveData<String>(""));

        if (suffix.equals("")) {
            formFields.setValue(map);
        }
        else {
            formFieldsErrors.setValue(map);
        }
    }

    public void onRegattaNameTextChanged(CharSequence s, int start, int before, int count) {
        formFields.getValue().get(getApplication().getString(R.string.regatta_name)).setValue(s.toString());
        resetDisplayError(formFieldsErrors.getValue().get(getApplication().getString(R.string.regatta_name_error)));
    }

    public void onCourseAxisTextChanged(CharSequence s, int start, int before, int count) {
        formFields.getValue().get(getApplication().getString(R.string.regatta_course_axis)).setValue(s.toString());
        resetDisplayError(formFieldsErrors.getValue().get(getApplication().getString(R.string.regatta_course_axis_error)));
    }

    public void onCourseLengthTextChanged(CharSequence s, int start, int before, int count) {
        formFields.getValue().get(getApplication().getString(R.string.regatta_course_length)).setValue(s.toString());
        resetDisplayError(formFieldsErrors.getValue().get(getApplication().getString(R.string.regatta_course_length_error)));
    }

    public void onStartLineLengthTextChanged(CharSequence s, int start, int before, int count) {
        formFields.getValue().get(getApplication().getString(R.string.regatta_start_line_length)).setValue(s.toString());
        resetDisplayError(formFieldsErrors.getValue().get(getApplication().getString(R.string.regatta_start_line_length_error)));
    }

    public void onStacchettoDistanceTextChanged(CharSequence s, int start, int before, int count) {
        formFields.getValue().get(getApplication().getString(R.string.regatta_stacchetto_distance)).setValue(s.toString());
        resetDisplayError(formFieldsErrors.getValue().get(getApplication().getString(R.string.regatta_stacchetto_distance_error)));
    }

    public void onBolinaDistanceTextChanged(CharSequence s, int start, int before, int count) {
        formFields.getValue().get(getApplication().getString(R.string.regatta_bolina_distance)).setValue(s.toString());
        resetDisplayError(formFieldsErrors.getValue().get(getApplication().getString(R.string.regatta_bolina_distance_error)));
    }

    public void onBuoySternTextChanged(CharSequence s, int start, int before, int count) {
        formFields.getValue().get(getApplication().getString(R.string.regatta_buoy_stern)).setValue(s.toString());
        resetDisplayError(formFieldsErrors.getValue().get(getApplication().getString(R.string.regatta_buoy_stern_error)));
    }

    public void onRegattaTypeChanged(int index) {
        if (index == 0) {
            regattaType.setValue(Constants.stickRegatta);
            enableBuoyStern.setValue(true);
        }
        else {
            regattaType.setValue(Constants.triangleRegatta);
            enableBuoyStern.setValue(false);
            resetDisplayError(formFieldsErrors.getValue().get(getApplication().getString(R.string.regatta_buoy_stern_error)));
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
        Boolean[] buoySternInfo = new Boolean[2];
        if (enableBuoyStern.getValue()== false || formFields.getValue().get(getApplication().getString(R.string.regatta_buoy_stern)).getValue().equals("")) {
            buoySternInfo[0] = false;
            buoySternInfo[1] = false;
        }
        else {
            buoySternInfo[0] = !(formFields.getValue().get(getApplication().getString(R.string.regatta_buoy_stern)).equals(getApplication().getString(R.string.regatta_buoy_stern_none)));
            buoySternInfo[1] = formFields.getValue().get(getApplication().getString(R.string.regatta_buoy_stern)).equals(getApplication().getString(R.string.regatta_buoy_stern_gate));
        }
        return buoySternInfo;
    }

    public Double[] getOptionalDistances() {
        Double[] optionalDistances = new Double[2];
        if (formFields.getValue().get(getApplication().getString(R.string.regatta_stacchetto_distance)).getValue().equals("")) {
            optionalDistances[0] = 0.0;
        }
        else {
            optionalDistances[0] = Double.parseDouble(formFields.getValue().get(getApplication().getString(R.string.regatta_stacchetto_distance)).getValue());
        }

        if (formFields.getValue().get(getApplication().getString(R.string.regatta_bolina_distance)).getValue().equals("")) {
            optionalDistances[1] = 0.0;
        }
        else {
            optionalDistances[1] = Double.parseDouble(formFields.getValue().get(getApplication().getString(R.string.regatta_bolina_distance)).getValue());
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
            if (k.equals(getApplication().getString(R.string.regatta_name))) {
                if (v.getValue().equals("")) {
                    mapFormFieldsErrors.get(getApplication().getString(R.string.regatta_name_error)).setValue(getApplication().getString(R.string.textfield_missing_field_error));
                    setFormValid(false);
                }
            } else if (k.equals(getApplication().getString(R.string.regatta_stacchetto_distance))) {
                if (enableStacchettoDistance.getValue() == true && v.getValue().equals("")) {
                    mapFormFieldsErrors.get(getApplication().getString(R.string.regatta_stacchetto_distance_error)).setValue(getApplication().getString(R.string.textfield_missing_field_error));
                    setFormValid(false);
                } else if (enableStacchettoDistance.getValue() == true && Double.parseDouble(v.getValue()) <= 0) {
                    mapFormFieldsErrors.get(getApplication().getString(R.string.regatta_stacchetto_distance_error)).setValue(getApplication().getString(R.string.textfield_invalid_field_error));
                    setFormValid(false);
                }
            } else if (k.equals(getApplication().getString(R.string.regatta_bolina_distance))) {
                if (enableBolinaDistance.getValue() == true && v.getValue().equals("")) {
                    mapFormFieldsErrors.get(getApplication().getString(R.string.regatta_bolina_distance_error)).setValue(getApplication().getString(R.string.textfield_missing_field_error));
                    setFormValid(false);
                } else if (enableBolinaDistance.getValue() == true && Double.parseDouble(v.getValue()) <= 0) {
                    mapFormFieldsErrors.get(getApplication().getString(R.string.regatta_bolina_distance_error)).setValue(getApplication().getString(R.string.textfield_invalid_field_error));
                    setFormValid(false);
                }
            }else if (k.equals(getApplication().getString(R.string.regatta_buoy_stern))) {
                if (enableBuoyStern.getValue() == true && v.getValue().equals("")) {
                    mapFormFieldsErrors.get(getApplication().getString(R.string.regatta_buoy_stern_error)).setValue(getApplication().getString(R.string.textfield_missing_field_error));
                    setFormValid(false);
                }
            } else {
                if (v.getValue().equals("")) {
                    mapFormFieldsErrors.get(k + getApplication().getString(R.string.error)).setValue(getApplication().getString(R.string.textfield_missing_field_error));
                    setFormValid(false);
                } else if (Double.parseDouble(v.getValue()) <= 0) {
                    mapFormFieldsErrors.get(k + getApplication().getString(R.string.error)).setValue(getApplication().getString(R.string.textfield_invalid_field_error));
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
            Log.d(TAG, "editRegatta: " + buoySternInfo[0] + " " + buoySternInfo[1]);
            Regatta regatta = new Regatta(
                    formFields.getValue().get(getApplication().getString(R.string.regatta_name)).getValue(),
                    regattaType.getValue(),
                    (int) (new Date().getTime()),
                    Integer.parseInt(formFields.getValue().get(getApplication().getString(R.string.regatta_course_axis)).getValue()),
                    Double.parseDouble(formFields.getValue().get(getApplication().getString(R.string.regatta_start_line_length)).getValue()),
                    optionalDistances[0],
                    Double.parseDouble(formFields.getValue().get(getApplication().getString(R.string.regatta_course_length)).getValue()) * 1000,
                    optionalDistances[1],
                    buoySternInfo[0],
                    buoySternInfo[1],
                    currentLat.getValue(),
                    currentLon.getValue()
            );

            //create buoys objects
            List<Buoy> buoys = BuoyFactory.buildCourse(regatta);

            regattaRepository.deleteRegatta(
                    regatta,
                    data->{},
                    (code, detail)->{}
            );

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
