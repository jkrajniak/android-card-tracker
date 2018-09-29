package pl.jkrajniak.cardtracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pl.jkrajniak.cardtracker.model.Card;
import pl.jkrajniak.cardtracker.model.CardRespository;

public class AddCardActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText cardNameText;
    private EditText numTransactionText;
    private EditText firstDayOfCycle;
    private boolean isValid = false;
    private boolean isChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button saveBtn = findViewById(R.id.saveCardBtn);

        cardNameText = findViewById(R.id.cardName);
        numTransactionText = findViewById(R.id.numTransaction);
        firstDayOfCycle = findViewById(R.id.cycleStartsOnDay);

        cardNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String text = cardNameText.getText().toString().trim();
                if (text.isEmpty()) {
                    cardNameText.setError("name required");
                    isValid = false;
                }
                isValid = isValid && true;
                isChanged = true;
            }
        });

        numTransactionText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = numTransactionText.getText().toString().trim();
                if (text.isEmpty()) {
                    cardNameText.setError("field required");
                    isValid = false;
                }
                try {
                    int numTransaction = Integer.parseInt(numTransactionText.getText().toString());
                    if (numTransaction < 1) {
                        isValid = false;
                        numTransactionText.setError("cannot be less than 1");
                    }
                } catch (NumberFormatException e) {
                    numTransactionText.setError("wrong number");
                    isValid = false;
                }
                isValid = isValid && true;
                isChanged = true;
            }
        });

        firstDayOfCycle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = firstDayOfCycle.getText().toString().trim();
                if (text.isEmpty()) {
                    cardNameText.setError("field required");
                    isValid = false;
                }
                try {
                    int firstDay = Integer.parseInt(firstDayOfCycle.getText().toString());
                    if (firstDay < 1 || firstDay > 31) {
                        isValid = false;
                        firstDayOfCycle.setError("wrong day (1-31)");
                    }
                } catch (NumberFormatException e) {
                    firstDayOfCycle.setError("wrong number");
                    isValid = false;
                }
                isValid = isValid && true;
                isChanged = true;
            }
        });
        saveBtn.setOnClickListener(this);
    }

    private void isValid() {
        String cardName = cardNameText.getText().toString().trim();
        if (cardName.isEmpty()) {
            cardNameText.setError("required");
            isValid = false;
        } else {
            isValid = true;
        }
        try {
            int numTransaction = Integer.parseInt(numTransactionText.getText().toString());
            if (numTransaction < 1) {
                isValid = false;
                numTransactionText.setError("cannot be less than 1");
            }
            isValid = true;
        } catch (NumberFormatException e) {
            numTransactionText.setError("wrong number");
            isValid = false;
        }

        try {
            int firstDay = Integer.parseInt(firstDayOfCycle.getText().toString());
            if (firstDay < 1 || firstDay > 31) {
                isValid = false;
                firstDayOfCycle.setError("wrong day (1-31)");
            }
            isValid = true;
        } catch (NumberFormatException e) {
            firstDayOfCycle.setError("wrong number");
            isValid = false;
        }
    }

    private void saveCard() {
        isValid();
        if (!isValid) {
            Snackbar.make(findViewById(R.id.addCardLayout), "Check errors", Snackbar.LENGTH_LONG).show();
            return;
        }
        CardRespository cardRespository = new CardRespository(getApplication());

        Card card = new Card(
                cardNameText.getText().toString(),
                Integer.parseInt(numTransactionText.getText().toString()),
                Integer.parseInt(firstDayOfCycle.getText().toString()));
        cardRespository.insert(card);
        Snackbar.make(findViewById(R.id.addCardLayout), "Card saved!", Snackbar.LENGTH_LONG).show();
        finish();
    };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveCardBtn: {
                saveCard();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isChanged) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Save edited card?").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveCard();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
        } else {
            super.onBackPressed();
        }
    }

}
