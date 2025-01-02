import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

public class BankService {
    private static final Logger LOGGER = Logger.getLogger(BankService.class.getName());
    // 控制线程并发数
    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void transfer(Account from, Account to, double amount) throws InterruptedException, NotEnoughMoneyException {
        // 使用锁来保证转账操作的原子性
        synchronized (from.getClass()) {
            synchronized (to.getClass()) {
                if (from.getBalance() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                    logTransaction(from, to, amount, true);
                } else {
                    logTransaction(from, to, amount, false);
                    throw new NotEnoughMoneyException("Not enough money in account: " + from.getBalance());
                }
            }
        }
    }

    private static void logTransaction(Account from, Account to, double amount, boolean success) {
        String status = success ? "Success" : "Failed";
        String message = String.format("Account %s transfers %s amounts to Account %s ，result : %s", from, to, amount, status);
        LOGGER.info(message);
    }

    public static void main(String[] args) {
        // 构造测试用例测试运行结果
        Account account1 = new Account(1000);
        Account account2 = new Account(500);

        try {
            for (int i = 0; i < 12; i++) {
                executor.submit(() -> {
                    try {
                        transfer(account1, account2, 100);
                    } catch (InterruptedException | NotEnoughMoneyException e) {
                        e.printStackTrace();
                    }
                });
            }
        } finally {
            // 关闭线程池
            executor.shutdown();
        }
    }

}

/**
 * 定义一个account类
 * 定义余额属性，保证读写余额的线程安全性（读/写锁）
 */
class Account {
    private double balance;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Account(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        lock.readLock().lock();
        try {
            return balance;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void withdraw(double amount) throws NotEnoughMoneyException {
        lock.writeLock().lock();
        try {
            if (balance < amount) throw new NotEnoughMoneyException("account Insufficient balance");
            balance -= amount;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void deposit(double amount) {
        lock.writeLock().lock();
        try {
            balance += amount;
        } finally {
            lock.writeLock().unlock();
        }
    }
}

//自定义异常类
class NotEnoughMoneyException extends Exception {
    //错误描述信息
    private String message;

    public NotEnoughMoneyException(String s) {
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}