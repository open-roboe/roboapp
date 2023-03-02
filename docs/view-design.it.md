
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

### Implementare liste di dati

https://developer.android.com/develop/ui/views/layout/recyclerview
https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter

### coordinator layout:

https://saulmm.github.io/mastering-coordinator

