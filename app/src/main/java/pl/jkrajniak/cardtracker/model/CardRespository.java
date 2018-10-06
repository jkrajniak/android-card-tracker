package pl.jkrajniak.cardtracker.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.util.Log;

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class CardRespository {
    private DaoCard daoCard;
    private LiveData<List<Card>> allCards;
    private AppDatabase appDatabase;
    private Application app;

    public CardRespository(Application app) {
        AppDatabase appDatabase = AppDatabase.getDatabase(app);
        daoCard = appDatabase.daoCard();
        allCards = daoCard.getAll();
        this.app = app;
    }

    public LiveData<List<Card>> getAllCards() {
        return allCards;
    }

    public LiveData<Card> getCard(int uid) {
        return daoCard.getCard(uid);
    }

    public void insert (Card card) {
        new insertAsyncTask(daoCard).execute(card);
    }

    public void updateCardsCounter() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                List<Card> cards = daoCard.getAllCards();
                Calendar today = Calendar.getInstance();
                for (Card card : cards) {
                    if (!card.isUpdated() && today.get(Calendar.DAY_OF_MONTH) == card.getCycleStartsOnDay()) {
                        card.setCurrentNumTransactions(0);
                        card.setUpdated(true);
                        CardHistory cardHistory = new CardHistory(card.getUid(), card.getCurrentNumTransactions());
                        daoCard.saveHistory(cardHistory);
                    } else if (card.getDaysLeft() > 0) {
                        card.setUpdated(false);
                    }
                    daoCard.updates(card);
                }
                return null;
            }
        }.execute();
    }

    public void delete (Card card) {
        new deleteAsyncTask(daoCard).execute(card);
    }

    private static class deleteAsyncTask extends AsyncTask<Card, Void, Void> {
        private DaoCard asyncDaoCard;

        deleteAsyncTask(DaoCard dao) { asyncDaoCard = dao; }

        @Override
        protected Void doInBackground(Card... cards) {
            asyncDaoCard.deletes(cards);
            return null;
        }
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
