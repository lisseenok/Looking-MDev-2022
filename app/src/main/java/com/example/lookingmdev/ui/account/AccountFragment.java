package com.example.lookingmdev.ui.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.ui.account.auth.editAccount.EditAccountFragment;

public class AccountFragment extends Fragment {

    private AccountViewModel mViewModel;
    private View view;


    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // если мы авторизованы
        if (MainActivity.isAuth) {
            // отрисовываем интерфейс fragment_auth_account - это xml самого аккаунта авторизованного пользователя
            view = inflater.inflate(R.layout.fragment_auth_account, container, false);
            // находим кнопочку выхода из аккаунта и кнопку редактирования аккаунта
            Button signOutButton = view.findViewById(R.id.sign_out_button);
            Button editAccountButton = view.findViewById(R.id.edit_account_button);
            // находим textview для почты
            TextView emailTextView = view.findViewById(R.id.email_textview);
            // ставим в него почту текущего пользователя
            emailTextView.setText(MainActivity.firebaseAuth.getCurrentUser().getEmail());
            // слушаем ее
            signOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // при нажатии выходим из аккаунта
                    MainActivity.firebaseAuth.signOut();
                    // меняем флаг
                    MainActivity.isAuth = false;
                    // закрытие всех страниц
                    MainActivity.bookingState = 0;
                    MainActivity.searchState = 0;
                    MainActivity.savedState = 0;

                    MainActivity.children = 0;
                    MainActivity.children = 0;
                    MainActivity.children = 0;

                    // меняем стейт аккаунта
//                    MainActivity.accountState = 0;
                    // и меняем фрагмент на фрагмент авторизации
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, newInstance());
                    fragmentTransaction.commit();
                }
            });

        } else {
            // а если мы не авторизованы то просто отрисовываем фрагмент авторизации
            view = inflater.inflate(R.layout.fragment_account, container, false);
        }

        return view;
    }


}