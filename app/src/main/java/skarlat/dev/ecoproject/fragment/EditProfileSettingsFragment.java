package skarlat.dev.ecoproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.HashMap;
import java.util.Objects;

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

    private void updateUser(User newUser) {
        User currentUser = User.currentUser;
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (!currentUser.geteMail().equals(newUser.geteMail())) {
            Task<Void> updateEmailTask = firebaseUser.updateEmail(newUser.geteMail());
            tasks.put("UPDATE_EMAIL", updateEmailTask);
            updateEmailTask.addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("ARRR", "EditProfileSettingsFragment.updateUser " + "updateEmailTask sucessful: " + task.isSuccessful());
                    Log.d("ARRR", "EditProfileSettingsFragment.updateUser " + "task toString(): " + task);
                    if (!task.isSuccessful()) {
                        Log.e("ARRR", "EditProfileSettingsFragment.updateUser ", task.getException());
                    } else {
                        showSuccessfulMessageInToast(getString(R.string.mail_changed_success));
                    }
                    if (tasks.containsKey("UPDATE_EMAIL")) {
                        tasks.remove("UPDATE_EMAIL");
                        if (tasks.isEmpty()) {
                            closeFragment();
                        }
                    }
                }
            });
        }
        if (!currentUser.name.equals(newUser.name)) {
            Task<Void> updateProfileName =
                    firebaseUser.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(newUser.name).build());
            tasks.put("UPDATE_USERNAME", updateProfileName);
            updateProfileName.addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("ARRR", "EditProfileSettingsFragment.updateUser " + "updateProfileName sucessful: " + task.isSuccessful());
                    if (!task.isSuccessful()) {
                        Log.e("ARRR", "EditProfileSettingsFragment.updateUser ", task.getException());
                    } else {
                        showSuccessfulMessageInToast(getString(R.string.name_changed_success));
                    }
                    if (tasks.containsKey("UPDATE_USERNAME")) {
                        tasks.remove("UPDATE_USERNAME");
                        if (tasks.isEmpty()) {
                            closeFragment();
                        }
                    }
                }
            });
        }
        if (!binding.userPassword.getText().toString().equals(getContext().getString(R.string.mask_user_password))) {
            Task<Void> updatePasswordTask = firebaseUser.updatePassword(binding.userPassword.getText().toString());
            tasks.put("UPDATE_PASSWORD", updatePasswordTask);
            updatePasswordTask.addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("ARRR", "EditProfileSettingsFragment.updateUser " + "updatePasswordTask sucessful: " + task.isSuccessful());
                    if (!task.isSuccessful()) {
                        Log.e("ARRR", "EditProfileSettingsFragment.updateUser ", task.getException());
                    } else {
                        showSuccessfulMessageInToast(getString(R.string.password_changed_success));
                    }
                    if (tasks.containsKey("UPDATE_PASSWORD")) {
                        tasks.remove("UPDATE_PASSWORD");
                        if (tasks.isEmpty()) {
                            closeFragment();
                        }
                    }
                }
            });
        }
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
