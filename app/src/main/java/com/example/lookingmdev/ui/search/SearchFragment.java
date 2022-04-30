package com.example.lookingmdev.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.databinding.FragmentSearchBinding;
import com.example.lookingmdev.databinding.FragmentSearchBinding;
import com.example.lookingmdev.ui.methods.Methods;

import com.google.android.material.bottomsheet.BottomSheetBehavior;


import java.text.BreakIterator;

public class SearchFragment extends Fragment {


    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;
    TextView dateText;

    @SuppressLint("DefaultLocale")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        searchViewModel =
//                new ViewModelProvider(this).get(SearchViewModel.class);
        Methods methods = new Methods();

        // инициализация буферных переменных, которые получают строковые данные от методов конвертации
        // дат в строки с текущей локализацией
        String startWeekDay = methods.convertWeekDay(MainActivity.startWeekDay, getContext());
        String endWeekDay = methods.convertWeekDay(MainActivity.endWeekDay, getContext());
        String startMonth = methods.convertMonth(MainActivity.startMonth, getContext());
        String endMonth = methods.convertMonth(MainActivity.endMonth, getContext());

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dateText = root.findViewById(R.id.dateText);

        // выстановление соответствующего текста в поле дат
        if (MainActivity.startDay == null)
            dateText.setText(getResources().getString(R.string.date));
        else{
            if (MainActivity.endDay.equals(""))
                dateText.setText((startWeekDay + ", " + MainActivity.startDay + " " + startMonth));
            else
                dateText.setText((String.format("%s, %s %s—%s, %s %s (%s: %d)",
                        startWeekDay,
                        MainActivity.startDay,
                        startMonth,
                        endWeekDay,
                        MainActivity.endDay,
                        endMonth,
                        getResources().getString(R.string.nights),
                        MainActivity.selectedDates.size() - 1)));
        }

        // инициализируем поле, в котором будет лежать XML вылезающего снизу меню
        LinearLayout llBottomSheet = root.findViewById(R.id.bottom_sheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // скрываем изначально это меню
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        // привязываем к текстовому полю с посетителями листенер
        TextView numberOfVisitors = root.findViewById(R.id.numberOfVisitors);
        numberOfVisitors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        // инициализируем кнопки и создаем листенеры для них
        Button plusRoomButton = root.findViewById(R.id.plusRoomButton);
        TextView numberOfRoomText = root.findViewById(R.id.numberOfRoomText);
        Button minusRoomButton = root.findViewById(R.id.minusRoomButton);

        Button plusHumanButton = root.findViewById(R.id.plusHumanButton);
        TextView numberOfHumanText = root.findViewById(R.id.numberOfHumanText);
        Button minusHumanButton = root.findViewById(R.id.minusHumanButton);

        Button plusKidsButton = root.findViewById(R.id.plusKidsButton);
        TextView numberOfKidsButton = root.findViewById(R.id.numberOfKidsButton);
        Button minusKidsButton = root.findViewById(R.id.minusKidsButton);

        plusRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(numberOfRoomText.getText().toString());
                ++number;
                numberOfRoomText.setText("" + number);
                if (number >= 2)
                    minusRoomButton.setBackgroundColor(getResources().getColor(R.color.buttonBlue));
            }
        });
        minusRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(numberOfRoomText.getText().toString());
                if (number > 1) {
                --number;
                numberOfRoomText.setText("" + number);
                if (number == 1)
                    minusRoomButton.setBackgroundColor(getResources().getColor(R.color.greyLine));

                }
            }
        });

        plusHumanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(numberOfHumanText.getText().toString());
                ++number;
                numberOfHumanText.setText("" + number);
                if (number >= 2)
                    minusHumanButton.setBackgroundColor(getResources().getColor(R.color.buttonBlue));
            }
        });
        minusHumanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(numberOfHumanText.getText().toString());
                if (number > 1) {
                    --number;
                    numberOfHumanText.setText("" + number);
                    if (number == 1)
                        minusHumanButton.setBackgroundColor(getResources().getColor(R.color.greyLine));

                }
            }
        });
        plusKidsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(numberOfKidsButton.getText().toString());
                ++number;
                numberOfKidsButton.setText("" + number);
                if (number >= 1)
                    minusKidsButton.setBackgroundColor(getResources().getColor(R.color.buttonBlue));
            }
        });
        minusKidsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(numberOfKidsButton.getText().toString());
                if (number > 0) {
                    --number;
                    numberOfKidsButton.setText("" + number);
                    if (number == 0)
                        minusKidsButton.setBackgroundColor(getResources().getColor(R.color.greyLine));

                }
            }
        });

        return root;

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}