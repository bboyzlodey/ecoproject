package skarlat.dev.ecoproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter<U> extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
	private LayoutInflater inflater;
	private List<Course> courses;
	
	CourseAdapter(Context context, List<Course> courses) {
		this.courses = courses;
		this.inflater = LayoutInflater.from(context);
	}
	@Override
	public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		
		View view = inflater.inflate(R.layout.card_course, parent, false);
		return new CourseAdapter.ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(CourseAdapter.ViewHolder holder, int position) {
		Course course = courses.get(position);
		holder.header.setText(course.getTitle());
		holder.description.setText(course.getDescription());
	}
	
	@Override
	public int getItemCount() {
		return courses.size();
	}
	
	public class ViewHolder extends RecyclerView.ViewHolder {
		final TextView header, description;
		ViewHolder(View view){
			super(view);
			header = (TextView) view.findViewById(R.id.current_title);
			description = (TextView) view.findViewById(R.id.current_small_description);
		}
	}
}
