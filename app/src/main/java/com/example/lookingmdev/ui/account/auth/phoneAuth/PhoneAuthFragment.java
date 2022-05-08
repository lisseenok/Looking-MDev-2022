package com.example.lookingmdev.ui.account.auth.phoneAuth;

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
import com.example.lookingmdev.ui.account.auth.phoneAuth.sms.SmsCodeFragment;
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

    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    PhoneAuthCredential credential;


    public static PhoneAuthFragment newInstance() {
        return new PhoneAuthFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPhoneAuthBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        signInButton = root.findViewById(R.id.receive_sms_code_button);
        phoneEditText = root.findViewById(R.id.editTextPhone);

        // блокируем кнопку
        //signInButton.setEnabled(false);
        //signInButton.setBackgroundColor(Color.parseColor("#808080"));

//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PhoneAuthOptions options =
//                        PhoneAuthOptions.newBuilder(MainActivity.firebaseAuth)
//                                .setPhoneNumber(phoneEditText.getText().toString())       // Phone number to verify
//                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                                .setActivity(getActivity())                 // Activity (for callback binding)
//                                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                                    @Override
//                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                        System.out.println(phoneAuthCredential.getSmsCode());
//                                        credential = phoneAuthCredential;
//                                    }
//
//                                    @Override
//                                    public void onVerificationFailed(@NonNull FirebaseException e) {
//                                        Log.w(TAG, "onVerificationFailed", e);
//                                    }
//                                    @Override
//                                    public void onCodeSent(@NonNull String verificationId,
//                                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                                        // The SMS verification code has been sent to the provided phone number, we
//                                        // now need to ask the user to enter the code and then construct a credential
//                                        // by combining the code with a verification ID.
//                                        Log.d(TAG, "onCodeSent:" + verificationId);
//                                        // Save verification ID and resending token so we can use them later
//                                        mVerificationId = verificationId;
//                                        mResendToken = token;
//
//                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, new SmsCodeFragment(credential));
//                                        fragmentTransaction.commit();
//
//                                    }
//                                })          // OnVerificationStateChangedCallbacks
//                                .build();
//                PhoneAuthProvider.verifyPhoneNumber(options);
//
//            }
//        });

        return root;
    }



}