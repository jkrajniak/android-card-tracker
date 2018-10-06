package pl.jkrajniak.cardtracker.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CardHistory {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "card_id")
    public int cardId;

    @ColumnInfo(name = "num_transactions")
    public int numTransactions;

    @ColumnInfo(name = "timestamp")
    public int timestamp;

    @Ignore
    public CardHistory(int cardId, int numTransactions) {
        this.cardId = cardId;
        this.numTransactions = numTransactions;
    }

    public CardHistory() {
        this.cardId = 0;
        this.numTransactions = 0;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getNumTransactions() {
        return numTransactions;
    }

    public void setNumTransactions(int numTransactions) {
        this.numTransactions = numTransactions;
    }
}
