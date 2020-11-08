package skarlat.dev.ecoproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import skarlat.dev.ecoproject.Const;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.User;
import skarlat.dev.ecoproject.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {
    private final String TAG = "SignInActivity";
    private final int RC_SIGN_IN = 10;
    private final String KEY_USENAME = "USERNAME";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;
    private TextInputEditText emailEditText;
    private TextInputEditText passwdEditText;
    ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mFirebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
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

    public void remindPassword(View v) {

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
        Log.d(TAG, "signInGoogle");

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, Const.RC_SIGN_IN_GOOGLE);
    }

    private void signInWithEmailAndPassword() {
        String pass = binding.userPasswd.getText().toString();
        String eMail = binding.userEmail.getText().toString();
        mFirebaseAuth.signInWithEmailAndPassword(eMail, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent();
                            intent.setClass(getBaseContext(), HomeActivity.class);
                            intent.putExtra(KEY_USENAME, task.getResult().getUser().getDisplayName());
                            User.currentUser = new User(task.getResult().getUser().getDisplayName());
                            Log.d(TAG, "Task " + " is successful");
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d(TAG, "Task " + " is failure");
                        }
                    }
                });
    }

    @SuppressLint("ShowToast")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Const.RC_SIGN_IN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Log.e(TAG, "Result is Success");
                // Google Sign-In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                // Google Sign-In failed
                Toast.makeText(this, "Авторизация не удалась!", Toast.LENGTH_SHORT);
                Log.e(TAG, "Google Sign-In failed." + result.getStatus().getStatusMessage());

            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

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
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();

    }
}
