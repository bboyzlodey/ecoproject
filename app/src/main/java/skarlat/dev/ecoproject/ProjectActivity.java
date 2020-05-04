package skarlat.dev.ecoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
	}
	
	protected void openCard(View view){
		Intent intent = new Intent(ProjectActivity.this, EcoCardActivity.class);
		startActivity(intent, savedInstanceState);
	}
}
