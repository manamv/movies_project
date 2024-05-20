package com.example.movieapp;

import android.content.Context;
import android.content.Intent;
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

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {

    // Инициализация списка элементов
    private ArrayList<ShowModel> arrayListAllShow;
    // Контекст для Intent
    private Context context;

    public ShowAdapter(ArrayList<ShowModel> arrayListAllShow, Context context) {
        this.arrayListAllShow = arrayListAllShow;
        this.context = context;
    }

    // Создание ViewHolder (вызывается при создании RecyclerView)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_show, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    // Обработка нажатий и взаимодействий
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textViewShowName.setText(arrayListAllShow.get(position).getName());
        Picasso.get().load(arrayListAllShow.get(position).getImageUrl()).into(holder.imageViewShow);


        // Обработчик нажатия
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowDetailActivity.class);
                intent.putExtra("show_id", arrayListAllShow.get(position).getId());
                intent.putExtra("show_language", arrayListAllShow.get(position).getLanguage());
                intent.putExtra("show_premiered", arrayListAllShow.get(position).getPremierDate());
                intent.putExtra("show_summary", arrayListAllShow.get(position).getSummary());
                intent.putExtra("show_img_url", arrayListAllShow.get(position).getImageUrl());
                intent.putExtra("show_name", arrayListAllShow.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListAllShow.size();
    }

    // Объявление и инициализация всех View внутри RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Объявление
        private TextView textViewShowName;
        private ImageView imageViewShow;
        private LinearLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewShowName = itemView.findViewById(R.id.tv_show_name);
            imageViewShow = itemView.findViewById(R.id.ic_show_image);
            // Контейнер
            parent = itemView.findViewById(R.id.single_show);
        }
    }
}
