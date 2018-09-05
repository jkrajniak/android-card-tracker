package pl.jkrajniak.cardtracker.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DaoCard {
    @Query("SELECT * FROM card")
    LiveData<Card> getAll();

    @Insert
    void insertAll(Card... cards);

    @Delete
    void delete(Card card);

    @Query("SELECT  * FROM card WHERE uid = :id")
    LiveData<Card> getCard(int id);
}
