package com.example.lookingmdev.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lookingmdev.ui.hostelPage.HostelPageFragment;
import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.model.HostelCard;

import java.util.List;

public class HostelCardAdapter extends RecyclerView.Adapter<HostelCardAdapter.HostelCardViewHolder> {

    Context context;
    List<HostelCard> hostelCards;


    public HostelCardAdapter(Context context, List<HostelCard> hostelCards) {
        this.context = context;
        this.hostelCards = hostelCards;
    }

    @NonNull
    @Override
    public HostelCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater Создает экземпляр XML-файла макета в соответствующих объектах View
        // from Получает LayoutInflater из заданного контекста
        // inflate создаем новую иерархию представлений из указанного xml-ресурса
        View hostelCardItems = LayoutInflater.from(context).inflate(R.layout.hostel_card_item, parent, false);

        // создаем объект HostelCardViewHolder с переданными в него XML элементами
        return new HostelCardViewHolder(hostelCardItems);
    }

    // метод, который возвращает максимальное число комнат, которые можно забронировать на эти даты
    public int getMaxRoomsInHostel(int position) {
        int maxRooms = hostelCards.get(position).getAmountOfHostelRooms();
        for (int i = 1; i < MainActivity.selectedDates.size(); ++i) {
            @SuppressLint("DefaultLocale")
            String key = String.format("%d %d %d - %d %d %d",
                    (MainActivity.selectedDates.get(i - 1).getYear() + 1900),
                    (MainActivity.selectedDates.get(i - 1).getMonth() + 1),
                    MainActivity.selectedDates.get(i - 1).getDate(),
                    (MainActivity.selectedDates.get(i).getYear() + 1900),
                    (MainActivity.selectedDates.get(i).getMonth() + 1),
                    MainActivity.selectedDates.get(i).getDate());

            if (hostelCards.get(position).getListOfBookingDates().containsKey(key) && hostelCards.get(position).getListOfBookingDates().get(key) < maxRooms)
                maxRooms = hostelCards.get(position).getListOfBookingDates().get(key);
        }

        return maxRooms;
    }

    @Override
    public void onBindViewHolder(@NonNull HostelCardViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // position - итератор по элементам списка
        // holder - дизайн каждого отдельного элемента - это объект класса CourseViewHolder, где лежат все элементы, помещенные в поля

        // получаем фотьки с бд
        Glide.with(context).load(hostelCards.get(position).getImage()).into(holder.hostelCardImage);

        // устанавливаем текст карточке в соответствии с данными текущего объекта из массива объектов
        holder.hostelCardNameText.setText(hostelCards.get(position).getHostelName());
        holder.hostelCardRatting.setText("" + hostelCards.get(position).getRating());
        double rating = hostelCards.get(position).getRating();
        if (rating > 9) {
            holder.hostelCardRattingText.setText("Превосходно");
        } else if (rating > 8.5) {
            holder.hostelCardRattingText.setText("Потрясающе");
        } else if (rating > 8) {
            holder.hostelCardRattingText.setText("Очень хорошо");
        } else if (rating > 7.5) {
            holder.hostelCardRattingText.setText("Хорошо");
        }
        holder.hostelCardAddressText.setText((hostelCards.get(position).getCity() + ", " + hostelCards.get(position).getAddress()));
        holder.hostelCardShortDescriptionText.setText(hostelCards.get(position).getShortDescription());

        // currentAmountOfHostelRooms - значение этого поля устанавливается непосредственно на странице отелей на странице поиска
        holder.hostelCardAmountOfHostelRoomsText.setText(("Свободных номеров в отеле: " + hostelCards.get(position).getCurrentAmountOfHostelRooms()));
        holder.hostelCardPriceText.setText((hostelCards.get(position).getPrice() + " ₽"));

        // если мы на странице сохраненных отелей, то используем функцию, для подсчета кол-ва комнат, возможных для брони
        if (MainActivity.startDay == null && MainActivity.selectedPage == 1) {
            holder.hostelCardAmountOfHostelRoomsText.setText("");
        } else if (MainActivity.startDay != null && MainActivity.selectedPage == 1) {
            holder.hostelCardAmountOfHostelRoomsText.setText(("Свободных номеров в отеле: " + getMaxRoomsInHostel(position)));
        }

        if (MainActivity.selectedPage == 2) {
            holder.hostelCardAmountOfHostelRoomsText.setText("");
        }

        // слушатель на клик по сердечку
        holder.iconHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // если пользователь авторизован
                if (MainActivity.isAuth)
                {
                    // проверяем есть ли нажатый отель в списке избранных текущего пользователя (в savedHostels)
                    if (!MainActivity.contains(MainActivity.savedHostels, hostelCards.get(position))){
                        // если отеля нет в списке избранных - добавляем его в статическое поле savedHostels
                        MainActivity.savedHostels.add(hostelCards.get(position));

                        // и меняем иконку сердечка
                        int newIconId = context.getResources().getIdentifier("ic_red_heart", "drawable", context.getPackageName());
                        holder.iconHeart.setImageResource(newIconId);
                    } else {
                        // если же отель есть в savedHostels - при нажатии нам надо его удалить
                        // удаляем
                        MainActivity.removeSaved(hostelCards.get(position).getId());
                        // меняем иконку
                        int newIconId = context.getResources().getIdentifier("ic_heart_border", "drawable", context.getPackageName());
                        holder.iconHeart.setImageResource(newIconId);
                    }
                    // изменили наш список избранных (savedHostels) и теперь пушим его на бд

                    MainActivity.databaseSavedReference.child(MainActivity.firebaseUser.getUid()).setValue(MainActivity.savedHostels);

                    // для страницы с бронированиями: обновляем ресайклер тк карточек отеля, на сердчко
                    // которого мы нажали может быть несколько и у всех надо поменять сердечко
                    MainActivity.bookingFragment.updateBookingsAdapter();

                } else {
                    // если пользователь не авторизован, то выводим тост, чтобы авторизовался
                    Toast.makeText(context.getApplicationContext(), "Необходимо авторизироваться", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // обрабатываем нажатие на карточку
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // обновляем глобальное значение (на всякий случай)
                if (MainActivity.selectedPage == 0) {
                    MainActivity.searchHostelCard = hostelCards.get(position);
                } else if (MainActivity.selectedPage == 1) {
                    MainActivity.savedHostelCard = hostelCards.get(position);
                } else if (MainActivity.selectedPage == 2) {
                    MainActivity.bookingHostelCard = hostelCards.get(position);
                }

                if (MainActivity.selectedPage == 0)
                    MainActivity.searchState = 2;
                else if (MainActivity.selectedPage == 1)
                    MainActivity.savedState = 1;
                else if (MainActivity.selectedPage == 2)
                    MainActivity.bookingState = 1;
//                Bundle bundle = new Bundle();
                // создаем фрагмент с отелем
                MainActivity.hostelPageFragment = new HostelPageFragment();

                AppCompatActivity activity = (AppCompatActivity) context;

                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, MainActivity.hostelPageFragment);
                fragmentTransaction.commit();
            }
        });

        if (MainActivity.isAuth)
        // если пользователь авторизован
        {
            if (MainActivity.contains(MainActivity.savedHostels, hostelCards.get(position))){
                int newIconId = context.getResources().getIdentifier("ic_red_heart", "drawable", context.getPackageName());
                holder.iconHeart.setImageResource(newIconId);
            } else {
                int newIconId = context.getResources().getIdentifier("ic_heart_border", "drawable", context.getPackageName());
                holder.iconHeart.setImageResource(newIconId);
            }
        } else {
            int newIconId = context.getResources().getIdentifier("ic_heart_border", "drawable", context.getPackageName());
            holder.iconHeart.setImageResource(newIconId);
        }


    }

    @Override
    public int getItemCount() {
        return hostelCards.size();
    }

    public static final class HostelCardViewHolder extends RecyclerView.ViewHolder {

        ImageView hostelCardImage, iconHeart;
        TextView hostelCardNameText, hostelCardRatting, hostelCardRattingText, hostelCardAddressText,
                hostelCardShortDescriptionText, hostelCardAmountOfHostelRoomsText, hostelCardPriceText;



        public HostelCardViewHolder(@NonNull View itemView) {
            super(itemView);

            iconHeart = itemView.findViewById(R.id.iconHeart);
            hostelCardImage = itemView.findViewById(R.id.hostelCardImage);
            hostelCardNameText = itemView.findViewById(R.id.hostelCardNameText);
            hostelCardRatting = itemView.findViewById(R.id.hostelCardRatting);
            hostelCardRattingText = itemView.findViewById(R.id.hostelCardRattingText);
            hostelCardAddressText = itemView.findViewById(R.id.hostelCardAddressText);
            hostelCardShortDescriptionText = itemView.findViewById(R.id.hostelCardShortDescriptionText);
            hostelCardAmountOfHostelRoomsText = itemView.findViewById(R.id.hostelCardAmountOfHostelRoomsText);
            hostelCardPriceText = itemView.findViewById(R.id.hostelCardPriceText);

        }
    }
}
