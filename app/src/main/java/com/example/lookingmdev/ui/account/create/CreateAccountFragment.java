package com.example.lookingmdev.ui.account.create;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lookingmdev.R;
import com.example.lookingmdev.databinding.FragmentCreateAccountBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountFragment extends Fragment {

    private CreareAccountViewModel mViewModel;
    private FragmentCreateAccountBinding binding;

    private EditText emailEditText, passwordEditText, repPasswordEditText;
    private Button registerButton;

    private FirebaseAuth firebaseAuth;


    public static CreateAccountFragment newInstance() {
        return new CreateAccountFragment();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_create_account, container, false);

        binding = FragmentCreateAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        emailEditText = root.findViewById(R.id.editTextEmailAddress);
        passwordEditText = root.findViewById(R.id.editTextPassword);
        repPasswordEditText = root.findViewById(R.id.editTextRepeatPassword);

        registerButton = root.findViewById(R.id.create_account_and_sign_button);

        firebaseAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(passwordEditText.getText().toString().equals(repPasswordEditText.getText().toString()))){
                    Toast.makeText(root.getContext(), "пароли не совпадают", Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(root.getContext(), "ура", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(root.getContext(), "не ура", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}