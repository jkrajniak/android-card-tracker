package pl.jkrajniak.cardtracker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import pl.jkrajniak.cardtracker.model.Card;
import pl.jkrajniak.cardtracker.model.CardRespository;
import pl.jkrajniak.cardtracker.model.CardViewModel;

public class MainActivity extends AppCompatActivity implements CardsAdapter.OnItemClickListener {
    private CardsAdapter cardsAdapter;
    private CardViewModel cardViewModel;

    public static final int NEW_CARD_ACTIVITY_REQUEST_CODE = 1;

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
    }

    @Override
    public void onItemClick(View view, int position) {
        Card card = cardViewModel.getCard(position);
        cardViewModel.addTransaction(card);
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
