# Repository

> Le repository non sono ancora state sviluppate! Per ora, skippare questa documentazione

Premessa: Se non sono chiari, i concetti di Repository e di Data layer sono spiegati prevemente nella [documentazione sull'architettura](https://developer.android.com/topic/architecture#recommended-app-arch) di Android.

In poche parole comunque, il data layer è la parte dell'applicazione che gestisce la logica e i dati dell'applicazione.

Tutte le interazioni con il data layer dell'applicazione avvengono tramite queste tre repository

## AuthRepository

Serve a gestire login, logout, ottenere i dati sull'utente loggato come ad esempio nome, e se è race officer.
Questi dati sono molto utili, ad esempio alcuni pulsanti dell'app si possono disattivare se l'utente non è
un race officer.

[TODO: link a docs authrepository]

## RegattaRepository

Questa repository serve a gestire le regate:
Contiene i metodi per modificare una regata, creare una regata, o eliminare una regata.

[TODO: link a docs regattarepository]

## RunningRegattaRepository

Questta repository serve a gestire la regata attiva al momento, e permette di ottenere
la lista di boe, roboe, barche, e altre info sulla regata attualmente selezionata.

è una repository particolare, perchè può essere usata solamente all'interno delle schermate:

- MapFragment
- BuoyInfoFragment
- BoatInfoFragment
- RoboaInfoFragment

[TODO: link a docs runningregattarepository]

la documentazione di questa repository include una spiegazione del suo comportamento particolare

[TODO: move to runningregattarepository doc]

Questa repository è complicata perchè quando si "lancia" una regatta, Occorre lanciare un serviceworker che tiene aggiornata la repository.
un service worker può solo essere lanciato da un fragment, non da dentro una repository, quindi ci troviamo
nella situazione un po sporca dove una repository deve essere tenuta in sincrono con un service, e qualcuno deve occuparsene.

Quel qualcuno è la schermata loadFragment[TODO: rinominare in futuro, magari in InitRunningRegattaFragment]


Quando si è nella schermata di login, o nella schermata con la lista delle regata, questa repository è vuota e non serve a niente.

Per attivare una regata bisogna navigare al fragment LoadFragment passando come argomento il nome della regatta che si vuole attivare.
Loadfragment si occupa di inizializzare il background service

Una volta attivata questa repository permette di ottenere boe, roboe, barche, e tutte le informazioni associate alla regata selezionata
