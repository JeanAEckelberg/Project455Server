import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Event {
    // Event object with read/write reentrant locks, allowing many reads but only a single write that bars any reading
    // Also serializable for easy transmission to client
    private final ReentrantReadWriteLock rwIdLock = new ReentrantReadWriteLock();
    private final Lock readIdLock = rwIdLock.readLock();
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

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    private double balance = 0.0;


    @JsonCreator
    public Event(@JsonProperty("id") int id,
                 @JsonProperty("title") String title,
                 @JsonProperty("description") String description,
                 @JsonProperty("target") double target,
                 @JsonProperty("currentAmount") double currentAmount,
                 @JsonSetter("deadline") String deadline) throws ParseException {
        this.id = id;
        this.title = title;
        this.description = description;
        this.target = target;
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        this.deadline = isoFormat.parse(deadline);
        this.balance = currentAmount;
    }

    @JsonGetter("id")
    public int getId() {
        readIdLock.lock();
        try {
            return id;
        } finally {
            readIdLock.unlock();
        }
    }

    @JsonGetter("title")
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

    @JsonGetter("description")
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

    @JsonGetter("target")
    public double getTarget() {
        readTargetLock.lock();
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

    @JsonGetter("deadline")
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

    @JsonGetter("currentAmount")
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
        } finally {
            writeLock.unlock();
        }
    }
}