package com.example.lookingmdev.ui.account.auth.phoneAuth.sms;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.databinding.FragmentPhoneAuthBinding;
import com.example.lookingmdev.databinding.FragmentSmsCodeBinding;
import com.example.lookingmdev.ui.account.AccountFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;

import java.util.concurrent.Executor;

public class SmsCodeFragment extends Fragment {

    private SmsCodeViewModel mViewModel;
    private FragmentSmsCodeBinding binding;

    EditText smsCodeEditText;
    Button signInButton;

    // переменная с домашним фрагментом аккаунта (переходим сюда при удачной авторизации)
    private AccountFragment accountFragment;

    PhoneAuthCredential credential;

    public SmsCodeFragment(PhoneAuthCredential credential){
        this.credential = credential;
    }

    public static SmsCodeFragment newInstance(PhoneAuthCredential credential) {
        return new SmsCodeFragment(credential);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSmsCodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        smsCodeEditText = root.findViewById(R.id.editTextSmsCode);
        signInButton = root.findViewById(R.id.sign_in_with_phone_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (smsCodeEditText.getText().toString().equals(credential.getSmsCode())){
                    signInWithPhoneAuthCredential(credential, root);
                    System.out.println("wwwwwwww");
                }

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
                            Log.d(TAG, "signInWithCredential:success");
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();
                            // Update UI

                            MainActivity.isAuth = true;

                            // делаем переход на домашний фрагмент аккаунта (newInstance() - возвращаем просто новый экземпляр класса)
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, accountFragment.newInstance());
                            MainActivity.accountState = 0;
                            fragmentTransaction.commit();
                        } else {
                            Log.d(TAG, "signInWithCredential:success");
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                ;
                            }
                        }
                    }
                });
    }



}