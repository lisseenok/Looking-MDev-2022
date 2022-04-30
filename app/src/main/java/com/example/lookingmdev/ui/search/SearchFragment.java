package com.example.lookingmdev.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.databinding.FragmentSearchBinding;
import com.example.lookingmdev.ui.methods.Methods;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class SearchFragment extends Fragment {


    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;

    Button plusRoomButton;
    TextView numberOfRoomText;
    Button minusRoomButton;
    Button plusHumanButton;
    TextView numberOfHumanText;
    Button minusHumanButton;
    Button plusKidsButton;
    TextView numberOfKidsText;
    Button minusKidsButton;

    TextView dateText;
    TextView numberOfVisitors;

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

        // инициализируем текстовое поле в месте для выбранных дат
        dateText = root.findViewById(R.id.dateText);

        // инициализируем текстовое поле в месте для выбранных комнат и людей
        numberOfVisitors = root.findViewById(R.id.numberOfVisitors);


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

        // инициализируем кнопки и создаем листенеры для них
        plusRoomButton = root.findViewById(R.id.plusRoomButton);
        numberOfRoomText = root.findViewById(R.id.numberOfRoomText);
        minusRoomButton = root.findViewById(R.id.minusRoomButton);

        plusHumanButton = root.findViewById(R.id.plusHumanButton);
        numberOfHumanText = root.findViewById(R.id.numberOfHumanText);
        minusHumanButton = root.findViewById(R.id.minusHumanButton);

        plusKidsButton = root.findViewById(R.id.plusKidsButton);
        numberOfKidsText = root.findViewById(R.id.numberOfKids);
        minusKidsButton = root.findViewById(R.id.minusKidsButton);

        // устанавливаем соответствующий текст в поле посетителей
        setVisitorsText();

        // привязываем к текстовому полю с посетителями листенер
        TextView numberOfVisitors = root.findViewById(R.id.numberOfVisitors);

        numberOfVisitors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MainActivity.visitors != null) {
                    numberOfRoomText.setText(MainActivity.rooms + "");
                    numberOfHumanText.setText(MainActivity.adults + "");
                    numberOfKidsText.setText(MainActivity.children + "");
                }



                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });


        // объявляем листенеры для кнопок менюшки снизу
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
                int number = Integer.parseInt(numberOfKidsText.getText().toString());
                ++number;
                numberOfKidsText.setText("" + number);
                if (number >= 1)
                    minusKidsButton.setBackgroundColor(getResources().getColor(R.color.buttonBlue));
            }
        });
        minusKidsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(numberOfKidsText.getText().toString());
                if (number > 0) {
                    --number;
                    numberOfKidsText.setText("" + number);
                    if (number == 0)
                        minusKidsButton.setBackgroundColor(getResources().getColor(R.color.greyLine));

                }
            }
        });

        // инициализируем кнопку на меню фильтров людей и комнат
        Button questsApplyButton = root.findViewById(R.id.questsApplyButton);

        // навешиваем листенер
        questsApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                // обновляем данные о посетителях
                MainActivity.rooms = Integer.parseInt(numberOfRoomText.getText().toString());
                MainActivity.adults = Integer.parseInt(numberOfHumanText.getText().toString());
                MainActivity.children = Integer.parseInt(numberOfKidsText.getText().toString());

                setVisitorsText();

            }
        });

        return root;

    }

    public void setVisitorsText(){
        if (MainActivity.rooms != 0) {
            if (MainActivity.children == 0) {
                MainActivity.visitors = getResources().getString(R.string.room) + ": " +

                        numberOfRoomText.getText().toString() + " • "
                        + getResources().getString(R.string.adult) +
                        ": " + numberOfHumanText.getText().toString() + " • " + getResources().getString(R.string.noChildren);


            } else {
                MainActivity.visitors = getResources().getString(R.string.room) + ": " +
                        numberOfRoomText.getText().toString() + " • " + getResources().getString(R.string.adult) +
                        ": " + numberOfHumanText.getText().toString() + " • " + getResources().getString(R.string.child) +
                        ": " + numberOfKidsText.getText().toString();
            }
            numberOfVisitors.setText(MainActivity.visitors);
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}