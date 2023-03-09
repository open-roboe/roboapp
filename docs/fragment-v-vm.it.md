
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

[Codice completo]

Il costruttore per ora è vuoto, ma lo teniamo. Lo useremo quando servirà comunicare con le repository.


### View
impostiamo databinding con viewmodel TODO

In questo esempio non ha importanza il design della view. Normalmente però le cose
sono piu complesse, e specificate in dettaglio in figma.
[Questa guida](./view-design.it.md) spiega come implementare il design in una view, inclusi gli elementi descritti in figma.

[Codice completo]

A differenza di un normale layout, il nostro 

### Fragment
Inizializziamo e coleghiamo tutto TODO

### aggiungiamo la schermata alla navigazione

In android studio, apri il file `res/navigation/navigation_main.xml`
Per aprire l'editor grafico delle navigazioni.

premi il tasto + in alto a sinistra, e cerca il nome del fragment che hai creato, per aggiungerlo alle schermate dell'applicazione

