package skarlat.dev.ecoproject.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import skarlat.dev.ecoproject.EcoCard;
import skarlat.dev.ecoproject.R;

/**
 * TODO ("Implement adapter for different viewTypes")
 */
public class CardsViewAdapter extends RecyclerView.Adapter<CardsViewAdapter.ViewHolder>  {
    private LayoutInflater inflater;
    private List<EcoCard> card;

    public CardsViewAdapter(Context context, List<EcoCard> card){
        this.card = card;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CardsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_courses_cards, parent, false);
        return new CardsViewAdapter.ViewHolder(view);
    }
   

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EcoCard ecoCard = card.get(position);
        EcoCard.Status status = (EcoCard.Status) ecoCard.getStatus();
        Drawable drawable;
        switch (status){
            case CLOSED:
                holder.header.setVisibility(View.GONE);
                holder.description.setVisibility(View.GONE);
                holder.countOpenCard.setVisibility(View.GONE);
                holder.cardClose.setVisibility(View.VISIBLE);
                drawable = holder.backgroundCard.getBackground();
                drawable.setColorFilter(Color.rgb(213,213,213), PorterDuff.Mode.SRC_OVER);
                holder.cardClose.setText(String.valueOf(position));
                break;
            case OPENED:
                holder.header.setVisibility(View.GONE);
                holder.description.setVisibility(View.GONE);
                holder.countOpenCard.setVisibility(View.GONE);
                holder.cardClose.setVisibility(View.VISIBLE);
                holder.cardClose.setText("");
                drawable = holder.backgroundCard.getBackground();
                drawable.setColorFilter(Color.rgb(248, 242, 232), PorterDuff.Mode.SRC_OVER);
                holder.cardClose.setBackgroundResource(R.drawable.ic_play);
                break;
            case WATCHED:
                holder.header.setVisibility(View.VISIBLE);
                holder.header.setText( ecoCard.getTitle() );
                holder.description.setVisibility(View.VISIBLE);
                holder.description.setText(ecoCard.getDescription());
                drawable = holder.backgroundCard.getBackground();
                drawable.setColorFilter(Color.rgb(248, 242, 232), PorterDuff.Mode.SRC_OVER);
                holder.countOpenCard.setVisibility(View.VISIBLE);
                holder.countOpenCard.setText(String.valueOf(position + 1));
                holder.cardClose.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        holder.backgroundCard.setTag(ecoCard);

        if ( getItemCount() - 1 == position ){ // Latest item
            holder.nextCard.setVisibility(View.GONE);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, 240));
            linearParams.setMargins(0,0,0,16);
            holder.backgroundCard.setLayoutParams(linearParams);
            holder.backgroundCard.requestLayout();

        }
    }

    @Override
    public int getItemCount() {
        return card.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView header, description, countOpenCard, cardClose;
        final View nextCard;
        final RelativeLayout backgroundCard;
        ViewHolder(View view){
            super(view);
            backgroundCard = view.findViewById(R.id.background_card);
            header = view.findViewById(R.id.title_card);
            description = view.findViewById(R.id.descr_card);
            countOpenCard = view.findViewById(R.id.count_open_card);
            cardClose = view.findViewById(R.id.card_close);
            nextCard = view.findViewById(R.id.nextCard);
        }
    }
}

