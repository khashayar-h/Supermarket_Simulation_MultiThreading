import java.util.concurrent.Semaphore;

class Cashier extends Thread {

    String cashierCode;
    String customerCode;
    Semaphore cash1 = new Semaphore(1, true);
    Semaphore cash2 = new Semaphore(1, true);
    Semaphore cash3 = new Semaphore(1, true);

    public Cashier(String customerCode)
    {
        this.customerCode = customerCode;
    }
    public void run()
    {
        try
        {
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
