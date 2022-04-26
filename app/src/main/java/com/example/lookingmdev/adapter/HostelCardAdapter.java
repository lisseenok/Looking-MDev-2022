package com.example.lookingmdev.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        System.out.println("111111111111");
        View hostelCardItems = LayoutInflater.from(context).inflate(R.layout.hostel_card_item, parent, false);

        // создаем объект HostelCardViewHolder с переданными в него XML элементами
        return new HostelCardViewHolder(hostelCardItems);
    }

    @Override
    public void onBindViewHolder(@NonNull HostelCardViewHolder holder, int position) {
        // position - итератор по элементам списка
        // holder - дизайн каждого отдельного элемента - это объект класса CourseViewHolder, где лежат все элементы, помещенные в поля

        // устанавливаем текст карточке в соответствии с данными текущего объекта из массива объектов
        System.out.println(12333);
        holder.hostelCardNameText.setText(hostelCards.get(position).getName());
        holder.hostelCardRatting.setText("" + hostelCards.get(position).getRating());
        double rating = hostelCards.get(position).getRating();
        if (rating > 8) {
            holder.hostelCardRattingText.setText("Очень хорошо");
        }
        holder.hostelCardAddressText.setText(hostelCards.get(position).getAddress());
        holder.hostelCardDescriptionText.setText(hostelCards.get(position).getShortDescription());
        holder.hostelCardCharacteristicsText.setText(hostelCards.get(position).getCharacteristics());
        holder.hostelCardPriceText.setText("" + hostelCards.get(position).getPrice());


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
            System.out.println("111111111111");

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
