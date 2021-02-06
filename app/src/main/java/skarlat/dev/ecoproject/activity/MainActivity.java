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

import skarlat.dev.ecoproject.EcoTipsApp;
import skarlat.dev.ecoproject.Const;
import skarlat.dev.ecoproject.User;
import skarlat.dev.ecoproject.core.SettingsManager;
import skarlat.dev.ecoproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private TextView count;
    static final private int PROGRESS_BAR = 0;
    private final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SettingsManager settingsManager = new SettingsManager(getSharedPreferences(Const.ECO_TIPS_PREFERENCES, MODE_PRIVATE));
        new AsyncTask<Void, Void, User>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                binding.progress.setVisibility(View.VISIBLE);
            }

            @Override
            protected User doInBackground(Void... voids) {
                return EcoTipsApp.auth.getCurrentUser();
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(User user) {
                final Intent intent = new Intent();
                if (user == null) {
                    intent.setClass(getBaseContext(), SignInActivity.class);
                    Log.d(TAG, "User is null");
                } else {
                    intent.setClass(getBaseContext(), HomeActivity.class);
                    User.currentUser = user;
                    assert user.name != null;
                    settingsManager.updateUserName(user.name);
                    Log.d(TAG, "User not null: " + user.name);
                }
                binding.progress.setVisibility(View.INVISIBLE);
                startActivity(intent);
                finish();
                super.onPostExecute(user);
            }
        }.execute();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PROGRESS_BAR) {
            if (resultCode == RESULT_OK) {

            }
        }
    }
}
