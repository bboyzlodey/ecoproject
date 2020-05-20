package skarlat.dev.ecoproject.adapter;

import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import skarlat.dev.ecoproject.EcoSoviet;
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
		holder.header.setText(ecoSoviet.getTitle());
		holder.description.setText(ecoSoviet.getDescription());
		holder.toggle.setTag(ecoSoviet);
		if (ecoSoviet.getStatus() == EcoSoviet.Status.LIKED)
			holder.toggle.setChecked(true);
		else
			holder.toggle.setChecked(false);
	}
	
	@Override
	public int getItemCount() {
		return soviets.size();
	}
	
	public class ViewHolder extends RecyclerView.ViewHolder {
		final TextView header, description;
		final ToggleButton toggle;
		ViewHolder(View view){
			super(view);
			header = (TextView) view.findViewById(R.id.header_card);
			description = (TextView) view.findViewById(R.id.descr_card);
			toggle = view.findViewById(R.id.toggle);
		}
	}
}