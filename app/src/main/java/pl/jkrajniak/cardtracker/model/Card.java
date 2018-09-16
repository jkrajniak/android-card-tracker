package pl.jkrajniak.cardtracker.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Card {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="requiredNumTransactions")
    private int requiredNumTransactions = 0;

    @ColumnInfo(name="currentNumTransactions")
    private int currentNumTransactions = 0;

    @Ignore
    public Card(String name, int numTransaction) {
        this.name = name;
        this.requiredNumTransactions = numTransaction;
    }

    public Card() {
        this.name = "";
        this.requiredNumTransactions = 0;
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
}
