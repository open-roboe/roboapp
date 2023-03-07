package it.halb.roboapp.ui.auth;

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
    private final MutableLiveData<Boolean> somethingToSave = new MutableLiveData<>(false);
    private final String initialUrl;
    private final AuthRepository authRepository;

    public LiveData<String> getUrlError(){
        return urlError;
    }

    public LiveData<String> getUrl(){
        return url;
    }

    public LiveData<Boolean> getSomethingToSave(){
        return somethingToSave;
    }

    public UrlConfigViewModel(@NonNull Application application) {
        super(application);
        authRepository = AuthRepository.getInstance(application);
        String fetchedUrl = authRepository.getApiBaseUrl();
        url.setValue(fetchedUrl);
        initialUrl = fetchedUrl;
    }

    public void urlTextChanged(CharSequence s, int start, int before, int count){
        url.setValue(s.toString());
        urlError.setValue("");
        somethingToSave.setValue(
                !s.toString().equals(initialUrl)
        );
    }

    public boolean save(){
        boolean success = authRepository.setApiBaseUrl(url.getValue());
        if(!success)
            urlError.setValue("Invalid URL. all urls must end with /");
        return success;
    }

}
