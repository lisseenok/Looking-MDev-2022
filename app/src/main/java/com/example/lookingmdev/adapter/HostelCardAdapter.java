package com.example.lookingmdev.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    public void onBindViewHolder(@NonNull HostelCardViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // position - итератор по элементам списка
        // holder - дизайн каждого отдельного элемента - это объект класса CourseViewHolder, где лежат все элементы, помещенные в поля

        // устанавливаем текст карточке в соответствии с данными текущего объекта из массива объектов
        holder.hostelCardNameText.setText(hostelCards.get(position).getName());
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
        holder.hostelCardAddressText.setText(hostelCards.get(position).getAddress());
        holder.hostelCardDescriptionText.setText(hostelCards.get(position).getShortDescription());
        holder.hostelCardCharacteristicsText.setText(hostelCards.get(position).getCharacteristics());
        holder.hostelCardPriceText.setText(hostelCards.get(position).getPrice() + " ₽");


        // обрабатываем нажатие на карточку
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // обновляем глобальное значение (на всякий случай)
                MainActivity.openHostelPage(hostelCards.get(position));
                HostelCard hostelCard = hostelCards.get(position);
                MainActivity.searchState = 2;
//                Bundle bundle = new Bundle();

                // создаем фрагмент с отелем
                MainActivity.hostelPageFragment = new HostelPageFragment(hostelCard);

                AppCompatActivity activity = (AppCompatActivity) context;

                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, MainActivity.hostelPageFragment);
                fragmentTransaction.commit();
            }
        });


        // создаем идентификатор  фотографии:
        // getResources(): создаем окно ко всем ресурсам проекта
        // getIdentifier(): возвращает ресурс из заданной папки
        // превращаем название картинки в идентификатор
        int imageId = context.getResources().getIdentifier(hostelCards.get(position).getImage(), "drawable", context.getPackageName());
        holder.hostelCardImage.setImageResource(imageId);

    }

    @Override
    public int getItemCount() {
        return hostelCards.size();
    }

    public static final class HostelCardViewHolder extends RecyclerView.ViewHolder {

        ImageView hostelCardImage;
        TextView hostelCardNameText, hostelCardRatting, hostelCardRattingText, hostelCardAddressText,
                hostelCardDescriptionText, hostelCardCharacteristicsText, hostelCardPriceText;



        public HostelCardViewHolder(@NonNull View itemView) {
            super(itemView);

            hostelCardImage = itemView.findViewById(R.id.hostelCardImage);
            hostelCardNameText = itemView.findViewById(R.id.hostelCardNameText);
            hostelCardRatting = itemView.findViewById(R.id.hostelCardRatting);
            hostelCardRattingText = itemView.findViewById(R.id.hostelCardRattingText);
            hostelCardAddressText = itemView.findViewById(R.id.hostelCardAddressText);
            hostelCardDescriptionText = itemView.findViewById(R.id.hostelCardDescriptionText);
            hostelCardCharacteristicsText = itemView.findViewById(R.id.hostelCardCharacteristicsText);
            hostelCardPriceText = itemView.findViewById(R.id.hostelCardPriceText);

        }
    }
}
