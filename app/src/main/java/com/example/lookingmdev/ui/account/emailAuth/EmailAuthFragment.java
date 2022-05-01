package com.example.lookingmdev.ui.account.emailAuth;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lookingmdev.R;
import com.example.lookingmdev.databinding.FragmentCreateAccountBinding;
import com.example.lookingmdev.databinding.FragmentEmailAuthBinding;
import com.google.firebase.auth.FirebaseAuth;

public class EmailAuthFragment extends Fragment {

    private EmailAuthViewModel mViewModel;
    private FragmentEmailAuthBinding binding;

    // editTexts с почтой и паролем
    private EditText emailEditText, passwordEditText;

    // кнопка входа
    private Button signButton;


    public static EmailAuthFragment newInstance() {
        return new EmailAuthFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEmailAuthBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // находим все элементы по id
        emailEditText = root.findViewById(R.id.editTextEmailAddressSign);
        passwordEditText = root.findViewById(R.id.editTextPasswordSing);

        signButton = root.findViewById(R.id.sign_with_email_button);


//        // ставим слушатель на editTexts (чтобы сразу сообщать об ошибках)
//        emailEditText.addTextChangedListener(watcher);
//        repPasswordEditText.addTextChangedListener(watcher);
//        passwordEditText.addTextChangedListener(watcher);

        return root;
    }



}