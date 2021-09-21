package com.apps.myturn.Adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.myturn.Models.NewsModel;
import com.apps.myturn.R;
import com.apps.myturn.ReadNewsActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import static com.apps.myturn.Api.IMG;
import static com.apps.myturn.Api.IMG1;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.viewholder> {
    List<NewsModel> newsModelList;

    public NewsAdapter(List<NewsModel> newsModelList) {
        this.newsModelList = newsModelList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(IMG1+newsModelList.get(position).getImage()).into(holder.imageView);
        Log.e("TG",String.valueOf(IMG1+newsModelList.get(position).getImage()));
        holder.newsHeading.setText(newsModelList.get(position).getHeading());
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        newsModelList.get(position).getUrl()
                        +"\n\n"
                        +newsModelList.get(position).getHeading());
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");

                view.getContext().startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ReadNewsActivity.class);
                intent.putExtra("url",newsModelList.get(position).getUrl());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsModelList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView newsHeading;
        ImageView imageView;
        ImageButton shareButton;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            newsHeading = itemView.findViewById(R.id.heading);
            imageView   = itemView.findViewById(R.id.image);
            shareButton = itemView.findViewById(R.id.shareBtn);
        }
    }
}
