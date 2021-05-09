package skarlat.dev.ecoproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import skarlat.dev.ecoproject.Const;
import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.User;
import skarlat.dev.ecoproject.activity.SignInActivity;
import skarlat.dev.ecoproject.core.SettingsManager;
import skarlat.dev.ecoproject.databinding.FragmentSettingsBinding;

public class ProfileSettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    public static Fragment newInstance(){
        return new ProfileSettingsFragment();
    }

    private SettingsManager settingsManager;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settingsManager = new SettingsManager(getActivity().getSharedPreferences(Const.ECO_TIPS_PREFERENCES, Context.MODE_PRIVATE));

        View.OnClickListener editClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToEditSettings();
            }
        };
        binding.buttonEdit.setOnClickListener(editClick);
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
        binding.buttonEditMaterial.setOnClickListener(editClick);
        binding.buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        initUser();
    }

    private void initUser() {
        binding.valueName.setText(settingsManager.getUserName());
        binding.valueEmail.setText(User.currentUser.getEmail());
        binding.valuePassword.setText(Objects.requireNonNull(getContext()).getString(R.string.mask_user_password));
    }

    private void logout() {
        settingsManager.clearSettings();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), SignInActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void navigateToEditSettings(){
        getFragmentManager().beginTransaction().add(R.id.home_layout, EditProfileFragment.newInstance()).commit();
    }

    private void closeFragment(){
        getFragmentManager().beginTransaction().remove(this).detach(this).commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater);
        return binding.getRoot();
    }
}
