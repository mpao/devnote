# Design Patterns


### Patterns Creazionali

- **Abstract Factory** - [def](https://github.com/mpao/java-design-patterns/tree/master/abstract-factory)<br/>
Fornisce un'interfaccia per creare famiglie di oggetti dipendenti e correlati tra loro, in modo che non ci sia necessità da parte 
dei client di specificare i nomi delle classi concrete all'interno del proprio codice. In altre parole, si potrebbe
definire anche come *"una fabbrica di fabbriche; una fabbrica che raggruppa singole fabbriche correlate e simili tra loro 
senza specificare le loro classi concrete"*.<p>
![diagram](https://github.com/mpao/devnote/blob/master/resources/abstract_factory.png)</p>

```kotlin
// how to use
fun main(args: Array<String>) {
    val abstractFactory = FactoryMaker() // could be static
    val elfFactory = abstractFactory.makeFactory( FactoryMaker.KingdomType.ELF)
    val orcFactory = abstractFactory.makeFactory( FactoryMaker.KingdomType.ORC)
    
    println( elfFactory.createKing().description)
    println( elfFactory.createArmy().description)
    println( orcFactory.createKing().description)
    println( orcFactory.createArmy().description)
}
// output
// This is the Elven king!
// This is the Elven Army!
// This is the Orchish king!
// This is the Orchish Army!
```

```kotlin
// stuff for implementation
class FactoryMaker {
    enum class KingdomType { 
      ELF, 
      ORC 
    }
    fun makeFactory(type: KingdomType): KingdomFactory {
        when (type) {
            FactoryMaker.KingdomType.ELF -> return ElfKingdomFactory()
            //similar implementation for Orchish legion
            //FactoryMaker.KingdomType.ORC -> return OrcKingdomFactory()
            else -> throw IllegalArgumentException("KingdomType not supported.")
        }
    }
}
interface Castle {
    val description: String
}

interface King {
    val description: String
}

interface Army {
    val description: String
}

interface KingdomFactory {
    fun createCastle(): Castle
    fun createKing(): King
    fun createArmy(): Army
}

// Elven implementations ->
class ElfKingdomFactory : KingdomFactory {
    override fun createCastle(): Castle {
        return ElfCastle()
    }
    override fun createKing(): King {
        return ElfKing()
    }
    override fun createArmy(): Army {
        return ElfArmy()
    }
}

class ElfCastle : Castle {
    override val description: String
        get() = DESCRIPTION

    companion object {
        internal val DESCRIPTION = "This is the Elven castle!"
    }
}

class ElfKing : King {
    override val description: String
        get() = DESCRIPTION

    companion object {
        internal val DESCRIPTION = "This is the Elven king!"
    }
}

class ElfArmy : Army {
    override val description: String
        get() = DESCRIPTION

    companion object {
        internal val DESCRIPTION = "This is the Elven Army!"
    }
}
// Orchish implementations is similar
```

