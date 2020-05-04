package skarlat.dev.ecoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = new Intent(this, ProjectActivity.class);
		//public Intent putExtra (String name,
		//                Bundle value) если тебе нужно передать инфу в другую активити
		startActivity(intent, savedInstanceState); // или так можно
	}
}
