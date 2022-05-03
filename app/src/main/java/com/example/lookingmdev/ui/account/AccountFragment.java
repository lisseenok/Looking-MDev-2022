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

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;

public class AccountFragment extends Fragment {

    private AccountViewModel mViewModel;
    private View view;


    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // если при запуске приложения уже мы авторизованы
        if (MainActivity.isAuth) {
            // отрисовываем интерфейс fragment_auth_account - это xml самого аккаунта авторизованного пользователя
            view = inflater.inflate(R.layout.fragment_auth_account, container, false);
            // находим кнопочку выхода из аккаунта
            Button signOutButton = view.findViewById(R.id.sign_out_button);
            // слушаем ее
            signOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // при нажатии выходим из аккаунта
                    MainActivity.firebaseAuth.signOut();
                    // меняем флаг
                    MainActivity.isAuth = false;
                    // меняем стейт аккаунта
//                    MainActivity.accountState = 0;
                    // и меняем фрагмент на фрагмент авторизации
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, newInstance());
                    fragmentTransaction.commit();
                }
            });

        } else{
            // а если при запуске приложения мы не авторизованы то просто отрисовываем фрагмент авторизации
            view = inflater.inflate(R.layout.fragment_account, container, false);
        }

        return view;
    }


}