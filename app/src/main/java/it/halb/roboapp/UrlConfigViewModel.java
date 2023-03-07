package it.halb.roboapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import it.halb.roboapp.dataLayer.AuthRepository;

public class UrlConfigViewModel extends AndroidViewModel {

    private final MutableLiveData<String> urlError = new MutableLiveData<>("");
    private final MutableLiveData<String> url = new MutableLiveData<>("");
    private final AuthRepository authRepository;

    public LiveData<String> getUrlError(){
        return urlError;
    }

    public LiveData<String> getUrl(){
        return url;
    }

    public UrlConfigViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        url.setValue(authRepository.getApiBaseUrl());
    }

    public void urlTextChanged(CharSequence s, int start, int before, int count){
        url.setValue(s.toString());
        urlError.setValue("");
    }

    public boolean save(){
        boolean success = authRepository.setApiBaseUrl(url.toString());
        if(!success)
            urlError.setValue("Invalid URL. all urls must end with /");
        return success;
    }

}
