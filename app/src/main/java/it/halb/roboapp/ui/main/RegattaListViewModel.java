package it.halb.roboapp.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Random;

import it.halb.roboapp.dataLayer.AuthRepository;
import it.halb.roboapp.dataLayer.ErrorCallback;
import it.halb.roboapp.dataLayer.RegattaRepository;
import it.halb.roboapp.dataLayer.SuccessCallback;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class RegattaListViewModel extends AndroidViewModel {

    public boolean showSwipeHint = true;
    private final LiveData<List<Regatta>> allRegattas;
    private final RegattaRepository regattaRepository;
    private final AuthRepository authRepository;

    public RegattaListViewModel(@NonNull Application application) {
        super(application);
        regattaRepository = new RegattaRepository(application);
        authRepository = AuthRepository.getInstance(application);
        allRegattas = regattaRepository.getAllRegattas();
    }
    public void testLogout(){
        authRepository.logout();
    }

    public LiveData<List<Regatta>> getAllRegattas(){
        return allRegattas;
    }

    public void deleteRegatta(Regatta regatta){
        regattaRepository.deleteRegatta(
                regatta,
                data->{},
                (code, detail)->{}
        );
    }

    public void refresh(SuccessCallback<Void> success, ErrorCallback error){
        regattaRepository.loadAllRegattas(l -> {
            success.success(null);
        }, error);
    }

}
