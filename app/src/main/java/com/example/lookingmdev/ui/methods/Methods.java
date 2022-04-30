package com.example.lookingmdev.ui.methods;

import android.content.Context;

import com.example.lookingmdev.MainActivity;

import com.example.lookingmdev.R;

public class Methods {
    Context context;



    public String convertWeekDay(String text, Context context) {

        // получение текущего системного языка
//        String local = Locale.getDefault().getLanguage();
//        System.out.println(local);
//        this.context = context.getApplicationContext();

        this.context = context;
        if (text != null) {
            switch (text) {
                case "0":
                    text = context.getResources().getString(R.string.sunday);
                    break;
                case "1":
                    text = context.getResources().getString(R.string.monday);
                    break;
                case "2":
                    text = context.getResources().getString(R.string.tuesday);
                    break;
                case "3":
                    text = context.getResources().getString(R.string.wednesday);
                    break;
                case "4":
                    text = context.getResources().getString(R.string.thursday);
                    break;
                case "5":
                    text = context.getResources().getString(R.string.friday);
                    break;
                case "6":
                    text = context.getResources().getString(R.string.saturday);
                    break;
            }
        }
        return text;

    }

    public String convertMonth(String text, Context context) {
        this.context = context;

        if (text != null) {

            switch (text) {
                case "0":
                    text = context.getResources().getString(R.string.january);
                    break;
                case "1":
                    text = context.getResources().getString(R.string.february);
                    break;
                case "2":
                    text = context.getResources().getString(R.string.march);
                    break;
                case "3":
                    text = context.getResources().getString(R.string.april);
                    break;
                case "4":
                    text = context.getResources().getString(R.string.may);
                    break;
                case "5":
                    text = context.getResources().getString(R.string.june);
                    break;
                case "6":
                    text = context.getResources().getString(R.string.july);
                    break;
                case "7":
                    text = context.getResources().getString(R.string.august);
                    break;
                case "8":
                    text = context.getResources().getString(R.string.september);
                    break;
                case "9":
                    text = context.getResources().getString(R.string.october);
                    break;
                case "10":
                    text = context.getResources().getString(R.string.november);
                    break;
                case "11":
                    text = context.getResources().getString(R.string.december);
                    break;
            }
        }
        return text;
    }
}
