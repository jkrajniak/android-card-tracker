package pl.jkrajniak.cardtracker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import pl.jkrajniak.cardtracker.model.Card;
import pl.jkrajniak.cardtracker.model.CardViewModel;

public class SettingsActivity extends AppCompatActivity implements SettingsCardsAdapter.OnItemClickListener {
    private SettingsCardsAdapter cardsAdapter;
    private CardViewModel cardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);

        cardsAdapter = new SettingsCardsAdapter(this, this);
        cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        cardViewModel.getAllCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                cardsAdapter.setData(cards);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.settings_cardList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardsAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent startEditIntent = new Intent(view.getContext(), AddCardActivity.class);
        Card card = cardViewModel.getCard(position);
        startEditIntent.putExtra("card", card);
        startActivity(startEditIntent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
