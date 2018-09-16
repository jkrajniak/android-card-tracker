package pl.jkrajniak.cardtracker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import pl.jkrajniak.cardtracker.model.Card;
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
    }
}
