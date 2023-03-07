package it.halb.roboapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import it.halb.roboapp.dataLayer.AuthRepository;

public class UrlConfigViewModel extends AndroidViewModel {

    public final MutableLiveData<String> urlError = new MutableLiveData<>("");
    public final MutableLiveData<String> url = new MutableLiveData<>("");
    private final AuthRepository authRepository;

    public UrlConfigViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    public void urlTextChanged(CharSequence s, int start, int before, int count){
        url.setValue(s.toString());
        urlError.setValue("");
    }

    public void save(){
        boolean success = authRepository.setApiBaseUrl(url.toString());
        if(success){
            //navigate back
        }
        else{
            urlError.setValue("Invalid URL. all urls must end with /");
        }
    }

}
