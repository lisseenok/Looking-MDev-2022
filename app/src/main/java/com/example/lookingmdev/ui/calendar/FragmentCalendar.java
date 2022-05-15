package com.example.lookingmdev.ui.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.ui.hostelPage.HostelPageFragment;
import com.example.lookingmdev.ui.methods.Methods;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.savvi.rangedatepicker.DefaultDayViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class FragmentCalendar extends Fragment {

    private FragmentCalendarViewModel mViewModel;

    TextView calendarText;
    String startWeekDay;
    String endWeekDay;
    String startMonth;
    String endMonth;
    Button calendarButton;


    // объявляем методы
    Methods methods = new Methods();

    public static FragmentCalendar newInstance() {
        return new FragmentCalendar();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);


        // инициализация календаря, я особо тут не вдавался в подробности
        Calendar pastYear = Calendar.getInstance();
        pastYear.add(Calendar.YEAR, 0);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 8);  // знаю, что тут задается количество месяцев наперед


        // нашли календарь в фрагменте
        CalendarPickerView calendar = view.findViewById(R.id.calendarView);

//        Collection<Date> listArray = new ArrayList<>();
//        listArray.add(new Date());

        // проинициализировали календарь
        // если в глобальной статическом массиве есть уже выбранные даты, то создаем коллекцию
        // и добавляем в нее первый и последний элементы массива дат
        if (MainActivity.endDay == null) {
        calendar.init(pastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.RANGE);
//                .withHighlightedDates(listArray);
        calendar.setTypeface(Typeface.SANS_SERIF);
        } else {
            Collection<Date> collection = new ArrayList<>();
            collection.add(MainActivity.selectedDates.get(0));
            collection.add(MainActivity.selectedDates.get(MainActivity.selectedDates.size() - 1));
            calendar.init(pastYear.getTime(), nextYear.getTime()) //
                    .inMode(CalendarPickerView.SelectionMode.RANGE)
                    .withSelectedDates(collection);
//                    .withHighlightedDates(listArray);
            calendar.setTypeface(Typeface.SANS_SERIF);
        }

        calendarText = view.findViewById(R.id.calendarText);
        calendar.setCustomDayView(new DefaultDayViewAdapter());

        // обновляем текст под календарем
        updateCalendarText();

        // при нажатии на календарь
        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                // инициализируем массив, в который кладем массив со всеми выбранными датами
                MainActivity.selectedDates = calendar.getSelectedDates();

                // обновляем данные для глобальных переменных (начальный месяц, конечный месяц, начальное число итд)
                updateGlobalValues();

                // обновляем текст под календарем
                updateCalendarText();
            }
            @Override
            public void onDateUnselected(Date date) {
            }
        });

        calendarButton = view.findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) getContext();

                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.slide_out_down);

                if (MainActivity.selectedPage == 0) {
                    if (MainActivity.searchState == -1)
                        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, MainActivity.searchFragment);
                    else if (MainActivity.searchState == 2)
                        fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, MainActivity.hostelPageFragment);
                    fragmentTransaction.commit();
                } else if (MainActivity.selectedPage == 1) {
                    fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, MainActivity.hostelPageFragment);
                    fragmentTransaction.commit();
                }
            }
        });

        return view;
    }

    // обновляем текст под календарем
    public void updateCalendarText(){

        // если переменные вообще заполнены (не первый запуск)
        if (MainActivity.endDay != null) {

            if (MainActivity.selectedDates.size() == 1){
                calendarText.setText(getContext().getResources().getString(R.string.date));
            }
            else {
                MainActivity.date = (startWeekDay + ", " + MainActivity.startDay +
                        " " + startMonth + "—" + endWeekDay + ", " +
                        MainActivity.endDay + " " + endMonth + String.format(" (%s: %d)",
                        getResources().getString(R.string.nights), MainActivity.selectedDates.size() - 1));
                calendarText.setText(MainActivity.date);
            }

        }
    }

    // обновляем данные для глобальных переменных (начальный месяц, конечный месяц, начальное число итд)
    private void updateGlobalValues() {
        // кладем в начальный день цифровое значение выбранного первого дня на календаре
        MainActivity.startWeekDay = "" + MainActivity.selectedDates.get(0).getDay();
        if (MainActivity.selectedDates.size() > 1)
            // кладем в последний день цифровое значение выбранного последнего дня на календаре
            MainActivity.endWeekDay = "" + MainActivity.selectedDates.get(MainActivity.selectedDates.size() - 1).getDay();
        else MainActivity.endWeekDay = "";
        // конвертируем значения дня недели
        startWeekDay = methods.convertWeekDay(MainActivity.startWeekDay, getContext());
        endWeekDay = methods.convertWeekDay(MainActivity.endWeekDay, getContext());

        // то же самое делаем с переменной месяца
        MainActivity.startMonth = "" + MainActivity.selectedDates.get(0).getMonth();
        if (MainActivity.selectedDates.size() > 1)
            MainActivity.endMonth = "" + MainActivity.selectedDates.get(MainActivity.selectedDates.size() - 1).getMonth();
        else MainActivity.endMonth = "";

        startMonth = methods.convertMonth(MainActivity.startMonth, getContext());
        endMonth = methods.convertMonth(MainActivity.endMonth, getContext());

        // прописываем ту же логику уже для числа дня
        MainActivity.startDay = "" + ((MainActivity.selectedDates.get(0).getDate()));
        if (MainActivity.selectedDates.size() > 1)
            MainActivity.endDay = "" + (MainActivity.selectedDates.get(MainActivity.selectedDates.size() - 1).getDate());
        else MainActivity.endDay = "";
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentCalendarViewModel.class);
        // TODO: Use the ViewModel
    }

}