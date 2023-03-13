
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

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
>
    <data>
        <variable
            name="loadViewModel"
            type="it.halb.roboapp.ui.main.LoadViewModel" />
    </data>
    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        
        <!-- Metti qui il tuo layout -->
        
        </androidx.constraintlayout.widget.ConstraintLayout>
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>

```

Questo layout è particolare: tutto è contenuto dentro un tag `<layout>`, che serve ad attivare il [databinding](https://developer.android.com/topic/libraries/data-binding). 
Dentro al tag layout, il tag `<data>` definisce la variabile pulsanteViewModel che permette di accedere ai dati del nostro viewModel

```xml
    <data>
        <variable
            name="pulsanteViewModel"
            type="it.halb.roboapp.ui.main.PulsanteViewModel" />
    </data>
 ```
 
 Ora modifichiamo il nostro layout per aggiungere una textiview e un pulsante che utilizzano databinding
 
 
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <data>
        <variable
            name="loadViewModel"
            type="it.halb.roboapp.ui.main.LoadViewModel" />
    </data>
    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        
        <!-- Metti qui il tuo layout -->
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="@{pulsanteViewModel.counter}"
                android:textAppearance="@style/TextAppearance.AppCompat.Display3" />

            <Button
                android:id="@+id/button4"
                android:layout_margin="20dp"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="Incrementa contatore" />

        
        </androidx.constraintlayout.widget.ConstraintLayout>
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>

La cosa interessante è il campo `android:text="@{pulsanteViewModel.counter}"` all'interno del tag TextView
Per imparare i dettagli puoi leggere la documentazione sul [databinding](https://developer.android.com/topic/libraries/data-binding). 
```
    


### Fragment

Il fragment unisce la view (sinonimo di layout xml) e il viewmodel.


### aggiungiamo la schermata alla navigazione

In android studio, apri il file `res/navigation/navigation_main.xml`
Per aprire l'editor grafico delle navigazioni.

premi il tasto + in alto a sinistra, e cerca il nome del fragment che hai creato, per aggiungerlo alle schermate dell'applicazione


