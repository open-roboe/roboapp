package it.halb.roboapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import it.halb.roboapp.dataLayer.AuthRepository;
import it.halb.roboapp.dataLayer.localDataSource.Account;

public class MainViewModel extends AndroidViewModel {
    private final LiveData<Account> account;

    private boolean isFirstLaunch = true;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AuthRepository authRepository = new AuthRepository(application);
        account = authRepository.getAccount();
    }

    public LiveData<Account> getAccount(){
        return account;
    }

    public boolean isFirstLaunch(){
        return isFirstLaunch;
    }

    public void setLaunched(){
        isFirstLaunch = false;
    }
}
