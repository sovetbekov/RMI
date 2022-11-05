import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implementation of the remote service.
 */
public class ServiceImpl extends UnicastRemoteObject implements Service {
    private final BlockingQueue<Integer> queue;
    ArrayList<Integer> numOfPrimes = new ArrayList<>();

    boolean inProcess = false;
    long start = 0;
    long end = 0;
    public ServiceImpl() throws RemoteException {
        super();
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public Integer pollElem() throws RemoteException, NullPointerException {

        if (!inProcess)
            start = System.nanoTime();

        inProcess = true;
        return this.queue.poll();
    }

    @Override
    public void addElem(int n) throws RemoteException {
        this.queue.add(n);
        System.out.println("Added: " + n);
    }

    @Override
    public void getResult(int num) throws RemoteException {
        numOfPrimes.add(num);

        if (queue.isEmpty()) {
            try {
                Thread.sleep((long) 11.11);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getSumOfPrimes(numOfPrimes);
        }
    }

    @Override
    public void getSumOfPrimes(ArrayList<Integer> list) {
        int product = 0;
        for (int num : list) {
            product += num;
        }

        end = System.nanoTime();

        System.out.println("Sum of primes: " + product);
        System.out.println("Time: " + (end - start)/1000000000);

    }
}