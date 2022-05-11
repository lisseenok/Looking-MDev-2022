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
        if (MainActivity.selectedPage == 0) {
            Methods methods = new Methods();

            // Объявляем доп переменные для конвертации глобальных значений в нужную локализацию
            String startWeekDay = methods.convertWeekDay(MainActivity.startWeekDay, getContext());
            String endWeekDay = methods.convertWeekDay(MainActivity.endWeekDay, getContext());
            String startMonth = methods.convertMonth(MainActivity.startMonth, getContext());
            String endMonth = methods.convertMonth(MainActivity.endMonth, getContext());

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
            System.out.println(MainActivity.searchHostelCard);

//            int image = getContext().getResources().getIdentifier(MainActivity.searchHostelCard.getImage(), "drawable", getContext().getPackageName());
//            hostelImage.setImageResource(image);

            Glide.with(view.getContext().getApplicationContext()).load(MainActivity.searchHostelCard.getImage()).into(hostelImage);

            // устанавливаем текстовые значения поля
            hostelName.setText(MainActivity.searchHostelCard.getHostelName());
            hostelTitle.setText(MainActivity.searchHostelCard.getHostelName());
            hostelFullDescription.setText(MainActivity.searchHostelCard.getFullDescription());
            hostelRatting.setText((String.valueOf(MainActivity.searchHostelCard.getRating())));

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
            hostelAddress.setText((MainActivity.searchHostelCard.getCity() + ", " + MainActivity.searchHostelCard.getAddress()));


            //databaseReference.child("-N1FTh65-DwsrqJk8ETb").child("address").setValue("23");


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

                            if (MainActivity.searchHostelCard.getListOfBookingDates().containsKey(key) && MainActivity.searchHostelCard.getListOfBookingDates().get(key) == 0) {
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
                                MainActivity.searchHostelCard.getListOfBookingDates().put(key, MainActivity.searchHostelCard.getListOfBookingDates().get(key) - 1);
                            }
                            // если такой даты еще нет, то добавляем новый ключ и макс кол-во номеров - 1
                            else {
                                MainActivity.searchHostelCard.getListOfBookingDates().put(key, MainActivity.searchHostelCard.getAmountOfHostelRooms() - 1);
                            }
                            System.out.println(MainActivity.searchHostelCard.getListOfBookingDates());

                        }
                        Toast.makeText(view.getContext(), "Успешно забранировано, в ближайшее время с вами свяжется представитель отеля", Toast.LENGTH_LONG).show();
                        databaseReference.child(MainActivity.searchHostelCard.getId()).child("listOfBookingDates").setValue(MainActivity.searchHostelCard.getListOfBookingDates());

                    } else if (MainActivity.selectedDates == null) {
                        Toast.makeText(view.getContext(), "Сначала надо указать количество посетителей и даты", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(view.getContext(), "Сначала необходимо войти в свой аккаунт", Toast.LENGTH_SHORT).show();


                    MainActivity.databaseReference.child(MainActivity.searchHostelCard.getId()).child("listOfBookingDates").setValue(MainActivity.searchHostelCard.getListOfBookingDates());

                }
            });


        }
        else if (MainActivity.selectedPage == 1) {


            Methods methods = new Methods();

            // Объявляем доп переменные для конвертации глобальных значений в нужную локализацию
            String startWeekDay = methods.convertWeekDay(MainActivity.startWeekDay, getContext());
            String endWeekDay = methods.convertWeekDay(MainActivity.endWeekDay, getContext());
            String startMonth = methods.convertMonth(MainActivity.startMonth, getContext());
            String endMonth = methods.convertMonth(MainActivity.endMonth, getContext());

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
            System.out.println(MainActivity.savedHostelCard);
//            int image = getContext().getResources().getIdentifier(MainActivity.savedHostelCard.getImage(), "drawable", getContext().getPackageName());
//            hostelImage.setImageResource(image);

            Glide.with(view.getContext().getApplicationContext()).load(MainActivity.savedHostelCard.getImage()).into(hostelImage);


            // устанавливаем текстовые значения поля
            hostelName.setText(MainActivity.savedHostelCard.getHostelName());
            hostelTitle.setText(MainActivity.savedHostelCard.getHostelName());
            hostelFullDescription.setText(MainActivity.savedHostelCard.getFullDescription());
            hostelRatting.setText((String.valueOf(MainActivity.savedHostelCard.getRating())));

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
            hostelAddress.setText((MainActivity.savedHostelCard.getCity() + ", " + MainActivity.savedHostelCard.getAddress()));


            //databaseReference.child("-N1FTh65-DwsrqJk8ETb").child("address").setValue("23");


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

                            if (MainActivity.savedHostelCard.getListOfBookingDates().containsKey(key) && MainActivity.savedHostelCard.getListOfBookingDates().get(key) == 0) {
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
                                MainActivity.savedHostelCard.getListOfBookingDates().put(key, MainActivity.savedHostelCard.getListOfBookingDates().get(key) - 1);
                            }
                            // если такой даты еще нет, то добавляем новый ключ и макс кол-во номеров - 1
                            else {
                                MainActivity.savedHostelCard.getListOfBookingDates().put(key, MainActivity.savedHostelCard.getAmountOfHostelRooms() - 1);
                            }
                            System.out.println(MainActivity.savedHostelCard.getListOfBookingDates());

                        }
                        Toast.makeText(view.getContext(), "Успешно забранировано, в ближайшее время с вами свяжется представитель отеля", Toast.LENGTH_LONG).show();
                        databaseReference.child(MainActivity.savedHostelCard.getId()).child("listOfBookingDates").setValue(MainActivity.savedHostelCard.getListOfBookingDates());

                    } else if (MainActivity.selectedDates == null) {
                        Toast.makeText(view.getContext(), "Сначала надо указать количество посетителей и даты", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(view.getContext(), "Сначала необходимо войти в свой аккаунт", Toast.LENGTH_SHORT).show();


                    MainActivity.databaseReference.child(MainActivity.savedHostelCard.getId()).child("listOfBookingDates").setValue(MainActivity.savedHostelCard.getListOfBookingDates());

                }
            });


        }

        return view;

    }

   

}