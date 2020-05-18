# Introduzione a JavaFX

- [JavaFX](https://openjfx.io/)
- [API](https://openjfx.io/javadoc/14/)
- [Documentazione Oracle](https://docs.oracle.com/javase/8/javase-clienttechnologies.htm)
- [Introduction to FXML](https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/doc-files/introduction_to_fxml.html)
- [JavaFX CSS Reference Guide](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html)

## 1. Configurazione con gradle e IntelliJ

Una guida più dettagliata è descritta nella [documentazione](https://openjfx.io/openjfx-docs/) del progetto.
Per creare una applicazione JavaFX in IntelliJ utilizzando gradle occorre creare un progetto `gradle` e 
utilizzare il `build.gradle` seguente

```groovy
plugins {
    id 'java'
    id 'application'
    id 'org.openjfx.javafxplugin' version '0.0.8' // versione plugin per gradle
}

group 'org.example'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    testCompile group: 'junit', name: 'junit', version: '4.12'
}

javafx {
    version = "14"
    modules = [ 'javafx.controls', 'javafx.fxml' ] // moduli che mi servono
}

mainClassName = 'org.example.fxgradle.MainClass' // classe che contiene il metodo main
```
 
## 2. Introduzione e terminologia.

La metafora centrale di JavaFX è lo *stage*; e come ogni palco, contiene una *scene*.
Uno *stage* definisce uno spazio, mentre una *scene* descrive cosa avviene in tale spazio.
In altre parole, lo *stage* è il contenitore per le *scenes*, le *scenes* sono i contenitori
per gli elementi che la compongono.

### 2.1 Componenti principali
`Stage` è un contenitore primario, tutte le applicazioni JavaFX hanno automaticamente accesso
ad uno Stage, chiamato stage primario. Lo stage primario è fornito in runtime quando l'applicazione
viene avviata. Nonostante sia possibile creare ulteriori stages, per la maggior parte delle
applicazioni lo stage primario sarà l'unico necessario.

Per creare una *scene*, bisogna aggiungere elementi ad una istanza di `Scene`; quindi, aggiungere
l'oggetto `Scene` ad uno `Stage`

Gli elementi di una `Scene` sono chiamati *nodes*, nodi. Tali nodi possono essere singoli
o a gruppi, possono avere *child* (figli), *parent* * (genitore); i nodi senza figli sono chiamati
*leaves* (foglie) e la collezione di tutti i nodi creati in una *scene* prende il nome di *tree* (albero).

La classe base che descrive un nodo è `Node`, da cui dipendono direttamente o indirettamente
altre numerose classi tra cui per esempio `Parent`, `Group`, `Region`, `Control`, solo per nominarne qualcuno.

JavaFX fornisce alcuni riquadri per gestire il processo di posizionamento degli elementi in una scena,
che prendono il nome di *layout*. Ognuno è in qualche modo una sottoclasse di `Node` e sono
racchiusi all'interno del package `javafx.scene.layout`

Una applicazione JavaFX deve essere una sottoclasse di `Application`, presente nel
package `javafx.application`. Estendendo questa classe quindi, si ha accesso a tre metodi
definiti nella superclasse per la gestione del ciclo di vita dell'applicazione: 
 1. `void init( )`
 2. `abstract void start(Stage primaryStage)`
 3. `void stop( )`
 
che vengono eseguiti proprio nell'ordine elencato.

`init()` è eseguito quando viene lanciata l'applicazione, ed è usato per eseguire
le opportune inizializzazioni< non può essere usato per creare uno stage o per costruire
una scene. Se non è richiesta nessuna inizializzazione, si può evitare l'override e usare
quindi la versione vuota della superclasse.

`start()` è eseguito subito dopo; qui è dove l'applicazione inizia, ed è usato per
costruire stage e scene. Riceve come argomento un oggetto Stage, che rappresenta lo
stage primario fornito dal sistema di runtime. È un metodo astratto quindi va 
necessariamente implementato dalla propria applicazione.

`stop()` è il metodo in cui gestire le operazioni di chiusura e pulizia. Nel caso non
occorra nessun tipo di operazione del genere, si può evitare l'override e usare 
quindi la versione vuota della superclasse.

In alcuni casi in applicazioni indipendenti o autonome, occore l'esecuzione nel metodo *launch()*,

`public static void launch(String … args)`

che avvierà l'applicazione partendo dal metodo `init()` come in tutti gli altri casi.
Il metodo *lauch()* non terminerà la propria esecuzione fino a che l'applicazione è in esecuzione
e prassi vuole che venga eseguito da un metodo `main()`

È importante sottolineare che né *main()*, né *launch()* sono strettamente necessari, quindi è 
possibile imbattersi in applicazioni JavaFX dove essi non sono presenti. Comunque, utilizzarli
garantisce una maggiore compatibilità in una larga parte di circostanze. Inoltre, se la propria
applicazione ha necessità di *main()* per altri motivi, allora è necessario un esplicito uso di *launch()*

Il passo successivo alla costruzione di una interfaccia, è costruire un grafo per la scena.
Uno dei componenti più semplici forniti da JavaFX è sicuramente `Label`, una classe 
di `javafx.scene.control` che identifica una etichetta contenente una stringa o una immagine.

```java
Label label = new Label("Hello world");
```

Una volta creato una istanza dell'oggetto - o di qualunque altro oggetto `Control` - va aggiunto
alla `Scene`, il che significa aggiungerlo al grafo della scena.

Per farlo occorre eseguire il metodo `getChildren( )` sul *root node* o su un nodo `Parent` per
ottenere una lista di nodi figli nella forma di una `ObservableList<Node>` del package `javafx.collections`.
Tale classe si comporta analogamente ad una `java.util.List`, quindi sono a disposizione i metodi
di una lista quali ad esempio `add()`, `remove()`, `addAll()` e così via

```java
rootNode.getChildren().add(label);
```


Di seguito, un esempio di applicazione JavaFX con quanto descritto sino ad ora:

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class LifeCycle extends Application {

    public static void main(String[] args) {
        System.out.println("avvio dell'applicazione");
        launch(args);
    }

    @Override
    public void init() {
        System.out.println("esecuzione del metodo init");
    }

    @Override
    public void start(Stage stage) {
        System.out.println("esecuzione del metodo start");  // log per identificare esecuzione
        stage.setTitle("JavaFX life-cycle");                // imposta titolo allo stage
        FlowPane rootNode = new FlowPane();                 // root node di tipo FlowPane
        Label label = new Label("Hello world");             // label
        rootNode.getChildren().add(label);                  // aggiungi label a root
        Scene scene = new Scene(rootNode, 300, 200);        // aggiungi root alla scena
        stage.setScene(scene);                              // aggiungi scena allo stage
        stage.show();                                       // mostra lo stage
    }

    @Override
    public void stop() {
        System.out.println("esecuzione del metodo stop");
    }

}
```

### 2.2 Eventi e Controls

Un evento è un oggetto che descrive una azione all'interno del programma, come ad esempio l'utente che
schiaccia un pulsante. JavaFX usa il [delegation event model](https://www.developer.com/java/data/understanding-and-using-the-java-delegation-event-model.html)
per la gestione degli eventi. Il concetto è abbastanza semplice: una sorgente, come ad esempio un oggetto `Control`,
genera un evento e lo invia a uno o più *listeners* che lo gestiscono. Per ricevere un evento, il componente deve essere
registrato con la sorgente dell'evento stesso

La classe base per la definizione di un evento è `Event`, nel package `javafx.event.Event` che deriva da `java.util.EventObject`,
il che significa che JavaFX condivide le stesse funzionalità degli altri eventi java.

Gli eventi sono gestiti attraverso l'implementazione dell'interfaccia `EventHandler`, che è anch'essa un evento ed è una
*functional interface*, quindi tipicamente implementata da una lambda expression o una classe anonima

```java
@FunctionalInterface
public interface EventHandler<T extends Event> extends EventListener {
    void handle(T event);
}
```

Fondamentalmente ci sono due modi per specificare un handler per un evento: utilizzare il metodo `addEventHandler( )` 
definito nella classe `Node` oppure i più comodi metodi che gestiscono lo specifico evento e iniziano con il prefisso `setOn`
Gli eventi sono processati lungo tutto il *scene graph* attraverso la *dispatch chain* fino al target effettivo dell'evento, 
ovvero il *control* che ha generato l'evento stesso. In questo processo di discesa (event capturing) e risalita (event bubbling)
attraverso il grafo, è possibile intercettare l'evento attraverso un *event filter* nel processo di *capturing* in modo che
sia consumato da un altro elemento del grafo prima di arrivare all'effettivo controllo che lo ha generato.

Come dimostrazione del processo, prima di vedere l'applicazione nel suo insieme, introduco la classe `Button` del
package `javafx.scene.control` che fa proprio al caso descritto precedentemente. Quando un pulsante viene premuto,
viene generato un `ActionEvent` dal package `javafx.event`; è facile registrare un gestore per gli eventi *action* attraverso
il metodo

```java
final void setOnAction(EventHandler<ActionEvent> handler)
```

dove l'`EventHandler` passato come argomento sarà una lambda expression o male che vada una classe anonima.
Se il gestore dell'evento necessita di tempo per eseguire delle operazioni, utilizzare un thread separato per
non rallentare l'interfaccia dell'applicazione e non peggiorare la UX all'utente.

Di seguito, un esempio di applicazione JavaFX per la gestione di quanto detto:

```java
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class SchisalButun extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("pulsanti ed eventi");
        FlowPane rootNode = new FlowPane(10, 10); // root node di tipo FlowPane con margini agli elementi
        rootNode.setAlignment(Pos.CENTER); // allinea i children al centro
        Label label = new Label("Schisa 'l Butun"); // crea un nodo per visualizzare del testo

        Button left = new Button("Primo"); // crea il primo pulsante
        left.setOnAction( e -> label.setText(left.getText())); // aggiunge l'handler per l'azione del primo pulsante
        Button right = new Button("Secondo"); // crea il secondo pulsante
        right.setOnAction( e -> label.setText(right.getText())); // aggiunge l'handler per l'azione del secondo pulsante

        rootNode.getChildren().addAll(left, right, label); // aggiunge tutti i nodi alla radice
        Scene scene = new Scene(rootNode, 400, 200); // aggiunge la radice alla scena
        stage.setScene(scene); // aggiunge la scena allo stage
        stage.show(); // mostra lo stage
    }

}
```

## 3. FXML

Aggiungere Controls, eventi e stili grafici alla applicazione diventa complicato per progetti più complessi di quanto visto fino ad ora.

[Introduction to FXML](https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/doc-files/introduction_to_fxml.html)
### 3.1 Definizione di un grafo
Uno *scene graph* può essere costruito attraverso un file xml, per la precisione un file `fxml` dove vengono definiti layouts e nodes che
descrivono la scena. Prima di vedere il contenuto di un file `fxml`, bisogna decidere come salvarlo nella struttura del progetto utilizzando
la cartella resources:
1. creare un albero di directory personalizzato in cui organizzare scene, immagini, e contenuti utilizzati dall'applicazione.<br/>
   In questo per accedere a essi da una classe java occorre inizializzare il *root node* come segue:
   ```java
   Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("scene.fxml"));
   ```
2. ricreare sotto resources il package dell'applicazione, ad esempio io.github.mpao...<br/>
   In questo per accedere a essi da una classe java occorre inizializzare il *root node* come segue:
   ```java
   Parent root = FXMLLoader.load(this.getClass().getResource("scene.fxml"));
   ```

Quindi spostare la definizione di una scena in file xml semplifica molto la gestione del codice e si riduce a:

```java
@Override
public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(this.getClass().getResource("scene.fxml"));
    stage.setScene(new Scene(root));
    stage.setTitle("app name");
    stage.show();
}
```

```html
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>

<HBox xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml"
            prefHeight="600" prefWidth="400"
            style="-fx-background-color: lightgrey;" >
    <Label>Hello World</Label>
</HBox>
```

### 3.2 Definizione degli eventi

Una volta definita la scena, rimane da gestire gli eventi che agiscono sui `Controls`. Per farlo, si delega tutto ad un *controller*, una classe
java dedicata a definire le azioni da compiere al verificarsi di un evento

```html
<HBox xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml"
            fx:controller="io.github.mpao.MainAppController"
            prefHeight="600" prefWidth="400"
            style="-fx-background-color: lightgrey;" >
    <Button fx:id="clickme" onAction="#hello">Hello</Button>
</HBox>
```

- `fx:controller`: definisce la classe delegata
- `fx:id`: identifica univocamente il `Control`
- `onAction`: definisce il metodo da eseguire al verificarsi di una azione

Nel *controller* posso iniettare il `Control` attraverso l'annotazione `@FXML` e definire le azioni

```java
public class MainAppController {
    
    @FXML public Button clickme;

    public void hello(ActionEvent actionEvent) {
        System.out.println(clickme.getText());
    }

}
```
### 3.3 Definizione degli stili

Il meccanismo visto finora è semplice ed è un pattern comune, come ad esempio nelle applicazioni Android, 
nelle PWA e via dicendo. Quindi è logico che allo stesso modo anche JavaFX permetta di separare la definizione
di una scena dal proprio aspetto estetico, così come dai controllers e analogamente agli ambienti nominati qui sopra.

- FXML -> Chi sei
- Controller -> Cosa fai
- CSS -> Come sei

Uno stylesheet si può legare ad una scena attraverso codice 

```java
rootNode.getStylesheets().add("styles.css");
```

anche se trovo più organizzato, più logico, legare tali stili alla definizione della scena nel file `fxml`, 
lasciando al codice soltanto la gestione della logica dell'applicazione. Quindi creo il mio *style.css*

```css
.container {
    -fx-font: 16px "Serif";
    -fx-padding: 10;
    -fx-background-color: #FF0000;
    -fx-pref-width: 300px;
    -fx-pref-height: Infinity;
}
```

e lo abbino alla mia scena

```html
<HBox xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml"
            fx:controller="io.github.mpao.calamaio.MainAppController"
            stylesheets="@styles.css"
            prefHeight="Infinity" prefWidth="Infinity">
	<HBox styleClass="container">
		<Button fx:id="clickme" onAction="#hello">Hello</Button>
	</HBox>
</HBox>
```

Informazioni: [JavaFX CSS Reference Guide](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html)

### 3.4 Gestione dei controller

Ora non rimane che eliminare la tentazione di creare un controller enorme, che gestisca tutto, quello che
su Android si definirebbe una *God Activity*. Prima di procedere, prendo ad esempio una scena composta da due
layouts affiancati, che al loro interno possono essere complicati, ma che rimangono comunque due diverse identità
e sarebbe meglio che ognuno di essi avesse il proprio controller per definirne i comportamenti.

```html
<HBox xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml"
            stylesheets="@styles.css"
            styleClass="container">
    <!-- primo layout -->
	<HBox styleClass="drawer">
		<ToggleButton fx:id="clickme" onAction="#hello">Hello</ToggleButton>
	</HBox>
    <!-- secondo layout -->
	<VBox styleClass="main" HBox.hgrow="ALWAYS">
		<Label styleClass="label" fx:id="target">DISABLED</Label>
	</VBox>
</HBox>
```

Il controller può essere assegnato **solo** al tag che fa da radice, quindi va da sé che sia unico per ogni
layout. Il primo pensiero che viene è utilizzare la direttiva `<fx:include>` e separare i layouts *drawer* e
*main* nei rispettivi file, creando i rispettivi controller, vedi [nested controllers](https://docs.oracle.com/javafx/2/api/javafx/fxml/doc-files/introduction_to_fxml.html#nested_controllers).
```html
<HBox xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml"
            fx:controller="io.github.mpao.MainAppController"
            stylesheets="@styles.css"
            styleClass="container">
	<fx:include source="drawer.fxml" />
	<fx:include source="main.fxml" />
</HBox>
```

Risolto il problema ? Sì, ma la lettura dello *scene graph* non è facile, dato l'utilizzo
delle direttive *include* e poi non è il massimo per la riusabilità dei componenti.<br/>
Forse [esiste](https://docs.oracle.com/javafx/2/fxml_get_started/custom_control.htm) un modo diverso, come i *custom control*.<br/>

Partendo dai layout precedenti, l'obiettivo è ottenerne uno simile al seguente, creando i componenti
personalizzati e i loro relativi controller estendendo quelli base forniti dalla sdk. Questo approccio permette
una migliore leggibilità e un possibile riuso dei componenti. Va da sé che devono essere componenti importanti,
complessi, e non dei semplici mock come quelli che uso qui, altrimenti il gioco non vale la candela.

```html
<HBox xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml"
            fx:controller="io.github.mpao.MainAppController"
            stylesheets="@styles.css"
            styleClass="container">
	<Drawerlayout fx:id="drawer" />
	<MainLayout fx:id="main" />
</HBox>
```

Riporto solo quanto riguardo il component *DrawerLayout*, poiché i contenuti sono analoghi anche per l'altro *Control*.
Questo approccio lo preferisco per progetti complessi, anche se un fastidioso errore falso-posivito viene prodotto da IntelliJ 
per l'attributo `onAction` che non trova nessun controller associato, perché oltre ai vantaggi elencati prima, permette di
abbinare un *css* ad un singolo componente e creare un package dedicato con `fxml`,`css`,`controller` facilmente modificabile e
riutilizzabile.

```html
<!-- drawer.fxml -->
<fx:root type="javafx.scene.layout.HBox"
         xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml"
         stylesheets="@styles.css"
         styleClass="drawer">
	<ToggleButton fx:id="clickme" onAction="#hello">Hello</ToggleButton>
</fx:root>
```

```java
public class DrawerLayout extends HBox {

    @FXML public ToggleButton clickme;

    public DrawerLayout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("drawer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    @FXML
    public void hello(ActionEvent actionEvent) {
        System.out.println(clickme.getText());
    }
}
```

Rimane da [condividere](https://stackoverflow.com/a/14190310/1588252) le azioni e i dati tra i due layout, che a 
seconda della complessità dell'applicazione va trattata caso per caso. Un esempio facile facile è
passare una reference del layout target al layout soggetto dell'evento

```java
@Override
public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(this.getClass().getResource("scene.fxml"));
    DrawerLayout drawer = (DrawerLayout) root.getChildrenUnmodifiable().get(0);
    MainLayout main = (MainLayout) root.getChildrenUnmodifiable().get(1);
    drawer.setTarget(main);
    ...
}
```

## 4. Packaging e distribuzione

I fat-jar non [funzionano](https://stackoverflow.com/a/52571719/1588252).
```bash
      java --module-path \javafx-sdk-14.0.1\lib
            --add-modules javafx.controls,javafx.fxml
            -jar app.jar
```
- packaging per distribuzione
- icona e splashscreen
- jre da allegare

https://docs.oracle.com/en/java/javase/11/tools/jlink.html

http://launch4j.sourceforge.net/
























