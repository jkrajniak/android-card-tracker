package pl.jkrajniak.cardtracker.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class CardViewModel extends AndroidViewModel{
    private CardRespository cardRespository;
    private LiveData<List<Card>> allCards;

    public CardViewModel(@NonNull Application application) {
        super(application);

        cardRespository = new CardRespository(application);
        allCards = cardRespository.getAllCards();
    }

    public LiveData<List<Card>> getAllCards() {
        return allCards;
    }

    public void insert(Card card) {
        cardRespository.insert(card);
    }

    public Card getCard(int position) {
        return allCards.getValue().get(position);
    }

    public void addTransaction(Card card) {
        int currentNumTransactions = card.getCurrentNumTransactions();
        int expectedNumTransactions = card.getRequiredNumTransactions();
        int newNumTransactions = currentNumTransactions + 1;
//        if (newNumTransactions <= expectedNumTransactions) {
            card.setCurrentNumTransactions(newNumTransactions);
            cardRespository.update(card);
//        }
    }

    public void updateCards() {
        cardRespository.updateCardsCounter();
    }
}
