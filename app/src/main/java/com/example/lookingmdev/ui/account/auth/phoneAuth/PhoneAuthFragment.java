package com.example.lookingmdev.ui.account.auth.phoneAuth;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.databinding.FragmentEmailAuthBinding;
import com.example.lookingmdev.databinding.FragmentPhoneAuthBinding;
import com.example.lookingmdev.ui.account.AccountFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class PhoneAuthFragment extends Fragment {

    private PhoneAuthViewModel mViewModel;
    private FragmentPhoneAuthBinding binding;

    // переменная с домашним фрагментом аккаунта (переходим сюда при удачной авторизации)
    private AccountFragment accountFragment;

    Button signInButton;
    EditText phoneEditText;

    public static PhoneAuthFragment newInstance() {
        return new PhoneAuthFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPhoneAuthBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        signInButton = root.findViewById(R.id.sign_in_with_phone_button);
        phoneEditText = root.findViewById(R.id.editTextPhone);

        // блокируем кнопку
        //signInButton.setEnabled(false);
        //signInButton.setBackgroundColor(Color.parseColor("#808080"));

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(MainActivity.firebaseAuth)
                                .setPhoneNumber(phoneEditText.getText().toString())       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(getActivity())                 // Activity (for callback binding)
                                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        Toast.makeText(root.getContext(), "Успешная верификация", Toast.LENGTH_SHORT).show();

                                        signInWithPhoneAuthCredential(phoneAuthCredential, root);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                    }
                                    @Override
                                    public void onCodeSent(@NonNull String verificationId,
                                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                        // The SMS verification code has been sent to the provided phone number, we
                                        // now need to ask the user to enter the code and then construct a credential
                                        // by combining the code with a verification ID.

                                        // Save verification ID and resending token so we can use them later
                                        //mVerificationId = verificationId;
                                        //mResendToken = token;
                                    }
                                })          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);

                //PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            }
        });

        return root;
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential, View root) {
        MainActivity.firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(root.getContext(), "signInWithCredential:success", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(root.getContext(), "signInWithCredential:failure", Toast.LENGTH_SHORT).show();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                ;
                            }
                        }
                    }
                });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PhoneAuthViewModel.class);
        // TODO: Use the ViewModel
    }

}