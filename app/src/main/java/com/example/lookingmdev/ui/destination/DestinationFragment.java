package com.example.lookingmdev.ui.destination;

//import static com.example.lookingmdev.MainActivity.hideSoftKeyboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
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
import com.example.lookingmdev.ui.methods.Methods;

public class DestinationFragment extends Fragment {

    private DestinationViewModel mViewModel;

    public static DestinationFragment newInstance() {
        return new DestinationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_destination, container, false);

        Methods methods = new Methods();
        // в эту переменную мы кладем значение текущей темы для отрисовки текста в фильтрах соответствующего цвета
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;


        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView editText = view.findViewById(R.id.autocomplete_city);

        // Get the string array
        String[] cities = getResources().getStringArray(R.array.countries_array);

        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, cities);

        editText.setAdapter(adapter);
        editText.requestFocus();

//        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        System.out.println("pizda");
//        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

        methods.OpenKeyBoard(getContext());
        // при нажатии на подсказку
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                MainActivity.city = editText.getText().toString();
                methods.CloseKeyBoard(getContext());
//                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
//                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
                editText.clearFocus();
                switch (currentNightMode) {
                    case Configuration.UI_MODE_NIGHT_NO:
                        // ночная тема не активна, используется светлая тема
                        editText.setTextColor(getResources().getColor(R.color.enteredTextColorLight));
                        break;
                    case Configuration.UI_MODE_NIGHT_YES:
                        // ночная тема активна, и она используется
                        editText.setTextColor(getResources().getColor(R.color.enteredTextColorDark));
                        break;
                }
                AppCompatActivity activity = (AppCompatActivity) getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.slide_out_down);
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, MainActivity.searchFragment);
                fragmentTransaction.commit();
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