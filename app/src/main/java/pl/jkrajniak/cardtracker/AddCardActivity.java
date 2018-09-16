package pl.jkrajniak.cardtracker;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pl.jkrajniak.cardtracker.model.CardRespository;

public class AddCardActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button saveBtn = findViewById(R.id.saveCardBtn);
        saveBtn.setOnClickListener(this);
    }

    private void saveCard() {
        CardRespository cardRespository = new CardRespository(getApplication());
        EditText cardNameText = findViewById(R.id.cardName);
        EditText numTransactionText = findViewById(R.id.numTransaction);
        cardRespository.insertCard(
                cardNameText.getText().toString(),
                Integer.parseInt(numTransactionText.getText().toString()));
        Snackbar.make(findViewById(R.id.linearLayout), "Card saved!", Snackbar.LENGTH_LONG);
    };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveCardBtn: {
                saveCard();
                finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        saveCard();
        super.onBackPressed();
    }

}
