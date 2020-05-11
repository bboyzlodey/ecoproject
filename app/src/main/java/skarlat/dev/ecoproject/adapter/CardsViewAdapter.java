package skarlat.dev.ecoproject.adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import skarlat.dev.ecoproject.Course;
import skarlat.dev.ecoproject.EcoCard;
import skarlat.dev.ecoproject.R;

public class CardsViewAdapter extends RecyclerView.Adapter<CardsViewAdapter.ViewHolder>  {
    private LayoutInflater inflater;
    private List<Course> card;

    public CardsViewAdapter(Context context, List<Course> card){
        this.card = card;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CardsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_courses_cards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course ecoCard = card.get(position);
        int status = ecoCard.getStatus();
        if (status == 0) {
            holder.header.setVisibility(View.GONE);
            holder.description.setVisibility(View.GONE);
            holder.countOpenCard.setVisibility(View.GONE);
            holder.cardClose.setVisibility(View.VISIBLE);
            holder.cardClose.setText(String.valueOf(position));
        } else if( status == 1 ){
            holder.header.setVisibility(View.GONE);
            holder.description.setVisibility(View.GONE);
            holder.countOpenCard.setVisibility(View.GONE);
            holder.cardClose.setVisibility(View.VISIBLE);
            holder.cardClose.setText("");
            Drawable drawable = holder.backgroundCard.getBackground();
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
//            holder.backgroundCard.setTranslationZ(24);
//            holder.backgroundCard.setElevation(0);
            holder.cardClose.setBackgroundResource(R.drawable.ic_play);
        }
        else if (status == 2 ){
            holder.header.setVisibility(View.VISIBLE);
            holder.header.setText( ecoCard.getTitle() );
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(ecoCard.getDescription());
            GradientDrawable drawable = (GradientDrawable) holder.backgroundCard.getBackground();
            drawable.setColor(Color.WHITE);
            holder.countOpenCard.setVisibility(View.VISIBLE);
            holder.countOpenCard.setText(String.valueOf(position));
            holder.cardClose.setVisibility(View.GONE);
        }

        if ( getItemCount() - 1 == position ){
            holder.nextCard.setVisibility(View.GONE);
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
            header = (TextView) view.findViewById(R.id.title_card);
            description = (TextView) view.findViewById(R.id.descr_card);
            countOpenCard = (TextView) view.findViewById(R.id.count_open_card);
            cardClose = (TextView) view.findViewById(R.id.card_close);
            nextCard = (View) view.findViewById(R.id.nextCard);
        }
    }
}

