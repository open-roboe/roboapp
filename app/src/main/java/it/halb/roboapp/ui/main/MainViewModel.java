package it.halb.roboapp.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import it.halb.roboapp.dataLayer.AuthRepository;
import it.halb.roboapp.dataLayer.localDataSource.Account;

public class MainViewModel extends AndroidViewModel {
    private final LiveData<Account> account;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AuthRepository authRepository = AuthRepository.getInstance(application);
        account = authRepository.getAccount();
        Log.d("VIEWMODEL_SCOPING_TEST", "main viewmodel constructor run");
    }

    public LiveData<Account> getAccount(){
        return account;
    }

}
