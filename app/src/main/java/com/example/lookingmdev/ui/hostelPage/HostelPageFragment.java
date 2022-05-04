package com.example.lookingmdev.ui.hostelPage;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.model.HostelCard;
import com.example.lookingmdev.ui.methods.Methods;

public class HostelPageFragment extends Fragment {
    private HostelPageViewModel mViewModel;
    public HostelPageFragment() {

    }
    public static HostelPageFragment newInstance() {
        return new HostelPageFragment();
    }

    HostelCard hostelCard = MainActivity.hostelCard;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hostel_page, container, false);

        Methods methods = new Methods();

        // Объявляем доп переменные для конвертации глобальных значений в нужную локализацию
        String startWeekDay = methods.convertWeekDay(MainActivity.startWeekDay, getContext());
        String endWeekDay = methods.convertWeekDay(MainActivity.endWeekDay, getContext());
        String startMonth = methods.convertMonth(MainActivity.startMonth, getContext());
        String endMonth = methods.convertMonth(MainActivity.endMonth, getContext());

        // Инициализируем переменные, в которые кладем текстовые поля со страницы отеля
        ImageView hostelImage = view.findViewById(R.id.hostelImage);
        TextView hostelName = view.findViewById(R.id.hostelName);
        TextView hostelTitle = view.findViewById(R.id.hostelTitle);
        TextView hostelRatting = view.findViewById(R.id.hostelRatting);
        TextView hostelStartDate = view.findViewById(R.id.hostelStartDate);
        TextView hostelEndDate = view.findViewById(R.id.hostelEndDate);
        TextView hostelQuests = view.findViewById(R.id.hostelQuests);
        TextView hostelAddress = view.findViewById(R.id.hostelAddress);
        TextView hostelFullDescription = view.findViewById(R.id.hostelFullDescription);

        int image = getContext().getResources().getIdentifier(hostelCard.getImage(), "drawable", getContext().getPackageName());
        hostelImage.setImageResource(image);

        // устанавливаем поля
        hostelName.setText(hostelCard.getHostelName());
        hostelTitle.setText(hostelCard.getHostelName());
        hostelRatting.setText((String.valueOf(hostelCard.getRating())));
        hostelStartDate.setText(String.format("%s, %s %s", startWeekDay, MainActivity.startDay, startMonth));
        hostelEndDate.setText(String.format("%s, %s %s", endWeekDay, MainActivity.endDay, endMonth));

        if (MainActivity.children == 0) {
            MainActivity.visitors = getResources().getString(R.string.room) + ": " +
                    MainActivity.rooms + " • "
                    + getResources().getString(R.string.adult) +
                    ": " + MainActivity.adults + " • " + getResources().getString(R.string.noChildren);
        } else {
            MainActivity.visitors = getResources().getString(R.string.room) + ": " +
                    MainActivity.rooms + " • " + getResources().getString(R.string.adult) +
                    ": " + MainActivity.adults + " • " + getResources().getString(R.string.child) +
                    ": " + MainActivity.children;
        }
        hostelQuests.setText(MainActivity.visitors);
        hostelAddress.setText(MainActivity.city);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HostelPageViewModel.class);
        // TODO: Use the ViewModel
    }

}