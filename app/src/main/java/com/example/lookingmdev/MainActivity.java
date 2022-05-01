package com.example.lookingmdev;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.lookingmdev.databinding.ActivityMainBinding;
import com.example.lookingmdev.ui.account.AccountFragment;
import com.example.lookingmdev.ui.account.auth.AuthenticationFragment;
import com.example.lookingmdev.ui.account.create.CreateAccountFragment;
import com.example.lookingmdev.ui.account.emailAuth.EmailAuthFragment;
import com.example.lookingmdev.ui.account.googleAuth.GoogleAuthFragment;
import com.example.lookingmdev.ui.booking.BookingFragment;
import com.example.lookingmdev.ui.calendar.FragmentCalendar;
import com.example.lookingmdev.ui.hostels.PageWithHostelsFragment;
import com.example.lookingmdev.ui.saved.SavedFragment;
import com.example.lookingmdev.ui.search.SearchFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static boolean created = false;  // запущено ли приложение

    private static int searchState = 0; // отвечает за состояние вкладки поиска (их будет около трех)
    private static int selectedPage = 0; // отвечает за то, какой фрагмент сейчас выведен на экран

    //TODO create getters/setters
    public static String startWeekDay, endWeekDay, startMonth, endMonth, startDay, endDay, date, visitors;
    public static int rooms, adults, children;
    public static List<Date> selectedDates;

    SearchFragment searchFragment = new SearchFragment();
    SavedFragment savedFragment = new SavedFragment();
    BookingFragment bookingFragment = new BookingFragment();
    AccountFragment accountFragment = new AccountFragment();

    FragmentCalendar fragmentCalendar = new FragmentCalendar();
    PageWithHostelsFragment pageWithHostelsFragment = new PageWithHostelsFragment();

    AuthenticationFragment authenticationFragment = new AuthenticationFragment();
    GoogleAuthFragment googleAuthFragment = new GoogleAuthFragment();
    EmailAuthFragment emailAuthFragment = new EmailAuthFragment();
    CreateAccountFragment createAccountFragment = new CreateAccountFragment();

    @SuppressLint("NonConstantResourceId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.lookingmdev.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (!created){
            replaceFragment(searchFragment);
            created = true;
        }

        // здесь обрабатываем нажатие на навигационном баре
        binding.navView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.navigation_search:

                    // если мы на начальной странице поиска
                    if (searchState == 0) {
                        replaceFragment(searchFragment);
                    // если открыты отели или страница отеля и мы изначально были на вкладке поиска
                    } else if (selectedPage == 0) {
                        replaceFragment(searchFragment);
                        searchState = 0;
                    // открываем страницу с отелями
                    } else {
                        replaceFragment(pageWithHostelsFragment);
                    }
                    selectedPage = 0;
                    break;
                case R.id.navigation_saved:
                    selectedPage = 1;
                    replaceFragment(savedFragment);
                    break;
                case R.id.navigation_booking:
                    selectedPage = 2;
                    replaceFragment(bookingFragment);
                    break;
                case R.id.navigation_account:
                    selectedPage = 3;
                    replaceFragment(accountFragment);
                    break;
            }
            return true;
        });


    }

    // метод смены фрагмента на соответствующий в зависимости от кнопки
    @SuppressLint("NonConstantResourceId")
    public void changeFragment(View view){

        switch (view.getId()) {
            case R.id.search_button:
//                if (visitors != null && date != null) {
//                replaceFragment(pageWithHostelsFragment, "left");
//                searchState = 1;
//                }
                replaceFragment(pageWithHostelsFragment, "left");
                searchState = 1;
                break;
            case R.id.sign_in_button:
                replaceFragment(authenticationFragment, "up");
                break;
            case R.id.close_auth_imageButton:
                replaceFragment(accountFragment, "down");
                break;
            case R.id.sign_with_google_button:
                replaceFragment(googleAuthFragment);
                break;
            case R.id.sign_with_email_button:
                replaceFragment(emailAuthFragment);
                break;
            case R.id.create_account_button:
                replaceFragment(createAccountFragment, "left");
                break;
            case R.id.back_create_imageButton:
                replaceFragment(authenticationFragment, "right");
                break;
        }
    }

    public void openCalendar(View view) {
        replaceFragment(fragmentCalendar, "up");
    }

    public void closeCalendar(View view) {
        replaceFragment(searchFragment);
    }





    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
//                R.anim.slide_out_left);
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment);
        fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment, String move) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (move) {
            case "up":
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.fade_out);
                break;
            case "left":
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case "right":
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case "down":
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.slide_out_down);
                break;
        }

        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment);
        fragmentTransaction.commit();
    }
}