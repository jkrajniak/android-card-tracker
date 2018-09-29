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

public class CardRespository {
    private DaoCard daoCard;
    private LiveData<List<Card>> allCards;
    private AppDatabase appDatabase;

    public CardRespository(Application app) {
        AppDatabase appDatabase = AppDatabase.getDatabase(app);
        daoCard = appDatabase.daoCard();
        allCards = daoCard.getAll();
    }

    LiveData<List<Card>> getAllCards() {
        return allCards;
    }

    public void insert (Card card) {
        new insertAsyncTask(daoCard).execute(card);
    }

    private static class insertAsyncTask extends AsyncTask<Card, Void, Void> {

        private DaoCard asyncDaoCard;

        insertAsyncTask(DaoCard dao) {
            asyncDaoCard = dao;
        }

        @Override
        protected Void doInBackground(Card... cards) {
            asyncDaoCard.insertAll(cards);
            return null;
        }
    }

    public void update(Card card) {
        new updateAsyncTask(daoCard).execute(card);
    }

    private static class updateAsyncTask extends AsyncTask<Card, Void, Void> {

        private final DaoCard asyncDaoCard;

        updateAsyncTask(DaoCard daoCard) {
            asyncDaoCard = daoCard;
        }

        @Override
        protected Void doInBackground(Card... cards) {
            asyncDaoCard.updates(cards);
            return null;
        }
    }

}
