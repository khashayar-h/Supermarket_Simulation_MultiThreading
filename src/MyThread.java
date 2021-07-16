
import java.util.Random;
import java.util.concurrent.Semaphore;

 class MyThread extends Thread
{
    String ThreadName;
    int waitingTime;
    Semaphore capacity;
    Semaphore gate;
    Semaphore cashier;
    Semaphore cash1 = new Semaphore(1,true);
    Semaphore cash2 = new Semaphore(1, true);
    Semaphore cash3 = new Semaphore(1, true);
    Semaphore exit = new Semaphore(1, true);

    public MyThread(Semaphore cashier , Semaphore capacity,Semaphore gate, String ThreadName)
    {
        this.cashier = cashier;
        this.capacity = capacity;
        this.gate = gate;
        this.ThreadName = ThreadName;
    }

    public void run()
    {
        try {
            capacity.acquire();
            gate.acquire();
            System.out.println("At : "+java.time.LocalTime.now()+" Acquiring Gate for customer : "+ this.ThreadName);

            Random random = new Random();
            this.waitingTime = random.nextInt(10);
            System.out.println("At : "+java.time.LocalTime.now()+" Shopping Time for customer : "+ this.ThreadName + " is : "+ this.waitingTime);

            gate.release();
            System.out.println("At : "+java.time.LocalTime.now()+" Releasing Gate for customer : "+ this.ThreadName);

            Thread.sleep(waitingTime*1000);
            System.out.println("At : "+java.time.LocalTime.now()+" waiting finished for customer : "+ this.ThreadName);

            Semaphore Cashier = Cashier();
            Cashier.acquire();
            System.out.println("At : "+java.time.LocalTime.now()+" Customer : "+ this.ThreadName +" went to Cashier : ");
            Cashier.release();

            exit.acquire();
            System.out.println("At : "+java.time.LocalTime.now()+" Customer : "+ this.ThreadName +" Exiting...... ");
            exit.release();
            capacity.release();

        }
        catch (InterruptedException e)

        {
            e.printStackTrace();
        }
    }

    public Semaphore  Cashier() {

        Semaphore s = null;

        if (cash1.availablePermits() >= cash2.availablePermits() && cash1.availablePermits() >= cash3.availablePermits()) {
            s = cash1;
        } else if (cash2.availablePermits() > cash1.availablePermits() && cash2.availablePermits() > cash3.availablePermits()) {
            s = cash2;
        } else if (cash3.availablePermits() > cash1.availablePermits() && cash3.availablePermits() > cash2.availablePermits()) {
            s = cash3;
        }

        return s;
    }

}



