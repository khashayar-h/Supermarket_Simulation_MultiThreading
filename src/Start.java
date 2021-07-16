import java.util.concurrent.Semaphore;

public class Start {

    public static void main( String args[] ) {

        //semaphores for number of entrance gates, capacity of Store and exit gates

        Semaphore gate = new Semaphore(2, true);
        Semaphore capacity = new Semaphore(500, true);
        Semaphore exit = new Semaphore(1, true);

        // create as many as customer Threads you want in a for loop and start it

        for (int customerCount = 1; customerCount <=3; customerCount++) {
            Customer t = new Customer(exit, capacity, gate, Integer.toString(customerCount));
            t.start();
        }
    }
}