import java.util.concurrent.Semaphore;

public class Start {

    public static void main( String args[] ) {

        Semaphore gate = new Semaphore(2, true);
        Semaphore capacity = new Semaphore(500, true);
        Semaphore cashier = new Semaphore(3, true);

        for (int customerCount = 5; customerCount > 0; customerCount--) {
            MyThread t = new MyThread(cashier, capacity, gate, Integer.toString(customerCount));
            t.start();
        }
    }
}