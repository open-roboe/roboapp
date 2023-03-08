# Repository

Tutte le interazioni con il data layer dell'applicazione avvengono tramite queste tre repository

## AuthRepository


## RegattaRepository

Questa repository serve a gestire le regate:
Contiene i metodi per modificare una regata, creare una regata, o eliminare una regata.

## RunningRegattaRepository

Questa è una repository speciale, perchè Rappresenta la regata attiva al momento.

Quando si è nella schermata di login, o nella schermata con la lista delle regata, questa repository è vuota e non serve a niente.

Per attivare una regata bisogna navigare al fragment LoadFragment passando come argomento il nome della regatta che si vuole attivare.

Una volta attivata questa repository permette di ottenere boe, roboe, barche, e tutte le informazioni associate alla regata selezionata


