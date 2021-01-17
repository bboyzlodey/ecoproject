package skarlat.dev.ecoproject.network;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import skarlat.dev.ecoproject.User;

public class FireBaseAuthenticator extends Authenticator<FirebaseAuth> {

    public FireBaseAuthenticator(FirebaseAuth instance) {
        super(instance);
    }

    @Override
    public User getCurrentUser() {
        FirebaseUser firebaseUser = instance.getCurrentUser();
        if (firebaseUser == null) {
            return null;
        }
        User user = new User(firebaseUser.getDisplayName());
        user.setEmail(firebaseUser.getEmail());
        return user;
    }
}
