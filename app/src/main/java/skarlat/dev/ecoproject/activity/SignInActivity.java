package skarlat.dev.ecoproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import skarlat.dev.ecoproject.Const;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.User;
import skarlat.dev.ecoproject.core.SettingsManager;
import skarlat.dev.ecoproject.databinding.ActivitySignInBinding;
import timber.log.Timber;

public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {
    private final String TAG = "SignInActivity";
    private final int RC_SIGN_IN = 10;
    private final String KEY_USENAME = "USERNAME";
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient signInClient;
    private FirebaseAuth mFirebaseAuth;
    private TextInputEditText emailEditText;
    private TextInputEditText passwdEditText;
    ActivitySignInBinding binding;
    private SettingsManager settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        settingsManager = new SettingsManager(getSharedPreferences(Const.ECO_TIPS_PREFERENCES, MODE_PRIVATE));

        mFirebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        signInClient = GoogleSignIn.getClient(this, gso);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        binding.signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });
        binding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithEmailAndPassword();
            }
        });
        binding.signIn.setEnabled(false);
        binding.userEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableOrDisableButton(binding.signIn);
            }
        });
        binding.userPasswd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableOrDisableButton(binding.signIn);
            }
        });
    }

    private void enableOrDisableButton(View view) {
        String userEmail = binding.userEmail.getText().toString();
        String userPassword = binding.userPasswd.getText().toString();

        if (!userEmail.equals("") && !userPassword.equals("")) {
            view.setEnabled(true);
        } else if (binding.signIn.isEnabled()) {
            view.setEnabled(false);
        }
    }

    public void onClick(View v) {
        int pressed = v.getId();
        Intent intent = new Intent();

        switch (pressed) {
            case R.id.remind_passwd:
                intent.setClass(this, RemindPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.register:
                intent.setClass(this, RegistrationActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void signInGoogle() {
        // TODO (Method not working)
        Timber.d("signInGoogle");

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, Const.RC_SIGN_IN_GOOGLE);
    }

    private void signInWithEmailAndPassword() {
        String pass = Objects.requireNonNull(binding.userPasswd.getText()).toString();
        String eMail = Objects.requireNonNull(binding.userEmail.getText()).toString();
        mFirebaseAuth.signInWithEmailAndPassword(eMail, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent();
                            intent.setClass(getBaseContext(), HomeActivity.class);
                            intent.putExtra(KEY_USENAME, task.getResult().getUser().getDisplayName());
                            User.currentUser = new User(task.getResult().getUser().getDisplayName());
                            User.currentUser.setEmail(task.getResult().getUser().getEmail());
                            assert User.currentUser.name != null;
                            settingsManager.updateUserName(User.currentUser.name);
                            Timber.d("Task " + " is successful");
                            startActivity(intent);
                            finish();
                        } else {
                            Timber.d("Task " + " is failure");
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.d("onActivityResult");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Const.RC_SIGN_IN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            assert result != null;
            if (result.isSuccess()) {
                Timber.e("Result is Success");
                // Google Sign-In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                // Google Sign-In failed
                Toast.makeText(this, "Авторизация не удалась!", Toast.LENGTH_SHORT).show();
                Timber.e("Google Sign-In failed." + result.getStatus().getStatusMessage());

            }
        }
    }

    private final int RC_SIGN_IN_GOOGLE = 1;

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        // TODO Re-implement it
        startActivityForResult(signInClient.getSignInIntent(), RC_SIGN_IN_GOOGLE);

        /*AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d(TAG, "name: " + acct.getDisplayName() + "\n".concat(acct.getEmail()));
                            startActivity(new Intent(SignInActivity.this, AuthActivity.class));
                            finish();
                        }
                    }
                });*/
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();

    }
}
