package com.example.lookingmdev.ui.hostels;

import static com.example.lookingmdev.MainActivity.databaseReference;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.adapter.HostelCardAdapter;
import com.example.lookingmdev.model.HostelCard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PageWithHostelsFragment extends Fragment {

    private PageWithHostelsViewModel mViewModel;

    private RecyclerView hostelsRecycler;
    private HostelCardAdapter hostelCardAdapter;

    private List<HostelCard> hostelList = new ArrayList<>();
    private int amountOfHostels = 0;
    private int maxRooms;
    private boolean isCreated = false;


    TextView sumHostelsText;

    public static PageWithHostelsFragment newInstance() {
        return new PageWithHostelsFragment();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        System.out.println("onCreateView");
        View view = inflater.inflate(R.layout.fragment_page_with_hostels, container, false);

        getHostelsFromServer(view);


        // устанавливаем в самом начале
        setAmountOfHostels(view);

        setHostelsRecycler(hostelList, view);
        return view;
    }

    private void setAmountOfHostels(View view) {
        // этот метод нужен тк при смене языка все ломается к чертям
        if(isAdded()) {

        sumHostelsText = view.findViewById(R.id.sumHostelsText);

        String text = String.format("%s %d", getResources().getString(R.string.hotelsFound), amountOfHostels);
        sumHostelsText.setText(text);
        }
    }

    // метод проверяет, доступен ли хоть один номер в отеле на эти даты
    // а так же считает, сколько доступно комнат

    private boolean dateCheck(HostelCard hostelCard) {
        maxRooms = hostelCard.getAmountOfHostelRooms();

        for (int i = 1; i < MainActivity.selectedDates.size(); ++i) {
            @SuppressLint("DefaultLocale")
            String key = String.format("%d %d %d - %d %d %d",
                    (MainActivity.selectedDates.get(i - 1).getYear() + 1900),
                    (MainActivity.selectedDates.get(i - 1).getMonth() + 1),
                    MainActivity.selectedDates.get(i - 1).getDate(),
                    (MainActivity.selectedDates.get(i).getYear() + 1900),
                    (MainActivity.selectedDates.get(i).getMonth() + 1),
                    MainActivity.selectedDates.get(i).getDate());
            if (hostelCard.getListOfBookingDates().containsKey(key) && hostelCard.getListOfBookingDates().get(key) == 0) {
                return false;
            } else if (hostelCard.getListOfBookingDates().containsKey(key) && hostelCard.getListOfBookingDates().get(key) < maxRooms)
                maxRooms = hostelCard.getListOfBookingDates().get(key);
        }

        return true;
    }

    private void getHostelsFromServer(View view){

        // создаем слушатель базы данных
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("Data changed");
                amountOfHostels = 0;
                // приходят данные (в объекте snapshot)

                // проверка пустой ли список отелей

                if (hostelList.size() > 0) hostelList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HostelCard hostelCard = dataSnapshot.getValue(HostelCard.class);

                    // сортировка по городам и датам
                    // добавляем только если объект есть (вдруг ошибки в бд)
                    if (hostelCard != null && hostelCard.getCity().equals(MainActivity.city) && dateCheck(hostelCard) && maxRooms >= MainActivity.rooms) {
                        hostelCard.setId(dataSnapshot.getKey());
                        hostelCard.setCurrentAmountOfHostelRooms(maxRooms);

                        hostelList.add(hostelCard);
                        amountOfHostels += 1;
                    }
                    // обновляем данные выбранного отеля
                    if (MainActivity.searchHostelCard != null && hostelCard != null && MainActivity.searchHostelCard.getId().equals(hostelCard.getId()))
                        MainActivity.searchHostelCard = hostelCard;
                }
                // нужна проверка, если вдруг данные поменяются на сервере, то у нас не должно крашиться приложение, если мы на другой странице
                if (MainActivity.searchState == 1) {
                    setAmountOfHostels(view);
                }

                // сообщаем адаптеру, что данные поменялись
                hostelCardAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        // добавляем в нашу бд слушатель
        databaseReference.addValueEventListener(valueEventListener);
    }


    public void setHostelsRecycler(List<HostelCard> hostelList, View view) {
        // связываем компонент с его элементом в активити
        hostelsRecycler = view.findViewById(R.id.hostelsRecycler);

        // задаем, как именно отображать элементы (создаем менеджер отображения)
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);

        // передаем в компонент менеджер
        hostelsRecycler.setLayoutManager(layoutManager);

        // создаем адаптер
        hostelCardAdapter = new HostelCardAdapter(getContext(), hostelList);

        // передаем адаптер в компонент
        hostelsRecycler.setAdapter(hostelCardAdapter);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PageWithHostelsViewModel.class);
        // TODO: Use the ViewModel
    }

}