import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Event {
    private final ReentrantReadWriteLock rwIdLock = new ReentrantReadWriteLock();
    private final Lock readIdLock = rwIdLock.readLock();
    private final Lock writeIdLock = rwIdLock.writeLock();
    private final int id;

    private final ReentrantReadWriteLock rwTitleLock = new ReentrantReadWriteLock();
    private final Lock readTitleLock = rwTitleLock.readLock();
    private final Lock writeTitleLock = rwTitleLock.writeLock();
    private String title;

    private final ReentrantReadWriteLock rwDescriptionLock = new ReentrantReadWriteLock();
    private final Lock readDescriptionLock = rwDescriptionLock.readLock();
    private final Lock writeDescriptionLock = rwDescriptionLock.writeLock();
    private String description;

    private final ReentrantReadWriteLock rwTargetLock = new ReentrantReadWriteLock();
    private final Lock readTargetLock = rwTargetLock.readLock();
    private final Lock writeTargetLock = rwTargetLock.writeLock();
    private double target;

    private final ReentrantReadWriteLock rwDeadlineLock = new ReentrantReadWriteLock();
    private final Lock readDeadlineLock = rwDeadlineLock.readLock();
    private final Lock writeDeadlineLock = rwDeadlineLock.writeLock();
    private Date deadline;

    private final Account account;



    public Event(int id, String title, String description, double target, String deadline) throws ParseException {
        this.id = id;
        this.title = title;
        this.description = description;
        this.target = target;
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        this.deadline = isoFormat.parse(deadline);
        account = new Account();
    }

    public int getId() {
        readIdLock.lock();
        try {
            return id;
        } finally {
            readIdLock.unlock();
        }
    }

    public String getTitle() {
        readTitleLock.lock();
        try {
            return title;
        } finally {
            readTitleLock.unlock();
        }
    }

    public void setTitle(String title) {
        writeTitleLock.lock();
        try {
            this.title = title;
        } finally {
            writeTitleLock.unlock();
        }
    }

    public String getDescription() {
        readDescriptionLock.lock();
        try{
            return description;
        } finally {
            readDescriptionLock.unlock();
        }
    }

    public void setDescription(String description) {
        writeDescriptionLock.lock();
        try{
            this.description = description;
        } finally {
            writeDescriptionLock.unlock();
        }
    }

    public double getTarget() {
        readTitleLock.lock();
        try{
            return target;
        } finally {
            readTargetLock.unlock();
        }
    }

    public void setTarget(double target) {
        writeTargetLock.lock();
        try{
            this.target = target;
        } finally {
            writeTargetLock.unlock();
        }
    }

    public Date getDeadline() {
        readDeadlineLock.lock();
        try{
            return deadline;
        } finally {
            readDeadlineLock.unlock();
        }
    }

    public String getDeadlineString() {
        readDeadlineLock.lock();
        try{
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            df.setTimeZone(tz);
            return df.format(this.deadline);
        } finally {
            readDeadlineLock.unlock();
        }
    }

    public void setDeadline(Date deadline) {
        writeDeadlineLock.lock();
        try {
            this.deadline = deadline;
        } finally {
            writeDeadlineLock.unlock();
        }
    }

    public void setDeadlineByString(String deadline) throws ParseException {
        writeDeadlineLock.lock();
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            this.deadline = isoFormat.parse(deadline);
        } finally {
            writeDeadlineLock.unlock();
        }
    }

    public Account getAccount() {
        return account;
    }
}