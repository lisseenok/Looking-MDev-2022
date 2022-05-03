package com.example.lookingmdev.ui.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.databinding.FragmentBookingBinding;

public class BookingFragment extends Fragment {

    private BookingViewModel bookingViewModel;
    private FragmentBookingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBookingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textView = root.findViewById(R.id.text_booking);

        if (MainActivity.isAuth) textView.setText("Здесь будут ваши бронирования");
        else textView.setText("Вам необходимо войти в аккаунт <3");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}