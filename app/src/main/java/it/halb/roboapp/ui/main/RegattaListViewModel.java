package it.halb.roboapp.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Random;

import it.halb.roboapp.dataLayer.AuthRepository;
import it.halb.roboapp.dataLayer.regattaRepository;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class RegattaListViewModel extends AndroidViewModel {

    private final LiveData<List<Regatta>> allRegattas;
    private final regattaRepository regattaRepository;
    private final AuthRepository authRepository;

    public RegattaListViewModel(@NonNull Application application) {
        super(application);
        regattaRepository = new regattaRepository(application);
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

    public void fakeInsert(){
        Log.d("FAKEINSERT", "cliecked");
        Random rand = new Random();
        String name = "name" + String.valueOf(rand.nextInt());
        regattaRepository.insertRegatta(new Regatta(name, "type", 123));
    }
}
