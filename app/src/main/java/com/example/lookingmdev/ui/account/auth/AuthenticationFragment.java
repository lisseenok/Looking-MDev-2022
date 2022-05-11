package com.example.lookingmdev.ui.account.auth;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.databinding.FragmentAuthenticationBinding;
import com.example.lookingmdev.databinding.FragmentEmailAuthBinding;
import com.example.lookingmdev.ui.account.AccountFragment;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthenticationFragment extends Fragment {

    private AuthenticationViewModel mViewModel;
    private FragmentAuthenticationBinding binding;

    private Button createAccountButton;
    private Button signWithPhone;
    private Button signWithEmailButton;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    // переменная с домашним фрагментом аккаунта (переходим сюда при удачной авторизации)
    private AccountFragment accountFragment;

    public static AuthenticationFragment newInstance() {
        return new AuthenticationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthenticationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        createAccountButton = root.findViewById(R.id.create_account_button);
        //signWithPhone = root.findViewById(R.id.sign_with_phone_button);
        signWithEmailButton = root.findViewById(R.id.sign_with_email_button);


        return root;
    }


}