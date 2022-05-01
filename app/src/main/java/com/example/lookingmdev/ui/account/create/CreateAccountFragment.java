package com.example.lookingmdev.ui.account.create;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

    // editTexts с почтой и двумя паролями
    private EditText emailEditText, passwordEditText, repPasswordEditText;
    // кнопка регистрации
    private Button registerButton;
    // textView где показываются сообщения об ошибках (пароли не совпадают)
    private TextView textView;

    // переменная, в которой лежат "инструменты авторизации бд"
    private FirebaseAuth firebaseAuth;


    public static CreateAccountFragment newInstance() {
        return new CreateAccountFragment();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCreateAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // находим все элементы по id
        emailEditText = root.findViewById(R.id.editTextEmailAddress);
        passwordEditText = root.findViewById(R.id.editTextPassword);
        repPasswordEditText = root.findViewById(R.id.editTextRepeatPassword);

        registerButton = root.findViewById(R.id.create_account_and_sign_button);
        textView = root.findViewById(R.id.flagTextView);

        // инициализируем бд
        firebaseAuth = FirebaseAuth.getInstance();

        // ставим слушатель на editTexts (чтобы сразу сообщать об ошибках)
        emailEditText.addTextChangedListener(watcher);
        repPasswordEditText.addTextChangedListener(watcher);
        passwordEditText.addTextChangedListener(watcher);

        // обрабатываем нажатие на кнопку регистрации
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // создаем пользователя по почте и паролю
                firebaseAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(root.getContext(), "Успешная регистрация", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(root.getContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        return root;
    }

    // слушатель для EditTexts
    final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // проверка что пароли одинаковые и не пустные
            if ((!passwordEditText.getText().toString().equals(repPasswordEditText.getText().toString()))
                    && passwordEditText.getText().toString().length() != 0
                    && repPasswordEditText.getText().toString().length() != 0) {
                textView.setText("Пароли не совпадают");
            } else {
                textView.setText("");
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
            // те же проверки
            if (emailEditText.getText().toString().length() == 0 ||
                    passwordEditText.getText().toString().length() == 0 || repPasswordEditText.getText().toString().length() == 0 ||
                    !passwordEditText.getText().toString().equals(repPasswordEditText.getText().toString())) {
                registerButton.setEnabled(false);
                registerButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            } else {
                // причем не даем нажать кнопку если что-то не так
                registerButton.setEnabled(true);
                registerButton.setBackgroundColor(Color.parseColor("#0070C2"));
            }
        }
    };



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}