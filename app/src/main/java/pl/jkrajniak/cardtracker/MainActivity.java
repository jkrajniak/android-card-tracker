package pl.jkrajniak.cardtracker;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import pl.jkrajniak.cardtracker.model.AppDatabase;
import pl.jkrajniak.cardtracker.model.Card;
import pl.jkrajniak.cardtracker.model.CardViewModel;

public class MainActivity extends AppCompatActivity implements CardsAdapter.OnItemClickListener {
    private CardsAdapter cardsAdapter;
    private CardViewModel cardViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent addCardIntent = new Intent(MainActivity.this, AddCardActivity.class);

        cardsAdapter = new CardsAdapter(this, this);
        cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        cardViewModel.getAllCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                cardsAdapter.setData(cards);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.cardItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardsAdapter);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);

        setSupportActionBar(findViewById(R.id.my_toolbar));
        cardViewModel.updateCards();
    }

    private void resetCards() {
        List<Card> cards = cardViewModel.getAllCards().getValue();
        for (Card card: cards) {
            Log.i("Cards", card.toString());
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Card card = cardViewModel.getCard(position);
        cardViewModel.addTransaction(card);
        Toast.makeText(view.getContext(), "Transaction added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void showSettings() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_add_card:
                startActivity(new Intent(this, AddCardActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
