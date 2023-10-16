import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Account {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    private double balance = 0.0;

    public double getBalance() {
        readLock.lock();
        try {
            return balance;
        } finally {
            readLock.unlock();
        }
    }

    public void deposit(double amount) {
        writeLock.lock();
        try {
            balance += amount;
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            writeLock.unlock();
        }
    }
}
