package com.example.lookingmdev;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import android.view.inputmethod.InputMethodManager;


import com.example.lookingmdev.databinding.ActivityMainBinding;
import com.example.lookingmdev.model.HostelCard;
import com.example.lookingmdev.ui.account.AccountFragment;
import com.example.lookingmdev.ui.account.auth.AuthenticationFragment;
import com.example.lookingmdev.ui.account.auth.create.CreateAccountFragment;
import com.example.lookingmdev.ui.account.auth.emailAuth.EmailAuthFragment;
import com.example.lookingmdev.ui.account.auth.phoneAuth.PhoneAuthFragment;
import com.example.lookingmdev.ui.booking.BookingFragment;
import com.example.lookingmdev.ui.calendar.FragmentCalendar;
import com.example.lookingmdev.ui.destination.DestinationFragment;
import com.example.lookingmdev.ui.hostelPage.HostelPageFragment;
import com.example.lookingmdev.ui.hostels.PageWithHostelsFragment;
import com.example.lookingmdev.ui.saved.SavedFragment;
import com.example.lookingmdev.ui.search.SearchFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static boolean created = false;  // запущено ли приложение

    /**
     * searchState:
     * <ul>
     *     <li>0 - начальная страница</li>
     *     <li>1 - страница с отелями</li>
     *     <li>2 - страница выбранного отеля</li>
     *     <li>-1 - страница ввода города/даты</li>
     * </ul>
     **/
    public static int searchState = 0; // отвечает за состояние вкладки поиска (их будет около трех)

    /**
     * accountState:
     * <ul>
     *     <li>0 - начальная страница</li>
     *     <li>1 - страница с предложением авторизации/зарегистрироваться</li>
     *     <li>2 - страница создания аккаунта/авторизации через email/google</li>
     * </ul>
     **/
    public static int accountState = 0; // отвечает за состояние вкладки аккаунта (их будет около трех)
    public static int selectedPage = 0; // отвечает за то, какой фрагмент сейчас выведен на экран

    //TODO create getters/setters
    public static String startWeekDay, endWeekDay, startMonth, endMonth, startDay, endDay, city, date, visitors;

    public static int rooms = 1, adults = 1, children;
    public static List<Date> selectedDates;
    public static HostelCard hostelCard;

    // бд и ключ
    public static DatabaseReference databaseReference;
    public static String HOSTELS_KEY = "Hostels";

    // переменная, в которой лежат "инструменты авторизации бд"
    public static FirebaseAuth firebaseAuth;
    // авторизован ли пользователь (да - true, нет - false)
    public static boolean isAuth;

    SearchFragment searchFragment = new SearchFragment();
    SavedFragment savedFragment = new SavedFragment();
    BookingFragment bookingFragment = new BookingFragment();
    AccountFragment accountFragment = new AccountFragment();
    public static HostelPageFragment hostelPageFragment;

    FragmentCalendar fragmentCalendar = new FragmentCalendar();
    PageWithHostelsFragment pageWithHostelsFragment = new PageWithHostelsFragment();

    AuthenticationFragment authenticationFragment = new AuthenticationFragment();
    /** эти фрагменты пока не нужны
     * GoogleAuthFragment googleAuthFragment = new GoogleAuthFragment();
     * EmailAuthFragment emailAuthFragment = new EmailAuthFragment();
     * CreateAccountFragment createAccountFragment = new CreateAccountFragment();
     **/

    @SuppressLint("NonConstantResourceId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // хз че за биндинг, еще не разбирался
        com.example.lookingmdev.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // инициализируем бд
        databaseReference = FirebaseDatabase.getInstance().getReference(HOSTELS_KEY);

//        ServerConnector serverConnector = new ServerConnector();
//        serverConnector.pushHostelsToServer();

        // инициализируем бд
        firebaseAuth = FirebaseAuth.getInstance();

        // устанавливаем русский язык для смс
        firebaseAuth.setLanguageCode("ru");

        // получаем ответ, авторизован ли пользователь
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        // firebaseUser = null когда пользователь не авторизован
        if (firebaseUser == null) isAuth = false;
        else isAuth = true;


        if (!created){
            replaceFragment(searchFragment);
            created = true;
        }

        // здесь обрабатываем нажатие на навигационном баре
        binding.navView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.navigation_search:
                    // если мы на странице поиска, то возвращаемся на начальную
                    if (selectedPage == 0) {
                        replaceFragment(searchFragment);
                        searchState = 0;
                    } else {
                        // если переходим на вкладку поиска с другой вкладки, то открываем тот фрагмент, который был открыт последним в поиске
                        switch (searchState) {
                            case 0:
                            case -1:
                                replaceFragment(searchFragment);
                                break;
                            case 1:
                                replaceFragment(pageWithHostelsFragment);
                                break;
                            case 2:
                                replaceFragment(hostelPageFragment);
                                break;
                        }
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
                    // если мы на странице аккаунта, то возвращаемся на начальную
                    if (selectedPage == 3) {
                        replaceFragment(accountFragment);
                        accountState = 0;
                    } else {
                        // если переходим на вкладку аккаунта с другой вкладки, то открываем тот фрагмент, который был открыт последним в поиске
                        switch (accountState) {
                            case 0:
                                replaceFragment(accountFragment);
                                break;
                            case 1:
                            case 2: // тут я решил все равно открывать фрагмент авторизации даже если вышли на фрагменте создать/войти
                                // открываем фрагмент авторизации только если не авторизованы
                                if (!isAuth) replaceFragment(authenticationFragment);
                                // а иначе открываем наш аккаунт
                                else replaceFragment(accountFragment);
                                accountState = 1;
                                break;
                        }
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
    @Override
    public void onBackPressed() {
        // смотря какая страница открыта на навигационном баре
        switch (selectedPage) {
            // если страница поиска
            case 0:
                // если не начальная страница, то уменьшаем индекс открытости страницы на один
                if (searchState > 0)
                    --searchState;
                // смотря на какой мы теперь странице относительно индекса - открываем соответствующий фрагмент
                switch (searchState){
                    case -1:
                        replaceFragment(searchFragment, "down");
                    case 0:
                        replaceFragment(searchFragment, "right");
                        break;
                    case 1:
                        replaceFragment(pageWithHostelsFragment, "right");
                        break;
                }
                break;
            case 3:
                if (accountState > 0) {
                    --accountState;
                }
                switch (accountState) {
                    case 0:
                        replaceFragment(accountFragment, "down");
                        break;
                    case 1:
                        replaceFragment(authenticationFragment, "right");
                        break;
                }
        }

    }

    // метод смены фрагмента на соответствующий в зависимости от кнопки
    @SuppressLint("NonConstantResourceId")
    public void changeFragment(View view){

        switch (view.getId()) {

            // нажали на кнопку назад на странице отеля
            case R.id.backFromHostelPage:
                replaceFragment(pageWithHostelsFragment, "right");
                searchState = 1;
                break;

            // нажали на выбор дат
            case R.id.datesLayout:
                replaceFragment(fragmentCalendar, "up");
                searchState = -1;
                break;

            // нажали на кнопку ПРИМЕНИТЬ в выборе города или
            // нажали назад на фрагменте выбора города или
            // нажали применить в фраменте календаря

            case R.id.applyButtonDestination:
            case R.id.back_destination_image_button:
            case R.id.calendarButton:
                replaceFragment(searchFragment, "down");
                searchState = 0;
                break;

            // открыли фрагмент выбора города
            case R.id.townLayout:
                replaceFragment(new DestinationFragment(), "up");
                searchState = -1;
                break;
            // нажали кнопку найти
            case R.id.search_button:
//                if (visitors != null && date != null) {
//                replaceFragment(pageWithHostelsFragment, "left");
//                searchState = 1;
//                }
                replaceFragment(pageWithHostelsFragment, "left");
                searchState = 1;
                break;

            // нажали кнопку войти
            case R.id.sign_in_button:
                replaceFragment(authenticationFragment, "up");
                accountState = 1;
                break;

            // вышли с фрагмента выбора авторизации
            case R.id.close_auth_imageButton:
                replaceFragment(accountFragment, "down");
                accountState = 0;
                break;

            // авторизация через гугл

            case R.id.sign_with_phone_button:
                accountState = 2;
                replaceFragment(new PhoneAuthFragment(), "left");
                break;


            // авторизация через email
            case R.id.sign_with_email_button:
                accountState = 2;
                replaceFragment(new EmailAuthFragment(), "left");
                break;

            // нажали создать аккаунт
            case R.id.create_account_button:
                accountState = 2;
                replaceFragment(new CreateAccountFragment(), "left");
                break;

            // нажали назад в авторизации через email/google
            case R.id.back_email_imageButton:
            case R.id.back_create_imageButtonReg:
                accountState = 1;
                replaceFragment(authenticationFragment, "right");
                break;

            // нажали назад на странице отелей
            case R.id.backFromPageWithHostels:
                replaceFragment(searchFragment, "right");
                searchState = 0;
                break;
        }
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