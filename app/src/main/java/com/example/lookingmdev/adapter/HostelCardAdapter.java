package com.example.lookingmdev.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HostelCardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static final class HostelCardViewHolder extends RecyclerView.ViewHolder {
        public HostelCardViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
