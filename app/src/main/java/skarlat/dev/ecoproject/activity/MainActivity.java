package skarlat.dev.ecoproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import skarlat.dev.ecoproject.App;
import skarlat.dev.ecoproject.User;
import skarlat.dev.ecoproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{

	private TextView count;
	static final private int PROGRESS_BAR = 0;
	private final String TAG = "MainActivity";
	private ActivityMainBinding binding;
	
	@SuppressLint("StaticFieldLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());

		// new feature WhyActivity
		Intent intent = new Intent(); // удалить
		intent.setClass(this, WhyActivity.class);
		startActivity(intent);
		finish();

		// Код временно закоментирован для дебагинга
	/*	new AsyncTask<Void, Void, User>() {
			@Override
			protected User doInBackground(Void... voids) {
				return App.auth.getCurrentUser();
			}

			@SuppressLint("StaticFieldLeak")
			@Override
			protected void onPostExecute(User user) {
				if (user == null)
					{intent.setClass(getBaseContext(), SignInActivity.class);
						Log.d(TAG, "User is null");
					}
				else{
					intent.setClass(getBaseContext(), HomeActivity.class);
					Log.d(TAG, "User not null");
				}
				binding.progress.setVisibility(View.INVISIBLE);
				startActivity(intent);
				super.onPostExecute(user);
			}
		}.execute();*/
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == PROGRESS_BAR){
			if (resultCode == RESULT_OK){

			}
		}
	}
}
