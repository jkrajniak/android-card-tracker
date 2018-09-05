package pl.jkrajniak.cardtracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.jkrajniak.cardtracker.model.Card;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> {

    private List<Card> data;
    private final LayoutInflater layoutInflater;
    private final Context context;

    public CardsAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CardsAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.fragment_carditem, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsAdapter.CardViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Card> newData) {
        if (data != null) {
            data.clear();
            data.addAll(newData);
        } else {
            data = newData;
        }
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        private Button btnCardName;
        private TextView tvNumTransaction;

        public CardViewHolder(View itemView) {
            super(itemView);

            btnCardName = itemView.findViewById(R.id.cardNameBtn);
            tvNumTransaction = itemView.findViewById(R.id.numTransaction);
        }

        void bind(final Card card) {
            if (card != null) {
                btnCardName.setText(card.getName());
                tvNumTransaction.setText(
                        card.getCurrentNumTransactions() + "/" + card.getRequiredNumTransactions()
                );
            }
        }
    }
}
