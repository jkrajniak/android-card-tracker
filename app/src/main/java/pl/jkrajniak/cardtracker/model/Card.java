package pl.jkrajniak.cardtracker.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

@Entity
public class Card {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int uid;

    @ColumnInfo(name="name")
    @NonNull
    private String name;

    @ColumnInfo(name="requiredNumTransactions")
    private int requiredNumTransactions = 0;

    @ColumnInfo(name="currentNumTransactions")
    private int currentNumTransactions = 0;

    @ColumnInfo(name="cycleStartsOnDay")
    private int cycleStartsOnDay = 1;

    @Ignore
    public Card(String name, int numTransaction, int cycleStartsOnDay) {
        this.name = name;
        this.requiredNumTransactions = numTransaction;
        this.cycleStartsOnDay = cycleStartsOnDay;
    }

    public Card() {
        this.name = "";
        this.requiredNumTransactions = 0;
        this.cycleStartsOnDay = 1;
        this.currentNumTransactions = 0;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRequiredNumTransactions() {
        return requiredNumTransactions;
    }

    public void setRequiredNumTransactions(int requiredNumTransactions) {
        this.requiredNumTransactions = requiredNumTransactions;
    }

    public int getCurrentNumTransactions() {
        return currentNumTransactions;
    }

    public void setCurrentNumTransactions(int currentNumTransactions) {
        this.currentNumTransactions = currentNumTransactions;
    }

    public int getCycleStartsOnDay() { return cycleStartsOnDay; }
    public void setCycleStartsOnDay(int s) { cycleStartsOnDay = s; }

    /**
     * Return the number of days left to the end of current cycle.
     * @return int number of days
     */
    public long getDaysLeft() {
        Calendar today = Calendar.getInstance();
        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH, cycleStartsOnDay);
        thatDay.set(Calendar.MONTH, (today.get(Calendar.MONTH)+1) % 12);
        long diffMillis = thatDay.getTimeInMillis() - today.getTimeInMillis();
        long diffDays = diffMillis / (24 * 60 * 60 * 1000);
        return diffDays - 1;
    }
}
