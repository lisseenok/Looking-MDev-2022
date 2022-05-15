package com.example.lookingmdev.ui.booking;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.adapter.HostelCardAdapter;
import com.example.lookingmdev.databinding.FragmentBookingBinding;
import com.example.lookingmdev.model.HostelCard;

import java.util.List;

public class BookingFragment extends Fragment {

    private BookingViewModel bookingViewModel;
    private static HostelCardAdapter hostelBookingsCardAdapter;


    @SuppressLint("NotifyDataSetChanged")
    public static void updateBookingsAdapter() {
        if (hostelBookingsCardAdapter != null) {
            hostelBookingsCardAdapter.notifyDataSetChanged();

        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view;
        // если авторизованы и в списке сохранненного есть что-то, то
        if (MainActivity.isAuth && MainActivity.bookingsHostels.size() != 0) {
            view = inflater.inflate(R.layout.fragment_page_with_bookings_hostels, container, false);

            setHostelsRecycler(MainActivity.bookingsHostels, view);

        } else if (MainActivity.isAuth && MainActivity.bookingsHostels.size() == 0) {
            view = inflater.inflate(R.layout.fragment_booking, container, false);
            TextView textView = view.findViewById(R.id.text_bookings);
            textView.setText(getResources().getString(R.string.bookingEmptyPageText));
        }
        else {
            view = inflater.inflate(R.layout.fragment_booking, container, false);
            TextView textView = view.findViewById(R.id.text_bookings);
            textView.setText((getResources().getString(R.string.haveToLogInSearchSavedBooking) + "<З"));
        }

        return view;
    }


    public void setHostelsRecycler(List<HostelCard> savedHostelList, View view) {
        // связываем компонент с его элементом в активити
        RecyclerView hostelsRecycler = view.findViewById(R.id.hostelsRecycler);

        // задаем, как именно отображать элементы (создаем менеджер отображения)
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);

        // передаем в компонент менеджер
        hostelsRecycler.setLayoutManager(layoutManager);

        // создаем адаптер
        hostelBookingsCardAdapter = new HostelCardAdapter(view.getContext(), savedHostelList);

        // передаем адаптер в компонент
        hostelsRecycler.setAdapter(hostelBookingsCardAdapter);
    }


}