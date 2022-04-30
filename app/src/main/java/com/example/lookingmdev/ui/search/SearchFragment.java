package com.example.lookingmdev.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        String startWeekDay = methods.convertWeekDay(MainActivity.startWeekDay, getContext());
        String endWeekDay = methods.convertWeekDay(MainActivity.endWeekDay, getContext());
        String startMonth = methods.convertMonth(MainActivity.startMonth, getContext());
        String endMonth = methods.convertMonth(MainActivity.endMonth, getContext());

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dateText = root.findViewById(R.id.dateText);

        if (MainActivity.date == null)
            dateText.setText(getResources().getString(R.string.date));
        else
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


        LinearLayout llBottomSheet = root.findViewById(R.id.bottom_sheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);


        TextView numberOfVisitors = root.findViewById(R.id.numberOfVisitors);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        numberOfVisitors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

//        final TextView textView = binding.textSearch;
//        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}