package skarlat.dev.ecoproject.fragment;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import skarlat.dev.ecoproject.App;
import skarlat.dev.ecoproject.User;
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
        binding.valueName.setText(User.currentUser.geteMail());

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
//        binding.buttonEditMaterial.setOnClickListener(editClick);
    }

    private void navigateToEditSettings(){

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
