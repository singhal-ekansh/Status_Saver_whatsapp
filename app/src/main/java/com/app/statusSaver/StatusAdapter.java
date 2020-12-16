package com.app.statusSaver;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.statusViewHolder> {

    private ArrayList<Object> filesList;
    private Context context;

    public StatusAdapter(ArrayList<Object> filesList, Context context) {
        this.filesList = filesList;
        this.context = context;
    }

    @NonNull
    @Override
    public statusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_card, parent, false);
        return new statusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final statusViewHolder holder, int position) {

        final StatusModel files = (StatusModel) filesList.get(position);
        File[] savedFiles;
        String saved = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME;
        File savedDir = new File(saved);

        int flag=0;
        if (savedDir.exists()) {
            savedFiles = savedDir.listFiles();

            for (File savedFile : savedFiles) {
                if (files.getFilename().equals(savedFile.getName())) {
                    holder.saveStatusBtn.setVisibility(View.GONE);
                    flag=1;
                    break;
                }
            }
            if(flag==0)
                holder.saveStatusBtn.setVisibility(View.VISIBLE);
        }


        if (files.getUri().toString().endsWith(".mp4")) {
            holder.videoIcon.setVisibility(View.VISIBLE);
            holder.imgName.setText("Video");
        } else {
            holder.videoIcon.setVisibility(View.INVISIBLE);
            holder.imgName.setText("Image");
        }


        Glide.with(context).load(files.getUri()).into(holder.statusImageView);

        holder.saveStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFolder();
                final String path = files.getPath();
                final File file = new File(path);

                String destinationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME;
                File destFile = new File(destinationPath);

                try {
                    FileUtils.copyFileToDirectory(file, destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MediaScannerConnection.scanFile(context,
                        new String[]{destinationPath + files.getFilename()},
                        new String[]{"*/*"},
                        new MediaScannerConnection.MediaScannerConnectionClient() {
                            @Override
                            public void onMediaScannerConnected() {

                            }

                            @Override
                            public void onScanCompleted(String path, Uri uri) {

                            }
                        });
                Toast.makeText(context, "Status Saved", Toast.LENGTH_SHORT).show();
                holder.saveStatusBtn.setVisibility(View.GONE);
            }
        });

        holder.statusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PreviewScreen.class);
                intent.putExtra("uri",files.getUri().toString());
                intent.putExtra("name",files.getFilename());
                context.startActivity(intent);
            }
        });
    }

    private void checkFolder() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME;
        File dir = new File(path);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

    public static class statusViewHolder extends RecyclerView.ViewHolder {

        ImageView statusImageView, saveStatusBtn, videoIcon;
        TextView imgName;

        public statusViewHolder(@NonNull View itemView) {
            super(itemView);
            statusImageView = itemView.findViewById(R.id.statusImage);
            saveStatusBtn = itemView.findViewById(R.id.saveBtn);
            videoIcon = itemView.findViewById(R.id.videoBtn);
            imgName = itemView.findViewById(R.id.name);
        }
    }
}
