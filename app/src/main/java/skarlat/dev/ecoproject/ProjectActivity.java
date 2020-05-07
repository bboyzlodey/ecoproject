package skarlat.dev.ecoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/*
**  Project Activity
*
*
 */
public class ProjectActivity extends AppCompatActivity {
	Bundle savedInstanceState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project);
		this.savedInstanceState = savedInstanceState;
		ImageView imageView = (ImageView) findViewById(R.id.hexagon);
		imageView.setImageDrawable(new HexogonDrawable());
	}
	
	public void openCard(View view){
		Intent intent = new Intent(ProjectActivity.this, EcoCardActivity.class);
		startActivity(intent, savedInstanceState);
	}
}
