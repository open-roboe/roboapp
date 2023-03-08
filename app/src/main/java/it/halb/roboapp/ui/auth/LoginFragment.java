package it.halb.roboapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import it.halb.roboapp.R;
import it.halb.roboapp.databinding.FragmentLoginBinding;
import it.halb.roboapp.ui.main.MainActivity;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ViewModel initialization
        LoginViewModel model = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setLoginViewModel(model);

        //ViewModel listeners
        model.getUsernameError().observe(getViewLifecycleOwner(), error ->{
            binding.textInputUsername.setError(error);
        });
        model.getPasswordError().observe(getViewLifecycleOwner(), error ->{
            binding.textInputPassword.setError(error);
        });
        model.getGenericError().observe(getViewLifecycleOwner(), error ->{
            if(error != null && error.length() > 0)
                Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show();
        });
        model.getAccount().observe(getViewLifecycleOwner(), account -> {
            if(account != null){
                //there is an account! move to main activity
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        //view listeners
        binding.buttonLogin.setOnClickListener(v -> {
            model.login();
        });
        binding.buttonEditServer.setOnClickListener(v ->{
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_urlConfigFragment);
        });

    }
}