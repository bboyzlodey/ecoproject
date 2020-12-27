package skarlat.dev.ecoproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import durdinapps.rxfirebase2.RxFirebaseUser;
import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.User;
import skarlat.dev.ecoproject.databinding.FragmentEditProfileBinding;

public class EditProfileSettingsFragment extends Fragment {

    private FragmentEditProfileBinding binding;

    public static Fragment newInstance() {
        return new EditProfileSettingsFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.userName.setText(User.currentUser.name);
        binding.userEmail.setText(User.currentUser.geteMail());
        binding.userPassword.setText(Objects.requireNonNull(getContext()).getString(R.string.mask_user_password));
        binding.buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
        binding.buttonEditMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = new User();
                newUser.seteMail(binding.userEmail.getText().toString());
                newUser.name = binding.userName.getText().toString();
                updateUser(newUser);
            }
        });
    }

    private volatile HashMap<String, Task<Void>> tasks = new HashMap<>(3);

    private Queue<Completable> firebaseRequestQueue = new ArrayDeque<>(3);

    private void updateUser(User newUser) {
        User currentUser = User.currentUser;
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (!currentUser.geteMail().equals(newUser.geteMail())) {
            firebaseRequestQueue.add(RxFirebaseUser
                    .updateEmail(firebaseUser, newUser.geteMail())
                    .doOnError(throwable -> showSuccessfulMessageInToast("Error!"))
                    .doOnComplete(() -> showSuccessfulMessageInToast(getString(R.string.mail_changed_success))));
        }
        if (!currentUser.name.equals(newUser.name)) {
            firebaseRequestQueue.add(
                    RxFirebaseUser
                            .updateProfile(firebaseUser, new UserProfileChangeRequest.Builder().setDisplayName(newUser.name).build())
                            .doOnError(throwable -> showSuccessfulMessageInToast("Error! Change password!"))
                            .doOnComplete(() -> showSuccessfulMessageInToast(getString(R.string.name_changed_success)))
            );
        }
        if (!binding.userPassword.getText().toString().equals(getContext().getString(R.string.mask_user_password))) {
            firebaseRequestQueue.add(
                    RxFirebaseUser.updatePassword(firebaseUser, binding.userPassword.getText().toString())
                            .doOnError(error -> showSuccessfulMessageInToast("Error! Change password!"))
                            .doOnComplete(() -> showSuccessfulMessageInToast(getString(R.string.password_changed_success)))
            );
        }
        processingRequests();
    }

    private void processingRequests() {
        Completable completable = firebaseRequestQueue.poll();
        if (completable == null) {
            return;
        }
        while (firebaseRequestQueue.size() > 0) {
            completable = completable.andThen(Objects.requireNonNull(Objects.requireNonNull(firebaseRequestQueue.poll()).delay(10, TimeUnit.SECONDS)));
        }
        completable.subscribe(new Action() {
            @Override
            public void run() throws Exception {
                showSuccessfulMessageInToast("Жизнь прекрасна!");
                closeFragment();
            }
        }, new Consumer<Throwable>() {
            // TODO Handling firebase exceptions
            @Override
            public void accept(Throwable throwable) throws Exception {
                showSuccessfulMessageInToast("Возникла ошибка");

                showSuccessfulMessageInToast("Перезапустите приложение");
                closeFragment();
                throwable.printStackTrace();
            }
        });
    }

    private void changePassword(FirebaseUser firebaseUser, String newPassword) {

    }

    private void showSuccessfulMessageInToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void closeFragment() {
        getFragmentManager().beginTransaction().remove(this).detach(this).commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater);
        return binding.getRoot();
    }
}
