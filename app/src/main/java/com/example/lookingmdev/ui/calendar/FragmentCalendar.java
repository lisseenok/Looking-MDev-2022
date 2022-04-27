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
                List<Date> selectedDates = calendar.getSelectedDates();

                // кладем в начальный день цифровое значение выбранного первого дня на календаре
                MainActivity.startWeekDay = "" + selectedDates.get(0).getDay();
                if (selectedDates.size() > 1)
                    // кладем в последний день цифровое значение выбранного последнего дня на календаре
                    MainActivity.endWeekDay = "" + selectedDates.get(selectedDates.size() - 1).getDay();
                else MainActivity.endWeekDay = "";
                // конвертируем значения дня недели
                MainActivity.startWeekDay = convertWeekDay(MainActivity.startWeekDay);
                MainActivity.endWeekDay = convertWeekDay(MainActivity.endWeekDay);

                // то же самое делаем с переменной месяца
                MainActivity.startMonth = "" + selectedDates.get(0).getMonth();
                if (selectedDates.size() > 1)
                    MainActivity.endMonth = "" + selectedDates.get(selectedDates.size() - 1).getMonth();
                else MainActivity.endMonth = "";

                MainActivity.startMonth = convertMonth(MainActivity.startMonth);
                MainActivity.endMonth = convertMonth(MainActivity.endMonth);

                // прописываем ту же логику уже для числа дня
                MainActivity.startDay = "" + ((selectedDates.get(0).getDate()));
                if (selectedDates.size() > 1)
                    MainActivity.endDay = "" + (selectedDates.get(selectedDates.size() - 1).getDate());
                else MainActivity.endDay = "";

                calendarText = view.findViewById(R.id.calendarText);
//                String text = String.format("Найдено: %d вариант", hostelList.size());
                if (selectedDates.size() == 1){
                    MainActivity.date = (MainActivity.startWeekDay + ", " + MainActivity.startDay + " " + MainActivity.startMonth);
                    calendarText.setText(MainActivity.date);
                }
                else {
                    MainActivity.date = (MainActivity.startWeekDay + ", " + MainActivity.startDay +
                            " " + MainActivity.startMonth + "—" + MainActivity.endWeekDay + ", " +
                            MainActivity.endDay + " " + MainActivity.endMonth + String.format(" (%s: %d)",
                            getResources().getString(R.string.nights), selectedDates.size() - 1));
                    calendarText.setText(MainActivity.date);

                }




            }

            @Override
            public void onDateUnselected(Date date) {
            }

        });
        return view;
    }

    public String convertWeekDay(String weekDay) {
        // получение текущего системного языка
//        String local = Locale.getDefault().getLanguage();
//        System.out.println(local);
        switch (weekDay) {
            case "0":
                weekDay = getResources().getString(R.string.sunday);
                break;
            case "1":
                weekDay = getResources().getString(R.string.monday);
                break;
            case "2":
                weekDay = getResources().getString(R.string.tuesday);
                break;
            case "3":
                weekDay = getResources().getString(R.string.wednesday);
                break;
            case "4":
                weekDay = getResources().getString(R.string.thursday);
                break;
            case "5":
                weekDay = getResources().getString(R.string.friday);
                break;
            case "6":
                weekDay = getResources().getString(R.string.saturday);
                break;
        }
        return weekDay;
    }

    public String convertMonth(String month) {
        switch (month) {
            case "0":
                month = getResources().getString(R.string.january);
                break;
            case "1":
                month = getResources().getString(R.string.february);
                break;
            case "2":
                month = getResources().getString(R.string.march);
                break;
            case "3":
                month = getResources().getString(R.string.april);
                break;
            case "4":
                month = getResources().getString(R.string.may);
                break;
            case "5":
                month = getResources().getString(R.string.june);
                break;
            case "6":
                month = getResources().getString(R.string.july);
                break;
            case "7":
                month = getResources().getString(R.string.august);
                break;
            case "8":
                month = getResources().getString(R.string.september);
                break;
            case "9":
                month = getResources().getString(R.string.october);
                break;
            case "10":
                month = getResources().getString(R.string.november);
                break;
            case "11":
                month = getResources().getString(R.string.december);
                break;
        }
        return month;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentCalendarViewModel.class);
        // TODO: Use the ViewModel
    }

}