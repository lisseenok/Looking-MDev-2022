package com.example.lookingmdev.ui.account.auth.googleAuth;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lookingmdev.R;
import com.example.lookingmdev.databinding.FragmentGoogleAuthBinding;

public class GoogleAuthFragment extends Fragment {

    private GoogleAuthViewModel mViewModel;
    private FragmentGoogleAuthBinding binding;

    public static GoogleAuthFragment newInstance() {
        return new GoogleAuthFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_google_auth, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GoogleAuthViewModel.class);
        // TODO: Use the ViewModel
    }

}