package pl.jkrajniak.cardtracker.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import pl.jkrajniak.cardtracker.model.AppDatabase;
import pl.jkrajniak.cardtracker.model.Card;

public class CardRespository extends AndroidViewModel {
    private String dbName = "card-db";

    private AppDatabase appDatabase;
    public CardRespository(@NonNull Application app) {
        super(app);
        appDatabase = Room.databaseBuilder(
                app.getApplicationContext(),
                AppDatabase.class, dbName).build();
    }

    public void insertCard(String cardName, int numberOfTransactions) {
        Card card = new Card();
        card.setName(cardName);
        card.setCurrentNumTransactions(0);
        card.setRequiredNumTransactions(numberOfTransactions);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.daoCard().insertAll(card);
                return null;
            }
        }.execute();
    }

    public LiveData<Card> getCard(int id) {
        return appDatabase.daoCard().getCard(id);
    }

    public LiveData<List<Card>> getCards() {
        return appDatabase.daoCard().getAll();
    }
}
