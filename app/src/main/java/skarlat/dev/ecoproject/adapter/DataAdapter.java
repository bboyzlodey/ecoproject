package skarlat.dev.ecoproject.adapter;

import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import skarlat.dev.ecoproject.includes.dataclass.EcoSoviet;
import skarlat.dev.ecoproject.R;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<EcoSoviet> soviets;

    public DataAdapter(Context context, List<EcoSoviet> soviets) {
        this.soviets = soviets;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.card_soviet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        EcoSoviet ecoSoviet = soviets.get(position);
        holder.header.setText(ecoSoviet.title);
        holder.description.setText(ecoSoviet.description);
    }

    @Override
    public int getItemCount() {
        return soviets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView header, description;

        ViewHolder(View view) {
            super(view);
            header = view.findViewById(R.id.header_card);
            description = view.findViewById(R.id.descr_card);
        }
    }
}