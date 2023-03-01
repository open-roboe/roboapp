
# schermate con Fragment, view, viewmodel

Fragment, view, viewmodel compongono una schermata dell'applicazione. Sono i concetti piu importanti da imparare

## Concetti

In android studio, apri il file `res/navigation/navigation_main.xml`
Per aprire l'editor grafico delle navigazioni.

Tutte le schermate dell'applicazione e le loro interazioni e navigazioni
soon definite in questo file.


> TODO

Per imparare, creiamo una nuova schermata che mostra un pulsante
e un contatore di quante volte è stato premuto il pulsante.

### viewModel
Una schermata ha spesso bisogno di salvare da qualche parte dei dati che servono solo a lei. Nel nostro caso vogliamo un posto in cui salvare il numero di volte che è stato premuto il pulsante.

I dati di una schermata si salvano nel viewmodel

### View
impostiamo databinding con viewmodel

### Fragment
Inizializziamo e coleghiamo tutto

### aggiungiamo la schermata alla navigazione

In android studio, apri il file `res/navigation/navigation_main.xml`
Per aprire l'editor grafico delle navigazioni.

premi il tasto + in alto a sinistra, e cerca il nome del fragment che hai creato, per aggiungerlo alle schermate dell'applicazione


