package skarlat.dev.ecoproject.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import skarlat.dev.ecoproject.EcoCard;
import skarlat.dev.ecoproject.R;

public class CardsViewAdapter extends RecyclerView.Adapter<CardsViewAdapter.ViewHolder>  {
    private LayoutInflater inflater;
    private List<Object> card;

    public CardsViewAdapter(Context context, List<Object> card){
        this.card = card;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_courses_cards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EcoCard ecoCard = (EcoCard) card.get(position);
        holder.header.setText(ecoCard.getTitle());
        holder.description.setText(ecoCard.getDescription());

    }

    @Override
    public int getItemCount() {
        return card.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView header, description;
        ViewHolder(View view){
            super(view);
            header = (TextView) view.findViewById(R.id.header_card);
            description = (TextView) view.findViewById(R.id.descr_card);
        }
    }
}

