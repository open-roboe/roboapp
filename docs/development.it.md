
# Guida per lo sviluppo android

> Questa guida da per scontato che tu abbia già android studio installato, e un account github

## Impostazioni iniziali

1. Apri l'app Android studio. Dal menu a tendina in alto a destra seleziona

    File > New > Project from Version Control

2. Nella finestra che comparirà, inserisci `https://github.com/open-roboe/roboapp-android.git` nel campo URL


4. Se hai già collegato Android studio a Github, Ti verrà aperto il progetto.
   Altrimenti ti verrà prima chiesto di effettuare l'autenticazione:
   Se succede, segui le [istruzioni per l'autenticazione in Github](./auth-github.it.md)

5. Per poter compilare correttamente, il progetto ha bisogno delle api key di google maps:
  
   Apri il file `Gradle scripts/local.properties`

   e aggiungi la riga

      MAPS_API_KEY=xxxxxxxxxxxxxxxxxxx

  sostituendo `xxxxxxxx` Con il vero token google, che devi chiedere sul gruppo whattsap


## sviluppare una feature

Una volta eseguite le impostazioni iniziali, è possibile iniziare a sviluppare una feature.
Per farlo è importante impostare correttamente git.

### lavorare ad una nuova feature

1. assicurati di essere nel ramo master: `git checkout master`

2. ottieni le ultime modifiche in master: `git pull origin master`

3. crea un nuovo branch in cui lavorerai alla feature: `git checkout -b feature_nomedellafeature`
   esempio: se vuoi implementare la login activity: `git checkout -b feature_login_activity`
   è buona pratica iniziare il nome del branch con feature_

4. lavora al codice. ogni volta che hai fatto delle modifiche consistenti fai un salvataggio(commit): 
   in android android studio Basta premere `CTRL + k`, selezionare i file da includere nel commit, scrivere una breve descrizione e premere il tasto commit in basso a sinistra.
   Si può anche fare da terminale: `git add --all && git commit -m "descrizione del commit"`

5. Metti su github i tuoi commit: `git push -u origin feature_nomedelbranch` oppure premi `CTRL + SHIFT + k`

### lavorare ad una feature esistente

1. individua il nome del branch in cui si sta sviluppando la feature: La lista di tutti i branch è [consultabile qui](https://github.com/open-roboe/roboapp-android/branches)

2. Aggiorna la lista locale dei branch: `git fetch`

3. spostati nel branch che preferisci: `git checkout nomedelbranch`.
   Ad esempio, se vuoi lavorare nel branch `feature_login_activity`: `git checkout feature_login_activity`

4. lavora al codice. ogni volta che hai fatto delle modifiche consistenti fai un salvataggio: `git add --all && git commit -m "descrizione del commit"`
   Questo comando è piu comodo da android studio: Basta premere `CTRL + k`, selezionare i file da includere nel commit, scrivere una breve descrizione e premere il tasto commit in basso a sinistra

5. Metti su github i tuoi commit: `git push -u origin feature_nomedelbranch` oppure premi `CTRL + SHIFT + k`
   Se stai lavorando insieme a qualcun'altro sullo stesso branch, il push ptrebbe non funzionare perchè qualcuno
   ha fatto un commit e lo ha caricato su github prima di te.
   In questo caso, per poter fare push dovrai prima ottenere il suo commit e 'inglobarlo' nel tuo codice.
   Per farlo basta fare `git pull`. concluso il pull potrai fare il push normalmente

