package skarlat.dev.ecoproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
	private LayoutInflater inflater;
	private List<Course> courses;

	CourseAdapter(Context context, List<Course> courses) {
		this.courses = courses;
		this.inflater = LayoutInflater.from(context);
	}
	CourseAdapter(Context context, Education education){}
	CourseAdapter(Context context, Map<Course.Status, Course> map){}
	
	@Override
	public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		
		View view = inflater.inflate(R.layout.card_course, parent, false);
		return new CourseAdapter.ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(CourseAdapter.ViewHolder holder, int position) {
		Course course = courses.get(position);
		holder.header.setText(course.getTitle());
		holder.lvl.setText(course.getLvl());
		holder.tag.setTag(course); // присваиваем вьюхе "карточка" объект карточки(Course);

	}
	
	@Override
	public int getItemCount() {
		return courses.size();
	}
	
	public class ViewHolder extends RecyclerView.ViewHolder {
		final TextView header, lvl;
		final CardView tag;
		ViewHolder(View view){
			super(view);
			header = (TextView) view.findViewById(R.id.current_title);
			lvl = (TextView) view.findViewById(R.id.current_small_description);
			tag = view.findViewById(R.id.current_course);
		}
	}
}
