## STRATEGY

**Scopo**

Definire una famiglia di algoritmi, incapsularli e renderli intercambiabili.
Strategy permette agli algoritmi di variare indipendentemente dai client che ne fanno uso.

**Applicabilità**

È opportuno usare tale pattern nei seguenti casi:
* Molte classi correlate differiscono fra loro solo per il comportamento.
* Sono necessarie più varianti di un algoritmo
* Un algoritmo usa una struttura dati che non dovrebbe essere resa nota ai client.
* Una classe definisce molti comportamenti che compaiono all'interno di scelte condizionali multiple.

**Partecipanti**

* `Strategy`: dichiara una interfaccia comune a tutti gli algoritmi supportati. *Context* usa 
questa interfaccia per invocare l'algoritmo definito da un *ConcreteStragedy*
* `ConcreteStrategy`: implementa l'algoritmo usando l'interfaccia *Strategy*
* `Context`:  è configurato con un oggetto *ConcreteStrategy*, contiene un riferimento ad un oggetto *Strategy*,
definisce un metodo che permette a *Strategy* di accedere alla propria struttura dati

![strategy](resources/strategy.png)

**Conseguenze**

L'uso del pattern Strategy ha le seguenti conseguenze:
1. `Famiglie di algoritmi correlati`: le gerarchie di classi Strategy definiscono una
famiglia di algoritmi o comportamenti riusabili per i contesti.
2. `Una alternativa all'ereditarietà`: l'ereditarietà offre un altro modo per implementare svariati
comportamenti, ma questo approccio lega staticamente il comportamento nel *Context* e mescola
l'implementazione del Context con quella del comportamento, rendendo Context più difficile da
capire, manutenere ed estendere. Quello che si ottiene usando l'ereditarietà, è una famiglia
di classi la cui unica differenze consiste nell'algoritmo che ognuna di esse implementa.
3. `Le strategie eliminano i blocchi condizionali`: Strategy offre un'alternativa ai blocchi `switch`
per determinare il comportamento desiderato. Spesso quando si è di fronte ad un blocco *switch*
con molte opzioni, è il caso di introdurre il pattern Strategy.

**Implementazione**

```kotlin
/* * * * * * * *
 * Java Style
 * * * * * * * */
//Strategy
interface Weapon{
    fun hit()
}

//ConcreteStrategy
data class Hands(val min: Int, val max: Int) : Weapon{
    override fun hit() = println("Beats for ${(min..max).random()} points")
}

//ConcreteStrategy
data class Sword(val min: Int, val max: Int): Weapon{
    override fun hit() = println("Slices for ${(min..max).random()} points")
}

//Context
class Hero() {
    var weapon: Weapon = Hands(0,2)
    fun fight() = weapon.hit()
}

//Application
fun main(){
    val hero = Hero()
    hero.fight()
    hero.weapon = Sword(3,12)
    hero.fight()
    // new weapon defined "on the fly"
    hero.weapon = object : Weapon {
        override fun hit() = println("Picks up new Weapon and hits for ${(6..20).random()} points")
    }
    hero.fight()
}

/* * * * * * * *
 * Kotlin idiomatic Style
 * * * * * * * */
//Strategy
object Weapon {
    //ConcreteStrategy
    val hands = fun(min:Int, max: Int) = println("Beats for ${getDamage(min,max)} points")
    val sword = fun(min:Int, max: Int) = println("Slices for ${getDamage(min,max)} points")
    private fun getDamage(min:Int, max: Int) = (min..max).random()
}

//Context
class Hero {
    var weapon = Weapon.hands(0,2)
    val fight = fun() { weapon }
}

//Application
fun main(){
    val hero = Hero()
    hero.fight()
    hero.weapon = Weapon.sword(3,12)
    hero.fight()
    // new weapon defined "on the fly"
    hero.weapon = println("Picks up new Weapon and hits for ${(6..20).random()} points")
    hero.fight()
}
```


