package com.example.lookingmdev;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lookingmdev.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    PageWithHostelsFragment pageWithHostelsFragment = new PageWithHostelsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        BottomNavigationView navView = findViewById(R.id.nav_view); // объект панели навигации
//
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_search, R.id.navigation_saved, R.id.navigation_booking)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @SuppressLint("NonConstantResourceId")
    public void test1(View view){

        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.button:
                fragment = pageWithHostelsFragment;
                System.out.println(1);
                break;
//            case R.id.button2:
//                fragment = firstFragment;
//                break;
        }
        // метод позволяет начать транзакцию

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // в самом начале устанавливаем первый фрагмент в фрейм лояут
        ft.replace(R.id.nav_host_fragment_activity_main, fragment);
        System.out.println(2);
        ft.commit();
    }
}