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
        return new CardsViewAdapter.ViewHolder(view);
    }
   

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course ecoCard = card.get(position);
        Course.Status status = (Course.Status) ecoCard.getStatus();
        switch (status){
            case CLOSED:
                holder.header.setVisibility(View.GONE);
                holder.description.setVisibility(View.GONE);
                holder.countOpenCard.setVisibility(View.GONE);
                holder.cardClose.setVisibility(View.VISIBLE);
                holder.cardClose.setText(String.valueOf(position));
                break;
            case CURRENT:
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
            case FINISHED:
                holder.header.setVisibility(View.VISIBLE);
                holder.header.setText( ecoCard.getTitle() );
                holder.description.setVisibility(View.VISIBLE);
                holder.description.setText(ecoCard.getDescription());
                GradientDrawable gradientDrawable = (GradientDrawable) holder.backgroundCard.getBackground();
                gradientDrawable.setColor(Color.WHITE);
                holder.countOpenCard.setVisibility(View.VISIBLE);
                holder.countOpenCard.setText(String.valueOf(position));
                holder.cardClose.setVisibility(View.GONE);
        }
        
        /**
         *    Можно делать также через реализацию с if
         *    В след коммите я это удалю
         */
//        if (ecoCard.getStatus() == Course.Status.CLOSED) {
//            holder.header.setVisibility(View.GONE);
//            holder.description.setVisibility(View.GONE);
//            holder.countOpenCard.setVisibility(View.GONE);
//            holder.cardClose.setVisibility(View.VISIBLE);
//            holder.cardClose.setText(String.valueOf(position));
//        }
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
            backgroundCard = (RelativeLayout)view.findViewById(R.id.background_card);
            header = (TextView) view.findViewById(R.id.title_card);
            description = (TextView) view.findViewById(R.id.descr_card);
            countOpenCard = (TextView) view.findViewById(R.id.count_open_card);
            cardClose = (TextView) view.findViewById(R.id.card_close);
            nextCard = (View) view.findViewById(R.id.nextCard);
        }
    }
}

