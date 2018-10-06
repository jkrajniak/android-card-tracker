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
import pl.jkrajniak.cardtracker.model.CardViewModel;

public class AddCardActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText cardNameText;
    private EditText numTransactionText;
    private EditText firstDayOfCycle;
    private EditText currentTransactions;
    private boolean isValid = false;
    private boolean isChanged = false;

    private boolean isEdit = false;
    private int cardId = 0;
    private Card card;
    CardRespository cardRespository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cardRespository = new CardRespository(getApplication());
        CardViewModel cardViewModel = new CardViewModel(getApplication());

        Bundle b = getIntent().getExtras();
        if (b != null) {
            card = (Card) b.get("card");
            if (card != null)
                isEdit = true;
        }

        Button saveBtn = findViewById(R.id.saveCardBtn);
        Button deleteBtn = findViewById(R.id.deleteCardBtn);

        cardNameText = findViewById(R.id.cardName);
        numTransactionText = findViewById(R.id.numTransaction);
        firstDayOfCycle = findViewById(R.id.cycleStartsOnDay);
        currentTransactions = findViewById(R.id.currentTransactions);

        if (isEdit) {
            saveBtn.setText("Update");
            deleteBtn.setVisibility(View.VISIBLE);
            cardNameText.setText(card.getName());
            numTransactionText.setText(Integer.toString(card.getRequiredNumTransactions()));
            firstDayOfCycle.setText(Integer.toString(card.getCycleStartsOnDay()));
            currentTransactions.setText(Integer.toString(card.getCurrentNumTransactions()));
        }

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
                    numTransactionText.setError("field required");
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

        currentTransactions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = currentTransactions.getText().toString().trim();
                if (text.isEmpty()) {
                    currentTransactions.setError("field required");
                    isValid = false;
                }
                try {
                    int numTransaction = Integer.parseInt(currentTransactions.getText().toString());
                    if (numTransaction < 0) {
                        isValid = false;
                        currentTransactions.setError("cannot be less than 0");
                    }
                } catch (NumberFormatException e) {
                    currentTransactions.setError("wrong number");
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
                    firstDayOfCycle.setError("field required");
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
        deleteBtn.setOnClickListener(this);
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
            int numTransaction = Integer.parseInt(currentTransactions.getText().toString());
            if (numTransaction < 1) {
                isValid = false;
                currentTransactions.setError("cannot be less than 1");
            }
            isValid = true;
        } catch (NumberFormatException e) {
            currentTransactions.setError("wrong number");
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

        String cardName = cardNameText.getText().toString();
        int numTransactions = Integer.parseInt(numTransactionText.getText().toString());
        int dayOfCycle = Integer.parseInt(firstDayOfCycle.getText().toString());
        int numCurrentTransactions = Integer.parseInt(currentTransactions.getText().toString());

        if (isEdit) {
            card.setName(cardName);
            card.setCycleStartsOnDay(dayOfCycle);
            card.setRequiredNumTransactions(numTransactions);
            card.setCurrentNumTransactions(numCurrentTransactions);
            cardRespository.update(card);
        } else {
            card = new Card(cardName, numTransactions, dayOfCycle);
            card.setCurrentNumTransactions(numCurrentTransactions);
            cardRespository.insert(card);
        }
        Snackbar.make(findViewById(R.id.addCardLayout), "Card saved!", Snackbar.LENGTH_LONG).show();
        finish();
    };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveCardBtn: {
                saveCard();
                break;
            }
            case R.id.deleteCardBtn: {
                deleteCard();
                break;
            }
        }
    }

    private void deleteCard() {
        new AlertDialog.Builder(this).setTitle("Do you want to remove the card?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    cardRespository.delete(card);
                    finish();
                }).setNegativeButton("No", (dialog, which) -> {
                    finish();
        }).show();
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
