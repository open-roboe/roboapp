package it.halb.roboapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Random;

import it.halb.roboapp.dataLayer.Repository;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class RegattaListViewModel extends AndroidViewModel {

    private final LiveData<List<Regatta>> allRegattas;
    private final Repository repository;

    public RegattaListViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allRegattas = repository.getAllRegattas();
    }

    public void testLogout(){
        repository.logout();
    }

    public LiveData<List<Regatta>> getAllRegattas(){
        return allRegattas;
    }

    public void deleteRegatta(Regatta regatta){
        repository.deleteRegatta(regatta);
    }

    public void fakeInsert(){
        Log.d("FAKEINSERT", "cliecked");
        Random rand = new Random();
        String name = "name" + String.valueOf(rand.nextInt());
        repository.insertRegatta(new Regatta(name, "type", 123));
    }
}
