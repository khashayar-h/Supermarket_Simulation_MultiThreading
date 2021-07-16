import java.util.concurrent.Semaphore;

// Cashier Thread Class which inherits Java Thread class for creating connections between customers and cashiers

class Cashier extends Thread {

    String cashierCode;
    String customerCode;

    // we have 3 cash registers in our store so we create 3 semaphores for them

    Semaphore cash1 = new Semaphore(1, true);
    Semaphore cash2 = new Semaphore(1, true);
    Semaphore cash3 = new Semaphore(1, true);

    //constructor method for Cashier Class to create a relation between customer and cashiers

    public Cashier(String customerCode)
    {
        this.customerCode = customerCode;
    }

    // run method of Thread which is implemented in Customer Class

    public void run()
    {
        try
        {

            // chose the cashier with shortest queue and assign it to the current customer

            if (cash1.availablePermits() >= cash2.availablePermits() && cash1.availablePermits() >= cash3.availablePermits()) {
                cash1.acquire();
                this.cashierCode = "A";
                cash1.release();
            } else if (cash2.availablePermits() > cash1.availablePermits() && cash2.availablePermits() > cash3.availablePermits()) {
                cash2.acquire();
                this.cashierCode = "B";
                cash2.release();
            } else if (cash3.availablePermits() > cash1.availablePermits() && cash3.availablePermits() > cash2.availablePermits()) {
                cash3.acquire();
                this.cashierCode = "C";
                cash3.release();
            }

            System.out.println("At : " + java.time.LocalTime.now() + " Customer : " + customerCode + " went to cashier : " + cashierCode);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
