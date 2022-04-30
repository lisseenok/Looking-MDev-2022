package com.example.lookingmdev.ui.hostels;

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

import com.example.lookingmdev.R;
import com.example.lookingmdev.adapter.HostelCardAdapter;
import com.example.lookingmdev.model.HostelCard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PageWithHostelsFragment extends Fragment {

    private PageWithHostelsViewModel mViewModel;

    private RecyclerView hostelsRecycler;
    private HostelCardAdapter hostelCardAdapter;

    private static DatabaseReference databaseReference;
    private static String HOSTELS_KEY = "Hostels";
    private List<HostelCard> hostelList = new ArrayList<>();
    private int amountOfHotels = 0;


    TextView sumHostelsText;

    public static PageWithHostelsFragment newInstance() {
        return new PageWithHostelsFragment();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        databaseReference = FirebaseDatabase.getInstance().getReference(HOSTELS_KEY); // проинициализировали бд


//        hostelList.add(new HostelCard("1", "Boutique Hotel Artrium Munchen", "Людвигсфорштадт · 1,4 км от центра", "hotel1", "Двухместный номер \"Комфорт\" с 1 кроватью и видом на сад", "1 номер в отеле", 2655, 8.1));
//        hostelList.add(new HostelCard("2", "Hotel Concorde", "Леэль · 0,5 км от центра", "hotel2", "Этот семейный 4-звездочный отель находится в центре Мюнхена, всего в 200 метрах от станции городской электрички Изартор", "3 номера в отеле", 4366, 8.6));
//        hostelList.add(new HostelCard("3", "Flemings Hotel München-SchwabingОткроется в новом окне", "Швабинг-Фрайман · 3,5 км от центра", "hotel3", "Отель расположен в мюнхенском районе, Швабинг, всего в 300 метрах от станции метро Münchner Freiheit.", "8 номеров в отеле", 1530, 7.7));
//        hostelList.add(new HostelCard("4", "harry's home hotel & apartments", "Моосах · 7 км от центра", "hotel4", "Комплекс harry's home hotel & apartments расположен в городе Мюнхен, в 2,4 км от дворца Нимфенбург. К услугам гостей номера и апартаменты с кондиционером и бесплатный Wi-Fi на всей территории", "2 номера в отеле", 2900, 8.7));

        View view = inflater.inflate(R.layout.fragment_page_with_hostels, container, false);
        getHostelsFromServer(view);
        setAmountOfHotels(view);


        setHostelsRecycler(hostelList, view);
        return view;
    }

    private void setAmountOfHotels(View view) {
        sumHostelsText = view.findViewById(R.id.sumHostelsText);

        String text = String.format("Найдено: %d вариант", amountOfHotels);
        if (hostelList.size() == 1) {
            text += "";
        } else if (hostelList.size() <= 4) {
            text += "а";
        } else if (hostelList.size() <= 20) {
            text += "ов";
        } else if (hostelList.size() % 10 >= 2 && hostelList.size() % 10 <= 4) {
            text += "а";
        } else if (hostelList.size() % 10 >= 5 || hostelList.size() % 10 == 0) {
            text += "ов";
        }
        sumHostelsText.setText(text);
    }

    public void setHostelsRecycler(List<HostelCard> hostelList, View view) {
        // связываем компонент с его элементом в активити
        hostelsRecycler = view.findViewById(R.id.hostelsRecycler);

        // задаем, как именно отображать элементы (создаем менеджер отображения)
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);

        // передаем в компонент менеджер
        hostelsRecycler.setLayoutManager(layoutManager);

        // создаем адаптер
        hostelCardAdapter = new HostelCardAdapter(view.getContext(), hostelList);

        // передаем адаптер в компонент
        hostelsRecycler.setAdapter(hostelCardAdapter);
    }

    private void getHostelsFromServer(View view){
        // создаем слушатель базы данных
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // приходят данные (в объекте snapshot)

                // проверка пустой ли список отелей
                if (hostelList.size() > 0) hostelList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HostelCard hostelCard = dataSnapshot.getValue(HostelCard.class);

                    // добавляем только если объект есть (вдруг ошибки в бд)
                    assert hostelCard != null;
                    hostelList.add(hostelCard);
                    amountOfHotels += 1;
                    setAmountOfHotels(view);
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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PageWithHostelsViewModel.class);
        // TODO: Use the ViewModel
    }

}