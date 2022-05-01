package com.example.lookingmdev.ui.destination;

import static com.example.lookingmdev.MainActivity.hideSoftKeyboard;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;

public class DestinationFragment extends Fragment {

    private DestinationViewModel mViewModel;

    public static DestinationFragment newInstance() {
        return new DestinationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination, container, false);


        // в эту переменную мы кладем значение текущей темы для отрисовки текста в фильтрах соответствующего цвета
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;


        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView textView = view.findViewById(R.id.autocomplete_city);
        // Get the string array
        String[] cities = getResources().getStringArray(R.array.countries_array);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, cities);
        textView.setAdapter(adapter);
        System.out.println(123123);
        textView.setText("");
        // при нажатии на подсказку
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                MainActivity.city = textView.getText().toString();
                System.out.println(MainActivity.city);
                hideSoftKeyboard(getActivity());

                switch (currentNightMode) {
                    case Configuration.UI_MODE_NIGHT_NO:
                        // ночная тема не активна, используется светлая тема
                        textView.setTextColor(getResources().getColor(R.color.enteredTextColorLight));
                        break;
                    case Configuration.UI_MODE_NIGHT_YES:
                        // ночная тема активна, и она используется
                        textView.setTextColor(getResources().getColor(R.color.enteredTextColorDark));
                        break;
                }
            }
        });





        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DestinationViewModel.class);
        // TODO: Use the ViewModel
    }

}