package com.example.lookingmdev.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.BreakIterator;

public class SearchFragment extends Fragment {


    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;
    static TextView dateText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        searchViewModel =
//                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dateText = root.findViewById(R.id.dateText);
        if (MainActivity.date == null)
            dateText.setText(getResources().getString(R.string.date));
        else
            setNewText();
//        final TextView textView = binding.textSearch;
//        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;

    }
    public void setNewText() {

        dateText.setText(MainActivity.date);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}