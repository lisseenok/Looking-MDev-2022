package com.example.lookingmdev.ui.calendar;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.ui.methods.Methods;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentCalendar extends Fragment {

    private FragmentCalendarViewModel mViewModel;

    TextView calendarText;

    public static FragmentCalendar newInstance() {
        return new FragmentCalendar();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Methods methods = new Methods();
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // инициализация календаря, я особо тут не вдавался в подробности
        Calendar pastYear = Calendar.getInstance();
        pastYear.add(Calendar.YEAR, -1);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.DATE,30 * 8);  // знаю, что тут задается количество месяцев наперед


        // нашли календарь в фрагменте
        CalendarPickerView calendar = view.findViewById(R.id.calendarView);

        // проинициализировали календарь
        calendar.init(pastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(new Date());
        calendar.setTypeface(Typeface.SANS_SERIF);

        // при нажатии на календарь
        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                // инициализируем массив, в который кладем массив со всеми выбранными датами
                MainActivity.selectedDates = calendar.getSelectedDates();

                // кладем в начальный день цифровое значение выбранного первого дня на календаре
                MainActivity.startWeekDay = "" + MainActivity.selectedDates.get(0).getDay();
                if (MainActivity.selectedDates.size() > 1)
                    // кладем в последний день цифровое значение выбранного последнего дня на календаре
                    MainActivity.endWeekDay = "" + MainActivity.selectedDates.get(MainActivity.selectedDates.size() - 1).getDay();
                else MainActivity.endWeekDay = "";
                // конвертируем значения дня недели
                String startWeekDay = methods.convertWeekDay(MainActivity.startWeekDay, getContext());
                String endWeekDay = methods.convertWeekDay(MainActivity.endWeekDay, getContext());

                // то же самое делаем с переменной месяца
                MainActivity.startMonth = "" + MainActivity.selectedDates.get(0).getMonth();
                if (MainActivity.selectedDates.size() > 1)
                    MainActivity.endMonth = "" + MainActivity.selectedDates.get(MainActivity.selectedDates.size() - 1).getMonth();
                else MainActivity.endMonth = "";

                String startMonth = methods.convertMonth(MainActivity.startMonth, getContext());
                String endMonth = methods.convertMonth(MainActivity.endMonth, getContext());

                // прописываем ту же логику уже для числа дня
                MainActivity.startDay = "" + ((MainActivity.selectedDates.get(0).getDate()));
                if (MainActivity.selectedDates.size() > 1)
                    MainActivity.endDay = "" + (MainActivity.selectedDates.get(MainActivity.selectedDates.size() - 1).getDate());
                else MainActivity.endDay = "";

                calendarText = view.findViewById(R.id.calendarText);
//                String text = String.format("Найдено: %d вариант", hostelList.size());
                if (MainActivity.selectedDates.size() == 1){
                    MainActivity.date = (startWeekDay + ", " + MainActivity.startDay + " " + startMonth);
                    calendarText.setText(MainActivity.date);
                }
                else {
                    MainActivity.date = (startWeekDay + ", " + MainActivity.startDay +
                            " " + startMonth + "—" + endWeekDay + ", " +
                            MainActivity.endDay + " " + endMonth + String.format(" (%s: %d)",
                            getResources().getString(R.string.nights), MainActivity.selectedDates.size() - 1));
                    calendarText.setText(MainActivity.date);

                }




            }

            @Override
            public void onDateUnselected(Date date) {
            }

        });
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentCalendarViewModel.class);
        // TODO: Use the ViewModel
    }

}