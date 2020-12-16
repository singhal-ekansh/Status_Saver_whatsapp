package com.app.statusSaver;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class savedStatusAdapter extends RecyclerView.Adapter<savedStatusAdapter.savedStatusViewHolder> {
    private ArrayList<Object> savedFilesList;
    private Context context;

    public savedStatusAdapter(ArrayList<Object> savedFilesList, Context context) {
        this.savedFilesList = savedFilesList;
        this.context = context;
    }

    @NonNull
    @Override
    public savedStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_card, parent, false);
        return new savedStatusAdapter.savedStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull savedStatusViewHolder holder, int position) {
        final StatusModel files = (StatusModel) savedFilesList.get(position);

        Glide.with(context).load(files.getUri()).into(holder.imageView);

        if (files.getUri().toString().endsWith(".mp4")) {
            holder.isVideo.setVisibility(View.VISIBLE);
        } else {
            holder.isVideo.setVisibility(View.INVISIBLE);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PreviewScreen.class);
                intent.putExtra("uri", files.getUri().toString());
                intent.putExtra("name", files.getFilename());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return savedFilesList.size();
    }

    public static class savedStatusViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, isVideo;

        public savedStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mediaImg);
            isVideo = itemView.findViewById(R.id.isVideoIcon);
        }
    }


}
