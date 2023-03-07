
# design

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

TODO

## navbar

TODO

## bottombar

TODO

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

### coordinator layout:

https://saulmm.github.io/mastering-coordinator

