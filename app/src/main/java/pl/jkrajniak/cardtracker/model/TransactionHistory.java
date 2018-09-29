package pl.jkrajniak.cardtracker.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.SET_NULL;

@Entity(foreignKeys = @ForeignKey(entity = Card.class, parentColumns = "uid",
childColumns = "cardId", onDelete = SET_NULL))
public class TransactionHistory {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name="cardId")
    private int cardId;

    @ColumnInfo(name="numTransactions")
    private int numTransactions = 0;

    @ColumnInfo(name = "createDate")
    private Date createDate;
}
