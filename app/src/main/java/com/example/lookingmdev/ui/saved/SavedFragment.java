package com.example.lookingmdev.ui.saved;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lookingmdev.MainActivity;
import com.example.lookingmdev.R;
import com.example.lookingmdev.adapter.HostelCardAdapter;
import com.example.lookingmdev.databinding.FragmentBookingBinding;
import com.example.lookingmdev.databinding.FragmentSavedBinding;
import com.example.lookingmdev.databinding.FragmentSavedBinding;
import com.example.lookingmdev.model.HostelCard;
import com.example.lookingmdev.ui.account.AccountFragment;
import com.example.lookingmdev.ui.hostels.PageWithHostelsFragment;

import java.util.List;

public class SavedFragment extends Fragment {

    private SavedViewModel savedViewModel;
    private static HostelCardAdapter hostelSavedCardAdapter;

    public static SavedFragment newInstance() {
        return new SavedFragment();
    }

    public static void updateAdapter() {
        if (hostelSavedCardAdapter != null) {
            hostelSavedCardAdapter.notifyDataSetChanged();

        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        System.out.println("onCreateView");


        View view;

        // если авторизованы и в списке сохранненного есть что-то, то
        if (MainActivity.isAuth && MainActivity.savedHostels.size() != 0) {
            view = inflater.inflate(R.layout.fragment_page_with_saved_hostels, container, false);



            setHostelsRecycler(MainActivity.savedHostels, view);

        }
        else if (MainActivity.isAuth && MainActivity.savedHostels.size() == 0) {
            view = inflater.inflate(R.layout.fragment_saved, container, false);
            TextView textView = view.findViewById(R.id.text_saved);
            textView.setText("Здесь будут ваши любимые отели");
        }
        else {
            view = inflater.inflate(R.layout.fragment_saved, container, false);
            TextView textView = view.findViewById(R.id.text_saved);
            textView.setText("Вам необходимо войти в аккаунт <3");
        }

        return view;
    }

    public void setHostelsRecycler(List<HostelCard> savedHostelList, View view) {
        // связываем компонент с его элементом в активити
        RecyclerView hostelsRecycler = view.findViewById(R.id.hostelsRecycler);

        // задаем, как именно отображать элементы (создаем менеджер отображения)
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);

        // передаем в компонент менеджер
        hostelsRecycler.setLayoutManager(layoutManager);

        // создаем адаптер
        hostelSavedCardAdapter = new HostelCardAdapter(view.getContext(), savedHostelList);

        // передаем адаптер в компонент
        hostelsRecycler.setAdapter(hostelSavedCardAdapter);
    }


}