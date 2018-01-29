import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Playgroud per Stream e Optional.
 * Tristi notizie per Android, dove è utilizzabile da
 * Nougat 7.0 API level 24
 */
public class StreamNotes {

    public static void main(String[] args){

        MyData a = new MyData("The Answer", 42);
        MyData b = new MyData("Another Value", 7);
        MyData c = new MyData("Last Value", 12);
        List<MyData> list = Arrays.asList(a,b,c);

        // lista come stream, filtro per cercare un nome, e per
        // ogni risultato ottenuto scrivo qualcosa
        list.stream()
                .filter( s -> s.getName().equals("The Answer") )
                .forEach( s -> System.out.println(s.getName() ) );

        // dallo stream ricava un nuovo stream di double applicando
        // la funzione passata come parametro. Su questo nuovo stream
        // faccio la media e mando in stampa se non nulla
        list.stream()
                .mapToDouble( MyData::getNumber )
                .average()
                .ifPresent(System.out::println);

        // stessa cosa di prima, ma questa volta salvo l'OptionalDouble
        // e mando in output il valore, e se nullo mando output 0
        OptionalDouble m = list.stream()
                .mapToDouble( MyData::getNumber )
                .average();
        System.out.println( "m = "+ String.valueOf(m.orElse(0)));

        // trasformo la lista in uno stream, e filtro cercando un valore
        // attraverso la relativa funzione. Dopo di che ricreo lo stream
        // dei valori trovati e ne prendo il primo valore, mandandolo in output
        Optional<String> son = list.stream()
                .filter( s -> s.getName().equals("This is missing"))
                .map(MyData::getName)
                .findFirst();
        System.out.println( son.orElse("Not Found") );

        // flatMap è più complicato, leggi doc, che contiene anche esempi
        // The flatMap() operation has the effect of applying a one-to-many
        // transformation to the elements of the stream, and then flattening
        // the resulting elements into a new stream.
        // Se l'oggetto MyData, contiene una qualche collezione, con flatMap
        // posso ottenere un nuovo stream concatenando tutte queste collezioni
        // e operare poi come negli esempi precedenti
        Stream.of(a,b,c)
                .flatMap( myData -> myData.flatting.stream() )
                .map(String::toUpperCase)
                .forEach(System.out::println);

        // sequenza di fibonacci ottenura attraverso stream e programmazione funzionale
        // 12 elementi analizzati
        Stream.iterate( new int[]{1, 1}, fib -> new int[] {fib[1], fib[0] + fib[1]} )
                .mapToInt(fib -> fib[0])
                .limit(12)
                .forEach(fib -> System.out.print(fib + " "));
                // Output: 1 1 2 3 5 8 13 21 34 55 89 144

        for(int i = 1; i  <= 12; i++) {
            System.out.println();
            System.out.print(StreamNotes.fibonacci(i) +" ");
            // Output: 1 1 2 3 5 8 13 21 34 55
        }

    }

    private static int fibonacci(int number) {
        int fib1 = 1;
        int fib2 = 1;
        int fibonacci = fib1;
        for (int i = 2; i < number; i++) {
            fibonacci = fib1 + fib2;
            fib1 = fib2;
            fib2 = fibonacci;
        }
        return fibonacci;
    }

    private static class MyData{

        private String name;
        private int number;
        private List<String> flatting = Arrays.asList("foo","bar","baz");

        private String getName() {
            return name;
        }

        private int getNumber() {
            return number;
        }

        private MyData(String name, int number){
            this.name = name;
            this.number = number;
        }

    }

}
