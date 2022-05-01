package com.example.lookingmdev;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lookingmdev.model.HostelCard;

public class HostelPageFragment extends Fragment {
    private HostelPageViewModel mViewModel;
    public HostelPageFragment() {

    }
    public static HostelPageFragment newInstance() {
        return new HostelPageFragment();
    }

    HostelCard hostelCard;

    public HostelPageFragment(HostelCard hostelCard) {
        this.hostelCard = hostelCard;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hostel_page, container, false);

        System.out.println(hostelCard.getName());


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HostelPageViewModel.class);
        // TODO: Use the ViewModel
    }

}