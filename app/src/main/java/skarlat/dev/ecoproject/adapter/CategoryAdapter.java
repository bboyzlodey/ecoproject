package skarlat.dev.ecoproject.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import skarlat.dev.ecoproject.EcoCard;
import skarlat.dev.ecoproject.EcoSoviet;
import skarlat.dev.ecoproject.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
	private LayoutInflater inflater;
	private List<EcoCard> ecoCards;
	
	public CategoryAdapter(Context context, List<EcoCard> ecoCards) {
		this.ecoCards = ecoCards;
		this.inflater = LayoutInflater.from(context);
	}
	@Override
	public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		
		View view = inflater.inflate(R.layout.card_by_category, parent, false);
		return new CategoryAdapter.ViewHolder(view);
	}
	
	@RequiresApi(api = Build.VERSION_CODES.N)
	@Override
	public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
		if (position == 0){
			forAll(holder);
		}
		EcoCard ecoCard = ecoCards.get(position);
//		holder.header.setText(ecoSoviet.getTitle());
//		holder.description.setText(ecoSoviet.getDescription());
	}
	
	@Override
	public int getItemCount() {
		return ecoCards.size();
	}
	
	public class ViewHolder extends RecyclerView.ViewHolder {
		final TextView totalProgress, category;
		final View view;
		ViewHolder(View view){
			super(view);
			this.view = view;
			totalProgress = (TextView) view.findViewById(R.id.progress_category);
			category = (TextView) view.findViewById(R.id.title_category);
		}
	}
	
	/**
	 *  Метод, который посчитает все открытые карточки в формате N/list.size()
	 *  Лучше бы от него уйти, потому что не работает на моем телефоне (API)
	 * @param holder
	 */
	@RequiresApi(api = Build.VERSION_CODES.N)
	protected void forAll(CategoryAdapter.ViewHolder holder){
//		String all = "Все";
//		Collection<EcoCard> collection = (Collection<EcoCard>) ecoCards;
//		Stream<EcoCard> ecoCardStream = collection.stream();
//		long watched = ecoCardStream.filter(new Predicate<EcoCard>() {
//			@Override
//			public boolean test(EcoCard ecoCard) {
//				return ecoCard.status == EcoCard.Status.WATCHED;
//			}
//		}).count();
//		holder.totalProgress.setText(watched + "/" + ecoCards.size());
//		holder.category.setText(all);
	}
}