package com.example.lookingmdev;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.example.lookingmdev.databinding.ActivityMainBinding;
import com.example.lookingmdev.model.HostelCard;
import com.example.lookingmdev.ui.account.AccountFragment;
import com.example.lookingmdev.ui.account.auth.AuthenticationFragment;
import com.example.lookingmdev.ui.account.auth.create.CreateAccountFragment;
import com.example.lookingmdev.ui.account.auth.editAccount.EditAccountFragment;
import com.example.lookingmdev.ui.account.auth.emailAuth.EmailAuthFragment;
import com.example.lookingmdev.ui.booking.BookingFragment;
import com.example.lookingmdev.ui.calendar.FragmentCalendar;
import com.example.lookingmdev.ui.destination.DestinationFragment;
import com.example.lookingmdev.ui.hostelPage.HostelPageFragment;
import com.example.lookingmdev.ui.hostels.PageWithSearchHostelsFragment;
import com.example.lookingmdev.ui.saved.SavedFragment;
import com.example.lookingmdev.ui.search.SearchFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static int savedState = 0;
    public static int bookingState = 0;

    //TODO create getters/setters
    public static String startWeekDay, endWeekDay, startMonth, endMonth, startDay, endDay, city, date, visitors;

    public static int rooms = 1, adults = 1, children = 0;
    public static List<Date> selectedDates;
    public static HostelCard searchHostelCard;
    public static HostelCard savedHostelCard;
    public static HostelCard bookingHostelCard;

    // бд и ключ для коллекции отелей
    public static DatabaseReference databaseReference;
    public static List<HostelCard> allHostelsList = new ArrayList<>();
    public static String HOSTELS_KEY = "Hostels";

    // бд и ключ коллекции избранных
    public static DatabaseReference databaseSavedReference;
    public static String SAVED_KEY = "Saved";

    // бд и ключ коллекции забронированных
    public static DatabaseReference databaseBookingsReference;
    public static String BOOKINGS_KEY = "Bookings";

    // список избранных отелей текущего пользователя
    public static List<HostelCard> savedHostels = new ArrayList<>();

    // список забронированных отелей текущего пользователя
    public static List<HostelCard> bookingsHostels = new ArrayList<>();

    // переменная, в которой лежат "инструменты авторизации бд"
    public static FirebaseAuth firebaseAuth;

    // авторизован ли пользователь (да - true, нет - false)
    public static boolean isAuth;

    // переменная с объектом пользователя
    public static FirebaseUser firebaseUser;

    public static SearchFragment searchFragment = new SearchFragment();
    public static SavedFragment savedFragment = new SavedFragment();
    public static BookingFragment bookingFragment = new BookingFragment();
    AccountFragment accountFragment = new AccountFragment();
    public static HostelPageFragment hostelPageFragment;

    public static FragmentCalendar fragmentCalendar = new FragmentCalendar();
    PageWithSearchHostelsFragment pageWithSearchHostelsFragment = new PageWithSearchHostelsFragment();

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

        System.out.println("onCreate");



        // хз че за биндинг, еще не разбирался
        com.example.lookingmdev.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // инициализируем бд

        // колллекция отелей
        databaseReference = FirebaseDatabase.getInstance().getReference(HOSTELS_KEY);

        // коллекция избранного
        databaseSavedReference = FirebaseDatabase.getInstance().getReference(SAVED_KEY);

        // коллекция забронированного
        databaseBookingsReference = FirebaseDatabase.getInstance().getReference(BOOKINGS_KEY);

        // блок кода для загрузки отелей в бд
//        ServerConnector serverConnector = new ServerConnector();
//        serverConnector.pushHostelsToServer();

        // инициализируем бд
        firebaseAuth = FirebaseAuth.getInstance();

        // устанавливаем русский язык для смс
        firebaseAuth.setLanguageCode("ru");



        // получаем ответ, авторизован ли пользователь
        firebaseUser = firebaseAuth.getCurrentUser();

        // firebaseUser = null когда пользователь не авторизован
        if (firebaseUser == null){
            isAuth = false;
        }
        else {
            isAuth = true;
            // вызываем метод получения избранных отелей с бд (записываются в статическое поле savedHostels)
            getSavedHostelsFromServer(MainActivity.firebaseUser.getUid());
            getBookingsHostelsFromServer(MainActivity.firebaseUser.getUid());
            getAllHostelsFromServer(MainActivity.firebaseUser.getUid());


        }

        // получаем все отели в глобальную переменную allHostelsList


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
                                replaceFragment(pageWithSearchHostelsFragment);
                                break;
                            case 2:
                                replaceFragment(new HostelPageFragment());
                                break;
                        }
                    }
                    selectedPage = 0;

                    break;
                // если мы нажали на вкладку с избранным
                case R.id.navigation_saved:

                    // если мы и так были на этой странице
                    if (selectedPage == 1) {
                        replaceFragment(savedFragment);
                        savedState = 0;
                    } else {
                        // если мы были на другой странице
                        switch (savedState) {
                            case 0:
                                replaceFragment(savedFragment);
                                break;
                            case 1:
                                replaceFragment(new HostelPageFragment());
                                break;
                        }
                    }

                    selectedPage = 1;
                    break;

                case R.id.navigation_booking:
                    // если мы и так были на этой странице
                    if (selectedPage == 2) {
                        replaceFragment(bookingFragment);
                        bookingState = 0;
                    } else {
                        // если мы были на другой странице
                        switch (bookingState) {
                            case 0:
                                replaceFragment(bookingFragment);
                                break;
                            case 1:
                                replaceFragment(new HostelPageFragment());
                                break;
                        }
                    }

                    selectedPage = 2;
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
//            System.out.println("NullPointerException " + nullPointerException);
        }
    }

    // обработка нажатия системной кнопки назад
    @Override
    public void onBackPressed() {
//        System.out.println(selectedPage);
//        System.out.println(savedState);
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
                        replaceFragment(pageWithSearchHostelsFragment, "right");
                        break;
                }
                break;
            case 1:
                if (savedState > 0)
                    --savedState;
                switch (savedState) {
                    case 0:
                        replaceFragment(savedFragment, "right");
                        break;
                }
                break;
            case 2:
                if (bookingState > 0)
                    --bookingState;
                switch (bookingState) {
                    case 0:
                        replaceFragment(bookingFragment, "right");
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
                // если мы были на странице отеля через вкладку поиска, то
                if (selectedPage == 0) {
                replaceFragment(pageWithSearchHostelsFragment, "right");
                searchState = 1;

                }
                // если мы были на странцие отеля через вкладку сохраненного, то
                else if (selectedPage == 1) {
                    replaceFragment(savedFragment, "right");
                    savedState = 0;
                }
                // если мы были на странцие отеля через вкладку сохраненного, то
                else if (selectedPage == 2) {
                    replaceFragment(bookingFragment, "right");
                    bookingState = 0;
                }
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
                replaceFragment(searchFragment, "down");
                searchState = 0;
                break;

            // открыли фрагмент выбора города
            case R.id.townLayout:
                replaceFragment(new DestinationFragment(), "up");
                searchState = -1;
                break;

            // нажали кнопку найти на странице поиска
            case R.id.search_button:
                if (visitors != null && date != null) {
                    replaceFragment(pageWithSearchHostelsFragment, "left");
                    searchState = 1;
                } else
                    Toast.makeText(view.getContext(), "Сначала введите все данные", Toast.LENGTH_LONG).show();
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

            case R.id.edit_account_button:
                //accountState = ?
                replaceFragment(new EditAccountFragment(), "up");
                break;

            // нажали назад на странице редактирования аккаунта
            case R.id.back_editing_imageButton:
                accountState = 0;
                replaceFragment(accountFragment, "down");
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



    // метод получения избранных отелей для пользователя по id (отели кладутся в статическое поле savedHostels)
    public static void getSavedHostelsFromServer(String uid){

        // создаем слушатель базы данных
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("================Saved data changed================");
                // приходят данные (в объекте snapshot)

                // проверка пустой ли список отелей, если нет - очищаем
                if (savedHostels.size() > 0) savedHostels.clear();

                // для каждой пары ключ (uid) - значение (список: arrayList<HostelCard>)
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    // проверяем равен ли ключ нужному uid
                    if (dataSnapshot.getKey().equals(uid)){
                        // с помощью дженерика получаем наше значение - arrayList<HostelCard> в переменную list
                        GenericTypeIndicator<List<HostelCard>> t = new GenericTypeIndicator<List<HostelCard>>() {};
                        List<HostelCard> list = dataSnapshot.getValue(t);
                        // добавляем объекты из list в наше статическое поле savedHostels
                        assert list != null;
                        savedHostels.addAll(list);

                        // завершаем цикл тк нужный ключ (uid) уже найден и его значение сохранено
                        break;
                    }

                }
                // сообщаем адаптеру, что данные поменялись
                savedFragment.updateSavedAdapter();
//                hostelCardAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        // добавляем слушатель в бд
        databaseSavedReference.addValueEventListener(valueEventListener);

    }

    // метод получения забронированных отелей для пользователя по id (отели кладутся в статическое поле bookingsHostels)
    public static void getBookingsHostelsFromServer(String uid){

        // создаем слушатель базы данных
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("================Bookings data changed================");
                // приходят данные (в объекте snapshot)

                // проверка пустой ли список отелей, если нет - очищаем
                if (bookingsHostels.size() > 0) bookingsHostels.clear();

                // для каждой пары ключ (uid) - значение (список: arrayList<HostelCard>)
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    // проверяем равен ли ключ нужному uid
                    if (dataSnapshot.getKey().equals(uid)){
                        // с помощью дженерика получаем наше значение - arrayList<HostelCard> в переменную list
                        GenericTypeIndicator<List<HostelCard>> t = new GenericTypeIndicator<List<HostelCard>>() {};
                        List<HostelCard> list = dataSnapshot.getValue(t);
                        // добавляем объекты из list в наше статическое поле savedHostels
                        assert list != null;
                        bookingsHostels.addAll(list);

                        // завершаем цикл тк нужный ключ (uid) уже найден и его значение сохранено
                        break;
                    }

                }
                // сообщаем адаптеру, что данные поменялись
                bookingFragment.updateBookingsAdapter();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        // добавляем слушатель в бд
        databaseBookingsReference.addValueEventListener(valueEventListener);

    }

    public static void getAllHostelsFromServer(String uid){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("================Getting all hostels================");

                // проверка пустой ли список отелей, если нет - очищаем
                if (allHostelsList.size() > 0) allHostelsList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HostelCard hostelCard = dataSnapshot.getValue(HostelCard.class);
                    hostelCard.setId(dataSnapshot.getKey());
                    allHostelsList.add(hostelCard);

                    // обновляем сохраненные данные и данные бронирований
                    if (isAuth) {
                        for (int i = 0; i < savedHostels.size(); ++i) {
                            if (hostelCard.getId().equals(savedHostels.get(i).getId())) {
                                savedHostels.set(i, hostelCard);
                            }
                        }
                        // заполняем каждую карточку бронирования в массиве бронирования
                        for (int i = 0; i < bookingsHostels.size(); ++i) {
                            if (hostelCard.getId().equals(bookingsHostels.get(i).getId())) {
                                bookingsHostels.get(i).setId(hostelCard.getId());
                                bookingsHostels.get(i).setHostelName(hostelCard.getHostelName());
                                bookingsHostels.get(i).setCity(hostelCard.getCity());
                                bookingsHostels.get(i).setAddress(hostelCard.getAddress());
                                bookingsHostels.get(i).setImage(hostelCard.getImage());
                                bookingsHostels.get(i).setShortDescription(hostelCard.getShortDescription());
                                bookingsHostels.get(i).setFullDescription(hostelCard.getFullDescription());
                                bookingsHostels.get(i).setAmountOfHostelRooms(hostelCard.getAmountOfHostelRooms());
                                bookingsHostels.get(i).setPrice(hostelCard.getPrice());
                                bookingsHostels.get(i).setRating(hostelCard.getRating());
                                bookingsHostels.get(i).setListOfBookingDates(hostelCard.getListOfBookingDates());
                            }
                        }
                    }

                    // обновляем текущую выбранную карточку отеля в зависимости от выбранной страницы
                    if (searchHostelCard != null && searchHostelCard.getId().equals(hostelCard.getId())) {
                        MainActivity.searchHostelCard = hostelCard;
                    }
                    if (savedHostelCard != null && savedHostelCard.getId().equals(hostelCard.getId())) {
                        MainActivity.savedHostelCard = hostelCard;
                    }
                    if (bookingHostelCard != null && bookingHostelCard.getId().equals(hostelCard.getId())) {
                        bookingHostelCard.setId(hostelCard.getId());
                        bookingHostelCard.setHostelName(hostelCard.getHostelName());
                        bookingHostelCard.setCity(hostelCard.getCity());
                        bookingHostelCard.setAddress(hostelCard.getAddress());
                        bookingHostelCard.setImage(hostelCard.getImage());
                        bookingHostelCard.setShortDescription(hostelCard.getShortDescription());
                        bookingHostelCard.setFullDescription(hostelCard.getFullDescription());
                        bookingHostelCard.setAmountOfHostelRooms(hostelCard.getAmountOfHostelRooms());
                        bookingHostelCard.setPrice(hostelCard.getPrice());
                        bookingHostelCard.setRating(hostelCard.getRating());
                        bookingHostelCard.setListOfBookingDates(hostelCard.getListOfBookingDates());

                    }
                }
                if (isAuth) {
                    // отправляем обновленные данные
                    if (savedHostels.size() != 0)
                        databaseSavedReference.child(firebaseUser.getUid()).setValue(savedHostels);
                    if (bookingsHostels.size() != 0)
                        databaseBookingsReference.child(firebaseUser.getUid()).setValue(bookingsHostels);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        // добавляем слушатель в бд
        databaseReference.addValueEventListener(valueEventListener);

    }

    // метод, проверяющий есть ли в каком-то списке List<HostelCard> какой-то объект hostelCard
    public static boolean contains(List<HostelCard> hostelCards, HostelCard hostelCard){
//        System.out.println("id текущей карточки - " + hostelCard.getId());

        for (HostelCard item : hostelCards) {
//            System.out.println("id одной из сохранненых карточек - " + item.getId());
            if (item.getId().equals(hostelCard.getId())) return true;
        }
        return false;
    }

    // метод, удаляющий из объекта savedHostels элемент с айдишником id
    public static void removeSaved(String id){
//        System.out.println(savedHostels.size());
        for (HostelCard item : savedHostels) {
            if (item.getId().equals(id)){
                savedHostels.remove(item);
                break;
            }
        }
    }

    // метод, удаляющий из объекта savedHostels элемент с айдишником id
    public static void removeBookings(String id){
        for (HostelCard item : bookingsHostels) {
            if (item.getId().equals(id)){
                bookingsHostels.remove(item);
                break;
            }
        }
    }

    public static boolean checkEmail(String email){
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }




}