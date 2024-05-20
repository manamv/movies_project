package com.example.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    // Инициализация списка элементов
    private ArrayList<CastModel> castList;
    // Контекст для тостов
    private Context appContext;

    public CastAdapter(ArrayList<CastModel> castList, Context appContext) {
        this.castList = castList;
        this.appContext = appContext;
    }

    // Создание ViewHolder (вызывается при создании RecyclerView)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cast, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    // Обработка нажатий и взаимодействий
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.castNameText.setText(castList.get(position).getActorName());
        Picasso.get().load(castList.get(position).getActorImageUrl()).into(holder.castImage);

    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    // Объявление и инициализация всех View внутри RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Объявление
        private TextView castNameText;
        private ImageView castImage;
        private LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            castNameText = itemView.findViewById(R.id.tv_cast_name);
            castImage = itemView.findViewById(R.id.ic_cast_image);
            // Контейнер
            parentLayout = itemView.findViewById(R.id.single_cast);
        }
    }
}
