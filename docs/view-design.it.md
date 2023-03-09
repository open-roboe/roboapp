
# design

Il design dell'applicazione è definito su [Questo progetto Figma](https://www.figma.com/file/HPiW882us98ExlP1A4v5It/Roboapp_Material3?node-id=53296%3A27527&t=15ZGVmNAZ8gUA7NO-1)


## Consigli generali

Utilizzare Constraintlayout per organizzare i vari elementi di una pagina.
Solo in rari casi servono altri tipi di layout

Constraint layout è spiegato in dettaglio in [questo sito](https://constraintlayout.com/), e permette di implementare design avanzati, come in
[Questo esempio](https://constraintlayout.com/basics/create_chains.html)

Quando si impostano le dimensioni di un elemento, usare sempre `match_parent` oppure `wrap_content`. è meglio evitare il piu possibile valori numerici nel design.

In alcuni casi, come quando si impostano padding, servono valori numerici. In questi casi usare `dp` come unita di misura, e come valore multipli di 8: `0, 8, 16, 24, 32`

## Preview del design scompare

Sta roba succede spesso: A caso, la preview del design diventa nera e non si capisce piu niente. [TODO: IMG]
per risolverla, premere `r` sulla tastiera, per ricaricare la preview

Un altro trucco che spesso risolve problemi: `Build > Clean project` oppure 
`Build > Rebuild project` . [TODO: IMG] è praticamente come riavviare android studio ma istantaneo.


## Usare gli elementi in figma

Implementare in android studio il design su figma non è immediato.

Diversi elementi come TextInput, navbar, etch si implementano in un modo specifico. 
Non basta trascinare un TextInput dalla lista di elementi di android studio, perchè quelli sono elementi Materia design 2.

Quindi come si fa?

Se l'elemento è gia stato implementao da qualche parte nell'app, si può copiare da li. Nota che quando si copia un elemento l'ID va cambiato.

Altrimenti, si può consultare la documentazione, tramite questa tarantella:

- cliccare sull'elemento in figma per scoprire il nome (a volte è accurato, a volte no, ma rende l'idea abbastanza)
- cercare il nome dell'elemento sulla [Guida material3](https://m3.material.io/components). Ad esempio, l'input di testo è in [questa pagina](https://m3.material.io/components/text-fields/overview)
- All'interno della pagina, nella sezione Resources, trovare il link alla pagina di documentazione MDC-Android. Ad esempio, per L'input di testo la documentazione è [questa](https://github.com/material-components/material-components-android/blob/master/docs/components/TextField.md)
- Usare la guida e gli esempi nella documentazione MDC-Android per implementare l'elemento in android studio. Nota che alcuni elementi non sono supportati dala nostra versione, e semplicemente non possono essere usati


## elementi di ui complessi

Quando si implementa un design la maggior parte del tempo è spesa a capire come
si implementano elementi comlpessi, come liste di dati, navbar, popup, eccetera.

Le guide che seguono spiegano come implementare alcuni di questi elementi all'interno della roboapp.

### navigare da una schermata ad un altra

TODO

### Implementare liste di dati

https://developer.android.com/develop/ui/views/layout/recyclerview
https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter

### Bottom sheet

Bottom sheet è una normale schermata, che però si apre dal basso, coprendo parzialmente
la schermata sotto. [TODO: IMG]

è molto facile da implementare:

- Prima di tutto si implementa una normale schermata, [come spiegato qui](./fragment-v-vm.it.md)
- Nel navgraph, si trova la sezione che descrive la schermata e si sostituisce il tag `fragment` con un tag `dialog`
- Si modifica il fragment della schermata: la classe fragment deve estendere `BottomSheetFragment` anzichè `Fragment`

[Queso video](https://www.youtube.com/watch?v=91kHVOYQM0s&list=PLSrm9z4zp4mHilvsfUM3jeCYFV3fTAS3J&index=10) mostra chiaramente i passaggi

All'interno di un BottomSheetFragment non si può navigare ad altre schermate.
L'unica operazione permessa è chiudere il bottomsheetfragment. Per farlo basta eseguire
`dismiss()`

