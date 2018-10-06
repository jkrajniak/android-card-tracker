package pl.jkrajniak.cardtracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public CardsAdapter(OnItemClickListener listener, Context context) {
        this.data = new ArrayList<>();
        this.listener = listener;
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
        holder.cardName.setOnClickListener(v -> listener.onItemClick(v, position));
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
        notifyDataSetChanged();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        private Button cardName;
        private TextView tvNumTransaction;
        private TextView tvBaseLine;

        public CardViewHolder(View itemView) {
            super(itemView);

            cardName = itemView.findViewById(R.id.cardNameBtn);
            tvNumTransaction = itemView.findViewById(R.id.numTransactions);
            tvBaseLine = itemView.findViewById(R.id.baseLine);
        }

        void bind(final Card card) {
            if (card != null) {
                String extraText = "";
                cardName.setText(card.getName());
                if (card.getCurrentNumTransactions() >= card.getRequiredNumTransactions()) {
                    extraText = " ✔";
                }
                tvNumTransaction.setText(Integer.toString(card.getCurrentNumTransactions()));
                tvBaseLine.setText(
                        "required: " + card.getRequiredNumTransactions() +
                                " (" + card.getDaysLeft() + " days left)" +
                                extraText);
            }
        }
    }
}
