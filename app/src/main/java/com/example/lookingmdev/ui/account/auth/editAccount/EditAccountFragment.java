package com.example.lookingmdev.ui.account.auth.editAccount;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
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
import com.example.lookingmdev.databinding.FragmentAuthenticationBinding;
import com.example.lookingmdev.databinding.FragmentEditAccountBinding;
import com.example.lookingmdev.ui.account.auth.emailAuth.EmailAuthFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.PhoneAuthCredential;

public class EditAccountFragment extends Fragment {

    private EditAccountViewModel mViewModel;
    private FragmentEditAccountBinding binding;

    private EditText emailEditText;
//    private EditText nameEditText;
//    private EditText phoneEditText;

    private TextView errorTextView;

    private Button confirmButton;

    private EmailAuthFragment emailAuthFragment;

    public static EditAccountFragment newInstance() {
        return new EditAccountFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEditAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        emailEditText = root.findViewById(R.id.editTextEmailAddressEdit);
//        nameEditText = root.findViewById(R.id.editTextNameEdit);
//        phoneEditText = root.findViewById(R.id.editTextPhoneEdit);

        emailEditText.setText(MainActivity.firebaseUser.getEmail());

        errorTextView = root.findViewById(R.id.flagTextViewEdit);

        confirmButton = root.findViewById(R.id.confirm_edit_button);

        confirmButton.setEnabled(false);
        confirmButton.setBackgroundColor(Color.parseColor("#808080"));

//        if (MainActivity.firebaseUser.getDisplayName().isEmpty()){
//            nameEditText.setHint("Введите имя");
//        } else {
//            nameEditText.setText(MainActivity.firebaseUser.getDisplayName());
//        }
//
//        if (MainActivity.firebaseUser.getPhoneNumber().isEmpty()){
//            phoneEditText.setHint("Введите номер телефона");
//        } else {
//            phoneEditText.setText(MainActivity.firebaseUser.getPhoneNumber());
//        }

        //phoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        emailEditText.addTextChangedListener(watcher);
        //nameEditText.addTextChangedListener(watcher);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.firebaseUser.updateEmail(emailEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(root.getContext(), "Успешно обновлено", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(root.getContext(), "Вам необходимо заново авторизоваться", Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, emailAuthFragment.newInstance());
                            MainActivity.accountState = 2;
                            fragmentTransaction.commit();
                        }
                    }
                });
            }
        });

        return root;
    }

    final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @SuppressLint("ResourceAsColor")
        @Override
        public void afterTextChanged(Editable s) {
            errorTextView.setText("");
            if (!MainActivity.checkEmail(emailEditText.getText().toString())) {
                errorTextView.setText("Почта введена неверно");
                confirmButton.setEnabled(false);
                confirmButton.setBackgroundColor(Color.parseColor("#808080"));
            } else {
                confirmButton.setEnabled(true);
                confirmButton.setBackgroundColor(Color.parseColor("#0070C2"));
                errorTextView.setText("");
            }
        }
    };
}