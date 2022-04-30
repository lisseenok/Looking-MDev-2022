package com.example.lookingmdev.server;

import com.example.lookingmdev.model.HostelCard;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ServerConnector {
    // объявляем переменную с бдшкой
    private static DatabaseReference databaseReference;

    // задаем ключ, который "объединит" все объекты
    private static String HOSTELS_KEY = "Hostels";

    public static void initDatabase(){
        // инициализация бд
        databaseReference = FirebaseDatabase.getInstance().getReference(HOSTELS_KEY);
    }

    public static void pushHostelToServer(HostelCard hostelCard){
        // пушим объект в бд
        databaseReference.push().setValue(hostelCard);
    }

    public static void pushHostelsToServer(){
        // метод для добавления списка в бд
        ServerConnector.initDatabase();
        List<HostelCard> hostelList = new ArrayList<>();
        hostelList.add(new HostelCard("1", "Boutique Hotel Artrium Munchen", "Людвигсфорштадт · 1,4 км от центра", "hotel1", "Двухместный номер \"Комфорт\" с 1 кроватью и видом на сад", "1 номер в отеле", 2655, 8.1));
        hostelList.add(new HostelCard("2", "Hotel Concorde", "Леэль · 0,5 км от центра", "hotel2", "Этот семейный 4-звездочный отель находится в центре Мюнхена, всего в 200 метрах от станции городской электрички Изартор", "3 номера в отеле", 4366, 8.6));
        hostelList.add(new HostelCard("3", "Flemings Hotel München-SchwabingОткроется в новом окне", "Швабинг-Фрайман · 3,5 км от центра", "hotel3", "Отель расположен в мюнхенском районе, Швабинг, всего в 300 метрах от станции метро Münchner Freiheit.", "8 номеров в отеле", 1530, 7.7));
        hostelList.add(new HostelCard("4", "harry's home hotel & apartments", "Моосах · 7 км от центра", "hotel4", "Комплекс harry's home hotel & apartments расположен в городе Мюнхен, в 2,4 км от дворца Нимфенбург. К услугам гостей номера и апартаменты с кондиционером и бесплатный Wi-Fi на всей территории", "2 номера в отеле", 2900, 8.7));
        for (HostelCard hostel:
                hostelList) {
            pushHostelToServer(hostel);
        }
    }

}
