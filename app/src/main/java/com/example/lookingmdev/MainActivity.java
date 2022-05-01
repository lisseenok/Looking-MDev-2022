package com.example.lookingmdev;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lookingmdev.databinding.ActivityMainBinding;
import com.example.lookingmdev.model.HostelCard;
import com.example.lookingmdev.server.ServerConnector;
import com.example.lookingmdev.ui.account.AccountFragment;
import com.example.lookingmdev.ui.auth.AuthenticationFragment;
import com.example.lookingmdev.ui.booking.BookingFragment;
import com.example.lookingmdev.ui.calendar.FragmentCalendar;
import com.example.lookingmdev.ui.destination.DestinationFragment;
import com.example.lookingmdev.ui.hostels.PageWithHostelsFragment;
import com.example.lookingmdev.ui.saved.SavedFragment;
import com.example.lookingmdev.ui.search.SearchFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static boolean created = false;  // запущено ли приложение

    private static int searchState = 0; // отвечает за состояние вкладки поиска (их будет около трех)
    private static int accountState = 0; // отвечает за состояние вкладки аккаунта (их будет около трех)
    private static int selectedPage = 0; // отвечает за то, какой фрагмент сейчас выведен на экран

    //TODO create getters/setters
    public static String startWeekDay, endWeekDay, startMonth, endMonth, startDay, endDay, city, date, visitors;
    public static int rooms, adults, children;
    public static List<Date> selectedDates;

    SearchFragment searchFragment = new SearchFragment();
    SavedFragment savedFragment = new SavedFragment();
    BookingFragment bookingFragment = new BookingFragment();
    AccountFragment accountFragment = new AccountFragment();

    DestinationFragment destinationFragment = new DestinationFragment();
    FragmentCalendar fragmentCalendar = new FragmentCalendar();
    PageWithHostelsFragment pageWithHostelsFragment = new PageWithHostelsFragment();

    AuthenticationFragment authenticationFragment = new AuthenticationFragment();

    @SuppressLint("NonConstantResourceId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // хз че за биндинг, еще не разбирался
        com.example.lookingmdev.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
                    // если мы на начальной странице аккаунта были
                    if (accountState == 0)
                        replaceFragment(accountFragment);
                    // если открыта вкладка авторизации и мы изначально были на вкладке аккаунта
                    else if (selectedPage == 3) {
                        replaceFragment(accountFragment);
                        accountState = 0;
                    // открываем страницу с отелями
                    } else {
                        replaceFragment(authenticationFragment);
                    }

                    selectedPage = 3;
                    break;
            }
            return true;
        });


    }

    // если проиходит какое-либо прикосновение к экрану, ты мы скрываем клавиатуру
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard(this);
        return false;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        try {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException nullPointerException) {
            System.out.println("NullPointerException" + nullPointerException);
        }
    }

    // обработка нажатия системной кнопки назад
    public void onBackPressed() {
        // смотря какая страница открыта на навигационном баре
        switch (selectedPage) {
            // если страница поиска
            case 0:
                // если не начальная страница, то уменьшаем индекс открытости страницы на один
                if (searchState > 0)
                    --searchState;
                // смотря на какой мы странице относительно индекса - открываем соответствующий фрагмент
                switch (searchState){
                    case 0:
                        replaceFragment(searchFragment);
                }
        }

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
                replaceFragment(authenticationFragment, "left");
                accountState = 1;
                break;
            case R.id.back_imageButton:
                replaceFragment(accountFragment, "right");
                accountState = 0;
                break;
        }
    }

    public void openCalendar(View view) {
//        hideSoftKeyboard(this);
        replaceFragment(fragmentCalendar, "up");
    }

    public void closeCalendar(View view) {
        replaceFragment(searchFragment, "down");
    }

    public void openDestination(View view) {
        replaceFragment(destinationFragment, "up");
    }

    public void closeDestination(View view) {
        replaceFragment(searchFragment, "down");
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment);
        fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment, String move) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (move) {
            case "up":
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                break;
            case "down":
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down);
                break;
            case "left":
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
            case "right":
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
        }

        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment);
        fragmentTransaction.commit();
    }
}