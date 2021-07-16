import java.util.concurrent.Semaphore;

public class Start {

    public static void main( String args[] ) {

        Semaphore gate = new Semaphore(2, true);
        Semaphore capacity = new Semaphore(500, true);
        Semaphore exit = new Semaphore(1, true);

        for (int customerCount = 1; customerCount <=10; customerCount++) {
            Customer t = new Customer(exit, capacity, gate, Integer.toString(customerCount));
            t.start();
        }
    }
}