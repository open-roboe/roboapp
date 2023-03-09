
# schermate con Fragment, view, viewmodel

Fragment, view, viewmodel compongono una schermata dell'applicazione. Sono i concetti piu importanti da imparare

## Concetti

In android studio, apri il file `res/navigation/navigation_main.xml`
Per aprire l'editor grafico delle navigazioni.

Tutte le schermate dell'applicazione e le loro interazioni e navigazioni
soon definite in questo file.

[TODO IMG]


Per fare un esempio, creiamo una nuova schermata che mostra un pulsante
con un contatore di quante volte è stato premuto il pulsante.

### viewModel
Una schermata ha spesso bisogno di salvare da qualche parte dei dati che servono solo a lei. Nel nostro caso vogliamo un posto in cui salvare il numero di volte che è stato premuto il pulsante.

Per questo tipo di dati si utilizza il viewModel. Scriviamo il nostro:

```java

// file: PulsanteViewModel.java

package it.halb.roboapp.ui.main;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class PulsanteViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> counter = new MutableLiveData<>(0);
    
        
    public LoadViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Integer> getCounter(){
        return counter;
    }

    public void incrementCounter(){
        Integer currentValue = counter.getValue();
        if(currentValue == null) currentValue = 0;

        counter.setValue(currentValue + 1);
    }


}


```

Il costruttore per ora è vuoto, ma lo teniamo. Lo useremo quando servirà comunicare con le repository.


### View

In questo esempio non ha importanza il design della view, vogliamo solamente un pulsante. Normalmente però il design da implementare è definito in Figma, e serve seguire i passaggi della [Guida per il design](./view-design.it.md)

Iniziamo col definire un layout vuoto

[TODO layout vuoto]

A differenza di un normale layout, il nostro 

### Fragment
Inizializziamo e coleghiamo tutto TODO

### aggiungiamo la schermata alla navigazione

In android studio, apri il file `res/navigation/navigation_main.xml`
Per aprire l'editor grafico delle navigazioni.

premi il tasto + in alto a sinistra, e cerca il nome del fragment che hai creato, per aggiungerlo alle schermate dell'applicazione

