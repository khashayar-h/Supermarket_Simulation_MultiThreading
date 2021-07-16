import java.util.Random;
import java.util.concurrent.Semaphore;

 // our custom Thread class for Customer Threads which inherits Java Thread class

 class Customer extends Thread {
     String ThreadName;
     int waitingTime;
     Semaphore capacity;
     Semaphore gate;
     Semaphore exit;

     //Constructor method for Customer Class

     public Customer(Semaphore exit, Semaphore capacity, Semaphore gate, String ThreadName) {
         this.exit = exit;
         this.capacity = capacity;
         this.gate = gate;
         this.ThreadName = ThreadName;
     }

     //Thread run method which can be implemented by Thread.start() in the main class

     public void run() {
         try {

             //Wait on Store Capacity and gate Semaphore which is 2(for 2 customers simultaneously)

             capacity.acquire();
             gate.acquire();
             System.out.println("At : " + java.time.LocalTime.now() + " Acquiring Gate for customer : " + this.ThreadName);

             //Generate a random Shopping time for the current Customer(Thread)

             Random random = new Random();
             this.waitingTime = random.nextInt(20);
             System.out.println("At : " + java.time.LocalTime.now() + " Shopping Time for customer : " + this.ThreadName + " is : " + this.waitingTime);

             //The Customer passes the Entrance Gate and Signal on gate semaphore

             gate.release();
             System.out.println("At : " + java.time.LocalTime.now() + " Releasing Gate for customer : " + this.ThreadName);

             //The Random Time Generated before finishes now with Thread.Sleep() Then Customer goes to the Cashier

             Thread.sleep(waitingTime * 1000);
             System.out.println("At : " + java.time.LocalTime.now() + " Shopping finished for customer : " + this.ThreadName);

             //Another Thread will be made here to connect current costumer to one of the cashiers in Cashier Class

             Cashier c = new Cashier(this.ThreadName);
             c.start();
             c.join();

             //Customer goes to Exit gate and Wait on exit semaphore then Signal on exit and capacity semaphore to increase the capacity after exit

             System.out.println("At : " + java.time.LocalTime.now() + " Customer : " + this.ThreadName + " Exiting...... Free Spaces : " + capacity.availablePermits());
             exit.acquire();
             exit.release();
             capacity.release();

         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }


 }



