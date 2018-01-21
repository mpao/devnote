import java.util.function.BinaryOperator;

public class LambdaExpressions {

    public static void main(String args[]){

        // esempio di  lambda expressions
        MyObjWithInt o = () -> 123;
        MyObjWithParam a = (n) -> (n % 2)==0;
        System.out.println( o.getInt() );
        System.out.println( o.getAnswer() );
        System.out.println( a.getBool( Math.random() ));
        System.out.println( MyObjWithParam.getAnswer(2));

        // esempio con generics
        MyObjGenerics<Integer> b = (n) -> 123 ;
        MyObjGenerics<Boolean> c = (n) -> !n;
        System.out.println( b.func(0));
        System.out.println( c.func( false ) );

        //calcolatrice
        int result;
        result = LambdaExpressions.calcola(5,2, (x,y) -> x + y);
        System.out.println(result);

        result = LambdaExpressions.calcola(5,2, (x,y) -> x * y);
        System.out.println(result);

    }

    /**
     * Functional interface con un metodo default
     */
    @FunctionalInterface
    interface MyObjWithInt{
        int getInt();
        default int getAnswer(){
            return 42;
        }
    }

    /**
     * Functional interface con parametri nei metodi
     */
    @FunctionalInterface
    interface MyObjWithParam{
        boolean getBool(double a);
        static int getAnswer( int param){
            return 42*param;
        }
    }

    /**
     * Funcional interface che utilizza generics
     * @param <T> tipo dell'interfaccia
     */
    @FunctionalInterface
    interface MyObjGenerics<T>{
        T func(T t);
    }

    /**
     * Esempio di metodo che utilizza una functional interface come attributo
     * per generalizzare l'operazione da eseguire sugli elementi
     * @param a primo elemento
     * @param b secondo elemento
     * @param operazione operazione binaria
     * @param <T> tipo del dato
     * @return restituisce il risultato dell'operazione
     */
    private static <T> T calcola(T a, T b, BinaryOperator<T> operazione) {
        return operazione.apply(a, b);
    }

}