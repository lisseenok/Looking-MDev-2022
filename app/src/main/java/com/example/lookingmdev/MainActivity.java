package com.example.lookingmdev;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.lookingmdev.ui.account.AccountFragment;
import com.example.lookingmdev.ui.booking.BookingFragment;
import com.example.lookingmdev.ui.hostels.PageWithHostelsFragment;
import com.example.lookingmdev.ui.saved.SavedFragment;
import com.example.lookingmdev.ui.search.SearchFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.lookingmdev.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static int searchState = 0; // отвечает за состояние вкладки поиска (их будет около трех)
    private static int selectedPage = 0; // отвечает за то, какой фрагмент сейчас выведен на экран


    SearchFragment searchFragment = new SearchFragment();
    SavedFragment savedFragment = new SavedFragment();
    BookingFragment bookingFragment = new BookingFragment();
    AccountFragment accountFragment = new AccountFragment();


    PageWithHostelsFragment pageWithHostelsFragment = new PageWithHostelsFragment();

    @SuppressLint("NonConstantResourceId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new SearchFragment());

        binding.navView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.navigation_search:

                    if (searchState == 0) {
                        replaceFragment(searchFragment);
                    } else if (selectedPage == 0) {
                        replaceFragment(searchFragment);
                        searchState -= 1;
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

//        BottomNavigationView navView = findViewById(R.id.nav_view); // объект панели навигации
//
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_search, R.id.navigation_saved, R.id.navigation_booking)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @SuppressLint("NonConstantResourceId")
    public void test1(View view){

        switch (view.getId()) {
            case R.id.search_button:
                replaceFragment(pageWithHostelsFragment);
                searchState += 1;
                break;
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, fragment);
        fragmentTransaction.commit();
    }
}