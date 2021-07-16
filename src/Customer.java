
import java.util.Random;
import java.util.concurrent.Semaphore;

 class Customer extends Thread {
     String ThreadName;
     int waitingTime;
     Semaphore capacity;
     Semaphore gate;
     Semaphore exit;

     public Customer(Semaphore exit, Semaphore capacity, Semaphore gate, String ThreadName) {
         this.exit = exit;
         this.capacity = capacity;
         this.gate = gate;
         this.ThreadName = ThreadName;
     }

     public void run() {
         try {
             capacity.acquire();
             gate.acquire();
             System.out.println("At : " + java.time.LocalTime.now() + " Acquiring Gate for customer : " + this.ThreadName);

             Random random = new Random();
             this.waitingTime = random.nextInt(10);
             System.out.println("At : " + java.time.LocalTime.now() + " Shopping Time for customer : " + this.ThreadName + " is : " + this.waitingTime);

             gate.release();
             System.out.println("At : " + java.time.LocalTime.now() + " Releasing Gate for customer : " + this.ThreadName);

             Thread.sleep(waitingTime * 1000);
             System.out.println("At : " + java.time.LocalTime.now() + " Shopping finished for customer : " + this.ThreadName);

             Cashier c = new Cashier(this.ThreadName);
             c.start();
             c.join();

             System.out.println("At : " + java.time.LocalTime.now() + " Customer : " + this.ThreadName + " Exiting...... Free Spaces : " + capacity.availablePermits());
             exit.acquire();
             exit.release();
             capacity.release();

         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }


 }



