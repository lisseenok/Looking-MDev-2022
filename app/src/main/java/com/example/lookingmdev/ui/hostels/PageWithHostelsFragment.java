package com.example.lookingmdev.ui.hostels;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.adapter.HostelCardAdapter;
import com.example.lookingmdev.model.HostelCard;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

public class PageWithHostelsFragment extends Fragment {

    private PageWithHostelsViewModel mViewModel;
    private RecyclerView hostelsRecycler;
    private HostelCardAdapter hostelCardAdapter;

    public static PageWithHostelsFragment newInstance() {
        return new PageWithHostelsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        List<HostelCard> hostelList = new ArrayList<>();
        hostelList.add(new HostelCard("Boutique Hotel Artrium Munchen", "Людвигсфорштадт · 1,4 км от центра", "hotel1", "Двухместный номер \"Комфорт\" с 1 кроватью и видом на сад", "1 номер в отеле", 2655, 8.1));
        hostelList.add(new HostelCard("Boutique Hotel Artrium Munchen", "Людвигсфорштадт · 1,4 км от центра", "hotel1", "Двухместный номер \"Комфорт\" с 1 кроватью и видом на сад", "1 номер в отеле", 2655, 8.1));
        hostelList.add(new HostelCard("Лиза Цаплина", "Людвигсфорштадт · 1,4 км от центра", "hotel1", "Двухместный номер \"Комфорт\" с 1 кроватью и видом на сад", "1 номер в отеле", 2655, 8.1));
        hostelList.add(new HostelCard("Boutique Hotel Artrium Munchen", "Людвигсфорштадт · 1,4 км от центра", "hotel1", "Двухместный номер \"Комфорт\" с 1 кроватью и видом на сад", "1 номер в отеле", 2655, 8.1));

        View view = inflater.inflate(R.layout.fragment_page_with_hostels, container, false);
        setHostelsRecycler(hostelList, view);
        return view;
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
        System.out.println(getContext());
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