package pl.jkrajniak.cardtracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.jkrajniak.cardtracker.model.Card;

public class SettingsCardsAdapter extends RecyclerView.Adapter<SettingsCardsAdapter.CardViewHolder> {

    private List<Card> data;
    private final LayoutInflater layoutInflater;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public SettingsCardsAdapter(OnItemClickListener listener, Context context) {
        this.data = new ArrayList<>();
        this.listener = listener;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public SettingsCardsAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.fragment_settings_carditem, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsCardsAdapter.CardViewHolder holder, int position) {
        holder.bind(data.get(position));
        holder.layoutItem.setOnClickListener(v -> listener.onItemClick(v, position));
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
        private TextView cardName;
        private LinearLayout layoutItem;

        public CardViewHolder(View itemView) {
            super(itemView);

            cardName = itemView.findViewById(R.id.settings_cardName);
            layoutItem = itemView.findViewById(R.id.settings_cardNameItem);
        }

        void bind(final Card card) {
            if (card != null) {
                cardName.setText(card.getName());
            }
        }
    }
}
