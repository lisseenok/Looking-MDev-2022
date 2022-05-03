package com.example.lookingmdev.ui.account.emailAuth;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.databinding.FragmentCreateAccountBinding;
import com.example.lookingmdev.databinding.FragmentEmailAuthBinding;
import com.example.lookingmdev.ui.account.AccountFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmailAuthFragment extends Fragment {

    private EmailAuthViewModel mViewModel;
    private FragmentEmailAuthBinding binding;

    // editTexts с почтой и паролем
    private EditText emailEditText, passwordEditText;

    // кнопка входа
    private Button signButton;

    // переменная с домашним фрагментом аккаунта (переходим сюда при удачной авторизации)
    private AccountFragment accountFragment;


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

        signButton = root.findViewById(R.id.sign_in_with_email_button);

        // блокируем кнопку
        signButton.setEnabled(false);
        signButton.setBackgroundColor(Color.parseColor("#808080"));

        // ставим слушатели на edittexts
        emailEditText.addTextChangedListener(watcher);
        passwordEditText.addTextChangedListener(watcher);

        // обрабатываем нажатие на кнопку входа
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.firebaseAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // если авторизация успешная
                        if (task.isSuccessful()){
                            Toast.makeText(root.getContext(), "Успешная авторизация", Toast.LENGTH_SHORT).show();
                            // флаг авторизации - true
                            MainActivity.isAuth = true;
                            // делаем переход на домашний фрагмент аккаунта (newInstance() - возвращаем просто новый экземпляр класса)
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, accountFragment.newInstance());
                            fragmentTransaction.commit();
                        }
                        else{
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
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            // те же проверки
            if (emailEditText.getText().toString().length() == 0 ||
                    passwordEditText.getText().toString().length() < 6) {
                // причем не даем нажать кнопку если что-то не так
                signButton.setEnabled(false);
                signButton.setBackgroundColor(Color.parseColor("#808080"));
            } else {
                signButton.setEnabled(true);
                signButton.setBackgroundColor(Color.parseColor("#0070C2"));
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}