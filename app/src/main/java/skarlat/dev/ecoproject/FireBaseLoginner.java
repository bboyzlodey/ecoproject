package skarlat.dev.ecoproject;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FireBaseLoginner extends Loginner implements OnCompleteListener<AuthResult> {
	private final String TAG = "FireBaseLoginner";
	private String operation;
	private String debugLogInGoogle = "signInWithGoogle";
	
	public FireBaseLoginner(String passwd, String eMail) {
		super(passwd, eMail);
	}
	
	@Override
	public User logIn(String passwd, String eMail) {
		operation = "signInWithEmail";
		FirebaseAuth mAuth = (FirebaseAuth) App.auth.getInstance();
		/*FirebaseUser user* =*/ mAuth
				                    .signInWithEmailAndPassword(eMail, passwd)
				                    .addOnCompleteListener(this);
//				                    .getResult()
//				                    .getUser();
		FirebaseUser user = mAuth.getCurrentUser();
		if (user == null)
			return null;
		return new User(user.getDisplayName());
	}
	
//	public
	
	@Override
	public User logIn() {
		return logIn(this.passwd, this.eMail);
	}
	
	
	
	@Override
	public void onComplete(@NonNull Task<AuthResult> task) {
		if (task.isSuccessful()){
			Log.d(TAG, "Task " + operation +" is successful");
		}
		else{
			Log.d(TAG, "Task " + operation +" is failure");
		}
	}
}