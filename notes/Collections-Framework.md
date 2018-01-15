###### Appunti tratti da "Java, The Complete Reference, Ninth Edition" di Herbert Schildt. ######

# 1. Caratteristiche Principali
- Il framework è costruito su un **set di interfacce** standard, alcune delle quali implementate in classi già utilizzabili così come sono.
- Comprende meccanismi per l'integrazione con gli **array** standard
- Sono definiti degli **algoritmi** che operano sulle collezioni attraverso metodi per la manipolazione dei dati
-d. Comprende l'interfaccia **Iterator** che offre l'accesso agli elementi di una collezione
- Definisce una serie di **interfacce map** e relative classi che definiscono dati come chiave/valore, che per quanto facciano parte del 
framework non sono propriamente delle collezioni, ma hanno una forte relazione con esse.
- Supportano i **Generics** e grazie all'autoboxing/unboxing anche i tipi primitivi

# 2. Panoramica sulle interfacce standard

|Interfaccia | Descrizione |
|------------|:------------|
|Collection  | Elemento in cima alla gerarchia delle collezioni, permette di lavorare con gruppi di oggetti |
|List        | Estende ```Collection```, gestisce sequenze di oggetti |
|Set         | Estende ```Collection```, gestisce set di oggetti univoci |
|Queue       | Estende ```Collection```, gestisce liste particolari, in cui gli elementi sono rimossi solo dall'inizio |
|Deque       | Estende ```Queue```, gli elementi sono gestiti sia dall'inizio che dalla fine della lista |
|SortedSet   | Estende ```Set```, oggetti univoci ordinati |
|NavigableSet| Estende ```SortedSet```, gestisce il recupero di elementi in base alle ricerche di corrispondenza più vicine

![diagram](../resources/interfaces.png)

In aggiunta a queste interfacce, Collections usa anche le interfacce 
- ```Comparator```: per confrontare due oggetti
- ```Iterator```, ```ListIterator```, ```Spliterator```: enumerano gli oggetti all'interno di una collezione
- ```RandomAccess``` : indica che una lista supporta l'accesso casuale ai propri elementi

## 2.1 Collection
> L'interfaccia [Collection](https://docs.oracle.com/javase/9/docs/api/java/util/Collection.html) è il fondamento su cui si basa l'intero framework, in quanto ogni classe che definisce una
collezione deve implementare tale interfaccia.
È definita come 
>```
>interface Collection<E>
>```
> ed estende ```Iterator```. Collection dichiara i metodi principali di tutte le collezioni e per questo motivo sono degni di essere
> memorizzati e capiti nella loro interezza

<table><tr><th>Metodo</th><th>Descrizione</th>
<tr><td nowrap>boolean add(E obj)</td><td>aggiunge obj alla collezione, restituisce true se l'oggetto è stato aggiunto, false se obj era già presente e la collezione non ammette duplicati</td></tr>
<tr><td nowrap>boolean addAll( Collection&lt? extend E&gt c )</td><td>aggiunge tutti gli elementi di c alla collezione, restituisce true se la collezione viene modificata, altrimenti false</td></tr>
<tr><td nowrap>void clear()</td><td>rimuote tutti gli elementi dalla collezione</td></tr>
<tr><td nowrap>boolean contains(Object obj)</td><td>restituisce true se obj è un elemento della collezione, altrimenti false</td></tr>
<tr><td nowrap>boolean containsAll( Collection&lt?&gt c )</td><td>restituisce true se la collezione contiene tutti gli elementi di c, altrimenti false</td></tr>
<tr><td nowrap>boolean equals(Object obj)</td><td>restituisce true se la collezione è uguale a obj</td></tr>
<tr><td nowrap>int hashCode()</td><td>restituisce l'hashcode della collezione</td></tr>
<tr><td nowrap>boolean isEmpty()</td><td>restituisce vero se la collezione è vuota, altrimenti false</td></tr>
<tr><td nowrap>Iterator<E> iterator()</td><td>restituisce un iterator per la collezione</td></tr>
<tr><td nowrap>default Stream<E> parallelStream()</td><td> restituisce uno stream che utilizza la collezione come sorgente per i propri elementi. Se possibile, lo stream utilizzerà il parallelismo per incrementare le prestazioni ( JDK8 )</td></tr>
<tr><td nowrap>boolean remove(Object obj)</td><td>rimuove una istanza di obj dalla collezione, restituisce true se l'oggetto viene effettivamente rimosso, false in caso contrario</td></tr>
<tr><td nowrap>boolean removeAll(Collection&lt?&gt c)</td><td>rimuove dalla collezione tutti gli elementi di c</td></tr>
<tr><td nowrap>default boolean removeIf( Predicate&lt? super E&gt p )</td><td>rimuove dalla collezione gli elementi che soddisfano la condizione p</td></tr>
<tr><td nowrap>boolean retainAll(Collection&lt?&gt c)</td><td>rimuove dalla collezione tutti gli elementi che non sono presenti in c</td></tr>
<tr><td nowrap>int size()</td><td>restituisce il numero di elementi della collezione</td></tr>
<tr><td nowrap>default Spliterator<E> spliterator()</td><td>restituisce uno spliterator per la collezione</td></tr>
<tr><td nowrap>default Stream<E> stream()</td><td>restituisce uno stream che utilizza la collezione come sorgente per i propri elementi. Lo stream è sequenziale.</td></tr>
<tr><td nowrap>Object[] toArray()</td><td>restituisce un array che contiene tutti gli elementi della collezione</td></tr>
<tr><td nowrap><T> T[] toArray(T array[])</td><td>restituisce un array che contiene tutti gli elementi della collezione. Se la dimensione di array è uguale a quella della collezione, gli elementi sono copiati in array; se è inferiore, viene creato un nuovo array; se superiore, le posizioni eccedenti sono inizializzate a null</td></tr>
</table>

## 2.2 List
>L'interfaccia [List](https://docs.oracle.com/javase/9/docs/api/java/util/List.html) estende `Collection` e descrive
>una collezione come una sequenza di elementi non ordinati. Si può inserire o accedere agli elementi attraverso la loro posizione nella
>lista, partendo dalla posizione 0 ( zero ); una List può contenere elemeneti duplicati.<br/>
>È definita come 
>```
>interface List<E>
>```
> Aggiunge i propri metodi come descritti nella documentazione

## 2.3 Set
>L'interfaccia [Set](https://docs.oracle.com/javase/9/docs/api/java/util/Set.html) estende `Collection`
>e rappresenta un set di oggetti univoci, ovvero non permette duplicati al proprio interno.<br/>
>È definita come 
>```
>interface Set<E>
>```
> Aggiunge i propri metodi come descritti nella documentazione

## 2.4 SortedSet
>L'interfaccia [SortedSet](https://docs.oracle.com/javase/9/docs/api/java/util/SortedSet.html) estende `Set` descrivendo
>un set ordinato in modo ascendente.<br/>
>È definita come 
>```
>interface SortedSet<E>
>```
> Aggiunge i propri metodi come descritti nella documentazione

## 2.5 NavigableSet
>L'interfaccia [NavigableSet](https://docs.oracle.com/javase/9/docs/api/java/util/NavigableSet.html) estende `SortedSet` e
>descrive una collezione che supporta il recupero degli oggetti basato su una ricerca di corrispondenza più vicina al valore passato.<br/>
>È definita come 
>```
>interface NavigableSet<E>
>```
> Aggiunge i propri metodi come descritti nella documentazione

## 2.6 Queue
>L'interfaccia [Queue](https://docs.oracle.com/javase/9/docs/api/java/util/Queue.html) estende `Collection` e descrive una collezione
>di tipo coda, spesso rappresentata come una lista first-in, first-out. Gli elementi possono essere rimossi solo dall'inizio della coda.<br/>
>È definita come 
>```
>interface Queue<E>
>```
> Aggiunge i propri metodi come descritti nella documentazione

## 2.7 Deque
>L'interfaccia [Deque](https://docs.oracle.com/javase/9/docs/api/java/util/Deque.html) estende `Queue` descrivendo una collezione di
>tipo coda in cui gli elementi possono essere presi da entrambi i capi, quindi first-in, first-out oppure last-in, first-out.
>Comprende i metodi `push()` e `pop()` che permettono a Deque di rappresentare uno *stack*.<br/>
>È definita come 
>```
>interface Deque<E>
>```
> Aggiunge i propri metodi come descritti nella documentazione