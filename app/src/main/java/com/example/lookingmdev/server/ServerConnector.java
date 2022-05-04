package com.example.lookingmdev.server;


import static com.example.lookingmdev.MainActivity.databaseReference;

import com.example.lookingmdev.model.HostelCard;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ServerConnector {

    public static void pushHostelToServer(HostelCard hostelCard){
        // пушим объект в бд
        databaseReference.push().setValue(hostelCard);
    }

    public static void pushHostelsToServer(){

        // метод для добавления списка в бды
        List<HostelCard> hostelList = new ArrayList<>();

        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("new Date()", 5);
        hostelList.add(new HostelCard("Boutique Hotel Artrium Munchen", "Москва",
                "Проспект Вернадского 78", "hotel1", "Двухместный номер \"Комфорт\" с 1 кроватью и видом на сад", "", 5, 2655, 8.1, hashMap));
        //hostelList.add(new HostelCard("Boutique Hotel Artrium Munchen", "Москва", "Проспект Вернадского 78", "hotel1", "Двухместный номер \"Комфорт\" с 1 кроватью и видом на сад", "", 5, 2655, 8.1, new HashMap<Date, Integer>()));
//        hostelList.add(new HostelCard("Hotel Concorde", "Пенза", "Халтуринская 17", "hotel2", "Этот семейный 4-звездочный отель находится в центре Мюнхена, всего в 200 метрах от станции городской электрички Изартор", "3 номера в отеле", 4366, 8.6, new HashMap<Date, Integer>()));
//        hostelList.add(new HostelCard("Flemings Hotel München-Schwabing", "Коломна", "Пионерская 28", "hotel3", "Отель расположен в мюнхенском районе, Швабинг, всего в 300 метрах от станции метро Münchner Freiheit.", "8 номеров в отеле", 1530, 7.7, new HashMap<Date, Integer>()));
//        hostelList.add(new HostelCard("harry's home hotel & apartments", "Москва", "Минская 13", "hotel4", "Комплекс harry's home hotel & apartments расположен в городе Мюнхен, в 2,4 км от дворца Нимфенбург. К услугам гостей номера и апартаменты с кондиционером и бесплатный Wi-Fi на всей территории", "2 номера в отеле", 2900, 8.7, new HashMap<Date, Integer>()));
        for (HostelCard hostel:
                hostelList) {
            pushHostelToServer(hostel);
        }
    }

}
