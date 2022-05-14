package com.example.lookingmdev.ui.hostelPage;

import static com.example.lookingmdev.MainActivity.databaseReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.model.HostelCard;
import com.example.lookingmdev.ui.methods.Methods;

import java.util.HashMap;

public class HostelPageFragment extends Fragment {
    private HostelPageViewModel mViewModel;
    public HostelPageFragment() {

    }
    public static HostelPageFragment newInstance() {
        return new HostelPageFragment();
    }


    HostelCard hostelCard;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hostel_page, container, false);

        // короче если открыта страница поиска, то используем карточку MainActivity.searchHostelCard
        Methods methods = new Methods();

        String startWeekDay;
        String endWeekDay;
        String startMonth;
        String endMonth;
        String startDay;
        String endDay;

        // Инициализируем переменные, в которые кладем текстовые поля со страницы отеля
        ImageView hostelImage = view.findViewById(R.id.hostelImage);
        TextView hostelName = view.findViewById(R.id.hostelName);
        TextView hostelTitle = view.findViewById(R.id.hostelTitle);
        TextView hostelRatting = view.findViewById(R.id.hostelRatting);
        TextView hostelStartDate = view.findViewById(R.id.hostelStartDate);
        TextView hostelEndDate = view.findViewById(R.id.hostelEndDate);
        TextView hostelQuests = view.findViewById(R.id.hostelQuests);
        TextView hostelAddress = view.findViewById(R.id.hostelAddress);
        TextView hostelFullDescription = view.findViewById(R.id.hostelFullDescription);
        Button hostelApplyButton = view.findViewById(R.id.hostelApplyButton);

        if (MainActivity.selectedPage == 0 || MainActivity.selectedPage == 1) {
            // Объявляем доп переменные для конвертации глобальных значений в нужную локализацию
            startWeekDay = methods.convertWeekDay(MainActivity.startWeekDay, getContext());
            endWeekDay = methods.convertWeekDay(MainActivity.endWeekDay, getContext());
            startMonth = methods.convertMonth(MainActivity.startMonth, getContext());
            endMonth = methods.convertMonth(MainActivity.endMonth, getContext());

            // если выбраны какие-то даты, то устанавливаем их
            if (startWeekDay != null) {
                hostelStartDate.setText(String.format("%s, %s %s", startWeekDay, MainActivity.startDay, startMonth));
                hostelEndDate.setText(String.format("%s, %s %s", endWeekDay, MainActivity.endDay, endMonth));
            }
            // иначе навешиваем листенеры на текстовые поля и открываем календарь
            else {
                hostelStartDate.setText("Выберите дату въезда");
                hostelEndDate.setText("Выберите дату выезда");
                hostelStartDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppCompatActivity activity = (AppCompatActivity) getContext();

                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.fade_out);
                        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, MainActivity.fragmentCalendar);
                        fragmentTransaction.commit();
                    }
                });
                hostelEndDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppCompatActivity activity = (AppCompatActivity) getContext();

                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.fade_out);
                        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, MainActivity.fragmentCalendar);
                        fragmentTransaction.commit();
                    }
                });
            }

            if (MainActivity.children == 0) {
                MainActivity.visitors = getResources().getString(R.string.room) + ": " +
                        MainActivity.rooms + " • "
                        + getResources().getString(R.string.adult) +
                        ": " + MainActivity.adults + " • " + getResources().getString(R.string.noChildren);
            } else {
                MainActivity.visitors = getResources().getString(R.string.room) + ": " +
                        MainActivity.rooms + " • " + getResources().getString(R.string.adult) +
                        ": " + MainActivity.adults + " • " + getResources().getString(R.string.child) +
                        ": " + MainActivity.children;
            }

            hostelQuests.setText(MainActivity.visitors);

        }


        if (MainActivity.selectedPage == 0) {


            Glide.with(view.getContext().getApplicationContext()).load(MainActivity.searchHostelCard.getImage()).into(hostelImage);

            // устанавливаем текстовые значения поля
            hostelName.setText(MainActivity.searchHostelCard.getHostelName());
            hostelTitle.setText(MainActivity.searchHostelCard.getHostelName());
            hostelFullDescription.setText(MainActivity.searchHostelCard.getFullDescription());
            hostelRatting.setText((String.valueOf(MainActivity.searchHostelCard.getRating())));

            hostelAddress.setText((MainActivity.searchHostelCard.getCity() + ", " + MainActivity.searchHostelCard.getAddress()));

            // навешиваем слушатель кнопки забронировать
            hostelApplyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.isAuth && MainActivity.selectedDates != null) {
                        // для каждой выбранной даты проверяем, нет ли ее как ключа в словаре или если есть, то больше ли нуля в нем значение
                        // проверяем подходит ли все еще отель для бронирования (проверка на случай, если данные уже обновились)

                        for (int i = 1; i < MainActivity.selectedDates.size(); ++i) {
                            @SuppressLint("DefaultLocale")
                            String key = String.format("%d %d %d - %d %d %d",
                                    (MainActivity.selectedDates.get(i - 1).getYear() + 1900),
                                    (MainActivity.selectedDates.get(i - 1).getMonth() + 1),
                                    MainActivity.selectedDates.get(i - 1).getDate(),
                                    (MainActivity.selectedDates.get(i).getYear() + 1900),
                                    (MainActivity.selectedDates.get(i).getMonth() + 1),
                                    MainActivity.selectedDates.get(i).getDate());

                            if (MainActivity.searchHostelCard.getListOfBookingDates().containsKey(key) && MainActivity.searchHostelCard.getListOfBookingDates().get(key) < MainActivity.rooms) {
                                Toast.makeText(view.getContext(), String.format("Вероятно, на текущие даты - (%s) только что забронировал номер другой пользователь, приносим свои извинения", key), Toast.LENGTH_LONG).show();
                                hostelApplyButton.setEnabled(false);
                                hostelApplyButton.setBackgroundColor(Color.parseColor("#808080"));
                                return;
                            }
                        }


                        // тут уже обновляем значение локального словаря и пушаем его в конце в фб
                        for (int i = 1; i < MainActivity.selectedDates.size(); ++i) {
                            @SuppressLint("DefaultLocale")
                            String key = String.format("%d %d %d - %d %d %d",
                                    (MainActivity.selectedDates.get(i - 1).getYear() + 1900),
                                    (MainActivity.selectedDates.get(i - 1).getMonth() + 1),
                                    MainActivity.selectedDates.get(i - 1).getDate(),
                                    (MainActivity.selectedDates.get(i).getYear() + 1900),
                                    (MainActivity.selectedDates.get(i).getMonth() + 1),
                                    MainActivity.selectedDates.get(i).getDate());

                            // если такая дата уже есть, то (она точно больше нуля - проверка выше)
                            if (MainActivity.searchHostelCard.getListOfBookingDates().containsKey(key)) {
                                MainActivity.searchHostelCard.getListOfBookingDates().put(key, MainActivity.searchHostelCard.getListOfBookingDates().get(key) - MainActivity.rooms);
                            }
                            // если такой даты еще нет, то добавляем новый ключ и макс кол-во номеров - число забронированных номеров
                            else {
                                MainActivity.searchHostelCard.getListOfBookingDates().put(key, MainActivity.searchHostelCard.getAmountOfHostelRooms() - MainActivity.rooms);
                            }

                        }

                        // создаем объект бронирования для последующего добавления его в коллекцию бронирований
                        HostelCard bookingHostelCard = new HostelCard();
                        bookingHostelCard.setId(MainActivity.searchHostelCard.getId());
                        bookingHostelCard.setHostelName(MainActivity.searchHostelCard.getHostelName());
                        bookingHostelCard.setCity(MainActivity.searchHostelCard.getCity());
                        bookingHostelCard.setAddress(MainActivity.searchHostelCard.getAddress());
                        bookingHostelCard.setImage(MainActivity.searchHostelCard.getImage());
                        bookingHostelCard.setShortDescription(MainActivity.searchHostelCard.getShortDescription());
                        bookingHostelCard.setFullDescription(MainActivity.searchHostelCard.getFullDescription());
                        bookingHostelCard.setAmountOfHostelRooms(MainActivity.searchHostelCard.getAmountOfHostelRooms());
                        bookingHostelCard.setPrice(MainActivity.searchHostelCard.getPrice());
                        bookingHostelCard.setRating(MainActivity.searchHostelCard.getRating());

                        HashMap<String, Integer> hashMap = new HashMap<>();

                        hashMap.put("startDay", Integer.parseInt(MainActivity.startDay));
                        hashMap.put("startWeekDay", Integer.parseInt(MainActivity.startWeekDay));
                        hashMap.put("startMonth", Integer.parseInt(MainActivity.startMonth));
                        hashMap.put("endDay", Integer.parseInt(MainActivity.endDay));
                        hashMap.put("endWeekDay", Integer.parseInt(MainActivity.endWeekDay));
                        hashMap.put("endMonth", Integer.parseInt(MainActivity.endMonth));

                        bookingHostelCard.setListOfBookingDates2(hashMap);

                        bookingHostelCard.setRooms(MainActivity.rooms);
                        bookingHostelCard.setAdults(MainActivity.adults);
                        bookingHostelCard.setChildren(MainActivity.children);

                        MainActivity.bookingsHostels.add(bookingHostelCard);

                        // обновляем данные в базе данных
                        MainActivity.databaseBookingsReference.child(MainActivity.firebaseUser.getUid()).setValue(MainActivity.bookingsHostels);



                        Toast.makeText(view.getContext(), "Успешно забранировано, в ближайшее время с вами свяжется представитель отеля", Toast.LENGTH_LONG).show();
                        MainActivity.databaseReference.child(MainActivity.searchHostelCard.getId()).child("listOfBookingDates").setValue(MainActivity.searchHostelCard.getListOfBookingDates());

                    } else if (MainActivity.selectedDates == null) {
                        Toast.makeText(view.getContext(), "Сначала надо указать количество посетителей и даты", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(view.getContext(), "Сначала необходимо войти в свой аккаунт", Toast.LENGTH_SHORT).show();
                }
            });


        }
        else if (MainActivity.selectedPage == 1) {

            Glide.with(view.getContext().getApplicationContext()).load(MainActivity.savedHostelCard.getImage()).into(hostelImage);


            // устанавливаем текстовые значения поля
            hostelName.setText(MainActivity.savedHostelCard.getHostelName());
            hostelTitle.setText(MainActivity.savedHostelCard.getHostelName());
            hostelFullDescription.setText(MainActivity.savedHostelCard.getFullDescription());
            hostelRatting.setText((String.valueOf(MainActivity.savedHostelCard.getRating())));

            hostelAddress.setText((MainActivity.savedHostelCard.getCity() + ", " + MainActivity.savedHostelCard.getAddress()));

            // навешиваем слушатель кнопки забронировать
            hostelApplyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.isAuth && MainActivity.selectedDates != null) {
                        // для каждой выбранной даты проверяем, нет ли ее как ключа в словаре или если есть, то больше ли нуля в нем значение
                        // проверяем подходит ли все еще отель для бронирования (проверка на случай, если данные уже обновились)

                        for (int i = 1; i < MainActivity.selectedDates.size(); ++i) {
                            @SuppressLint("DefaultLocale")
                            String key = String.format("%d %d %d - %d %d %d",
                                    (MainActivity.selectedDates.get(i - 1).getYear() + 1900),
                                    (MainActivity.selectedDates.get(i - 1).getMonth() + 1),
                                    MainActivity.selectedDates.get(i - 1).getDate(),
                                    (MainActivity.selectedDates.get(i).getYear() + 1900),
                                    (MainActivity.selectedDates.get(i).getMonth() + 1),
                                    MainActivity.selectedDates.get(i).getDate());

                            if (MainActivity.savedHostelCard.getListOfBookingDates().containsKey(key) && MainActivity.savedHostelCard.getListOfBookingDates().get(key) < MainActivity.rooms) {
                                Toast.makeText(view.getContext(), String.format("Вероятно, на текущие даты - (%s) только что забронировал номер другой пользователь, приносим свои извинения", key), Toast.LENGTH_LONG).show();
                                hostelApplyButton.setEnabled(false);
                                hostelApplyButton.setBackgroundColor(Color.parseColor("#808080"));
                                return;
                            }
                        }


                        // тут уже обновляем значение локального словаря и пушаем его в конце в фб
                        for (int i = 1; i < MainActivity.selectedDates.size(); ++i) {
                            @SuppressLint("DefaultLocale")
                            String key = String.format("%d %d %d - %d %d %d",
                                    (MainActivity.selectedDates.get(i - 1).getYear() + 1900),
                                    (MainActivity.selectedDates.get(i - 1).getMonth() + 1),
                                    MainActivity.selectedDates.get(i - 1).getDate(),
                                    (MainActivity.selectedDates.get(i).getYear() + 1900),
                                    (MainActivity.selectedDates.get(i).getMonth() + 1),
                                    MainActivity.selectedDates.get(i).getDate());

                            // если такая дата уже есть, то (она точно больше нуля - проверка выше)
                            if (MainActivity.savedHostelCard.getListOfBookingDates().containsKey(key)) {
                                MainActivity.savedHostelCard.getListOfBookingDates().put(key, MainActivity.savedHostelCard.getListOfBookingDates().get(key) - MainActivity.rooms);
                            }
                            // если такой даты еще нет, то добавляем новый ключ и макс кол-во номеров - 1
                            else {
                                MainActivity.savedHostelCard.getListOfBookingDates().put(key, MainActivity.savedHostelCard.getAmountOfHostelRooms() - MainActivity.rooms);
                            }

                        }


                        // создаем объект бронирования для последующего добавления его в коллекцию бронирований
                        HostelCard bookingHostelCard = new HostelCard();
                        bookingHostelCard.setId(MainActivity.savedHostelCard.getId());
                        bookingHostelCard.setHostelName(MainActivity.savedHostelCard.getHostelName());
                        bookingHostelCard.setCity(MainActivity.savedHostelCard.getCity());
                        bookingHostelCard.setAddress(MainActivity.savedHostelCard.getAddress());
                        bookingHostelCard.setImage(MainActivity.savedHostelCard.getImage());
                        bookingHostelCard.setShortDescription(MainActivity.savedHostelCard.getShortDescription());
                        bookingHostelCard.setFullDescription(MainActivity.savedHostelCard.getFullDescription());
                        bookingHostelCard.setAmountOfHostelRooms(MainActivity.savedHostelCard.getAmountOfHostelRooms());
                        bookingHostelCard.setPrice(MainActivity.savedHostelCard.getPrice());
                        bookingHostelCard.setRating(MainActivity.savedHostelCard.getRating());

                        HashMap<String, Integer> hashMap = new HashMap<>();

                        hashMap.put("startDay", Integer.parseInt(MainActivity.startDay));
                        hashMap.put("startWeekDay", Integer.parseInt(MainActivity.startWeekDay));
                        hashMap.put("startMonth", Integer.parseInt(MainActivity.startMonth));
                        hashMap.put("endDay", Integer.parseInt(MainActivity.endDay));
                        hashMap.put("endWeekDay", Integer.parseInt(MainActivity.endWeekDay));
                        hashMap.put("endMonth", Integer.parseInt(MainActivity.endMonth));

                        bookingHostelCard.setListOfBookingDates2(hashMap);

                        bookingHostelCard.setRooms(MainActivity.rooms);
                        bookingHostelCard.setAdults(MainActivity.adults);
                        bookingHostelCard.setChildren(MainActivity.children);

                        MainActivity.bookingsHostels.add(bookingHostelCard);

                        // обновляем данные в базе данных
                        MainActivity.databaseBookingsReference.child(MainActivity.firebaseUser.getUid()).setValue(MainActivity.bookingsHostels);


                        Toast.makeText(view.getContext(), "Успешно забранировано, в ближайшее время с вами свяжется представитель отеля", Toast.LENGTH_LONG).show();
                        databaseReference.child(MainActivity.savedHostelCard.getId()).child("listOfBookingDates").setValue(MainActivity.savedHostelCard.getListOfBookingDates());

                    } else if (MainActivity.selectedDates == null) {
                        Toast.makeText(view.getContext(), "Сначала надо указать количество посетителей и даты", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(view.getContext(), "Сначала необходимо войти в свой аккаунт", Toast.LENGTH_SHORT).show();



                }
            });

        } else if (MainActivity.selectedPage == 2) {
            Glide.with(view.getContext().getApplicationContext()).load(MainActivity.bookingHostelCard.getImage()).into(hostelImage);

            // устанавливаем текстовые значения поля
            hostelName.setText(MainActivity.bookingHostelCard.getHostelName());
            hostelTitle.setText(MainActivity.bookingHostelCard.getHostelName());
            hostelFullDescription.setText(MainActivity.bookingHostelCard.getFullDescription());
            hostelRatting.setText((String.valueOf(MainActivity.bookingHostelCard.getRating())));

            // Объявляем доп переменные для конвертации глобальных значений в нужную локализацию
            startWeekDay = methods.convertWeekDay(MainActivity.bookingHostelCard.getListOfBookingDates2().get("startWeekDay") + "", getContext());
            endWeekDay = methods.convertWeekDay(MainActivity.bookingHostelCard.getListOfBookingDates2().get("endWeekDay") + "", getContext());
            startMonth = methods.convertMonth(MainActivity.bookingHostelCard.getListOfBookingDates2().get("startMonth") + "", getContext());
            endMonth = methods.convertMonth(MainActivity.bookingHostelCard.getListOfBookingDates2().get("endMonth") + "", getContext());
            startDay = MainActivity.bookingHostelCard.getListOfBookingDates2().get("startDay") + "";
            endDay = MainActivity.bookingHostelCard.getListOfBookingDates2().get("endDay") + "";

            // устанавливаем даты въезда/выезда, которые указывали при бронировании
            hostelStartDate.setText(String.format("%s, %s %s", startWeekDay, startDay, startMonth));
            hostelEndDate.setText(String.format("%s, %s %s", endWeekDay, endDay, endMonth));

            // устанавливаем адрес
            hostelAddress.setText((MainActivity.bookingHostelCard.getCity() + ", " + MainActivity.bookingHostelCard.getAddress()));

            if (MainActivity.bookingHostelCard.getChildren() == 0) {
                MainActivity.visitors = getResources().getString(R.string.room) + ": " +
                        MainActivity.bookingHostelCard.getRooms() + " • "
                        + getResources().getString(R.string.adult) +
                        ": " + MainActivity.bookingHostelCard.getAdults() + " • " + getResources().getString(R.string.noChildren);
            } else {
                MainActivity.visitors = getResources().getString(R.string.room) + ": " +
                        MainActivity.bookingHostelCard.getRooms() + " • " + getResources().getString(R.string.adult) +
                        ": " + MainActivity.bookingHostelCard.getAdults() + " • " + getResources().getString(R.string.child) +
                        ": " + MainActivity.bookingHostelCard.getChildren();
            }
            hostelQuests.setText(MainActivity.visitors);

            hostelApplyButton.setText(getResources().getString(R.string.cancelBooking));

            // навешиваем слушатель кнопки забронировать
            hostelApplyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.isAuth) {
                        // тут уже обновляем значение локального словаря и пушаем его в конце в бд
                        HashMap<String, Integer> hashMap = new HashMap<>();
                        for ( String key : MainActivity.bookingHostelCard.getListOfBookingDates().keySet() ) {
                            hashMap.put(key, MainActivity.bookingHostelCard.getListOfBookingDates().get(key) + MainActivity.bookingHostelCard.getRooms());
                        }
                        MainActivity.bookingHostelCard.setListOfBookingDates(hashMap);
//                        System.out.println(hashMap.toString());
//                        for (int i = 1; i < MainActivity.bookingHostelCard.getListOfBookingDates().size(); ++i) {
//                            MainActivity.bookingHostelCard
//                            @SuppressLint("DefaultLocale")
//                            String key = String.format("%d %d %d - %d %d %d",
//                                    (MainActivity.bookingHostelCard.getListOfBookingDates().get(i - 1).getYear() + 1900),
//                                    (MainActivity.bookingHostelCard.getListOfBookingDates().get(i - 1).getMonth() + 1),
//                                    MainActivity.bookingHostelCard.getListOfBookingDates().get(i - 1).getDate(),
//                                    (MainActivity.bookingHostelCard.getListOfBookingDates().get(i).getYear() + 1900),
//                                    (MainActivity.bookingHostelCard.getListOfBookingDates().get(i).getMonth() + 1),
//                                    MainActivity.bookingHostelCard.getListOfBookingDates().get(i).getDate());

//                            MainActivity.bookingHostelCard.getListOfBookingDates().put(key, MainActivity.searchHostelCard.getListOfBookingDates().get(key) + MainActivity.bookingHostelCard.getRooms());

//                        }
                        databaseReference.child(MainActivity.bookingHostelCard.getId()).child("listOfBookingDates").setValue(MainActivity.bookingHostelCard.getListOfBookingDates());
                        Toast.makeText(view.getContext(), "Успешно отменена бронь", Toast.LENGTH_LONG).show();

                    }
                }
            });

        }

        return view;

    }

   

}