package skarlat.dev.ecoproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import skarlat.dev.ecoproject.R;
import skarlat.dev.ecoproject.User;
import skarlat.dev.ecoproject.activity.SignInActivity;
import skarlat.dev.ecoproject.databinding.FragmentSettingsBinding;

public class ProfileSettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    public static Fragment newInstance(){
        return new ProfileSettingsFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.valueName.setText(User.currentUser.name);
        binding.valueEmail.setText(User.currentUser.getEmail());
        binding.valuePassword.setText(getContext().getString(R.string.mask_user_password));

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

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), SignInActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void navigateToEditSettings(){
        getFragmentManager().beginTransaction().add(R.id.home_layout, EditProfileSettingsFragment.newInstance()).commit();
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
