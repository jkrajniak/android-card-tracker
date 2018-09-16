package pl.jkrajniak.cardtracker;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import pl.jkrajniak.cardtracker.model.CardRespository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addCardButton);
        fab.setOnClickListener(view -> startActivity(new Intent(view.getContext(), AddCardActivity.class)));

        CardsAdapter cardsAdapter = new CardsAdapter(this);
        CardRespository cardRespository = ViewModelProviders.of(this).get(CardRespository.class);
        cardRespository.getCards().observe(this, cards -> cardsAdapter.setData(cards));

        RecyclerView recyclerView = findViewById(R.id.cardItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardsAdapter);
    }
}
