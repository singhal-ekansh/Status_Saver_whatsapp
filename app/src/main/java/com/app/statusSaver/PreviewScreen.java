package com.app.statusSaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;

import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.File;

public class PreviewScreen extends AppCompatActivity {

    ImageView imagePreview;
    VideoView videoPreview;
    Intent intent;
    Uri uri;
    Toolbar toolbar;
    String fileType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_screen);

        intent = getIntent();
        toolbar = findViewById(R.id.imgToolbar);

        toolbar.setTitle(intent.getStringExtra("name"));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        imagePreview = findViewById(R.id.imgPrev);
        videoPreview = findViewById(R.id.videoPrev);

        uri = Uri.parse(intent.getStringExtra("uri"));

        if (uri.toString().endsWith(".mp4")) {
            fileType = "video/mp4";

            imagePreview.setVisibility(View.GONE);
            videoPreview.setVisibility(View.VISIBLE);
            videoPreview.setVideoURI(uri);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoPreview);
            videoPreview.setMediaController(mediaController);
            videoPreview.start();

        } else {
            fileType = "image/*";
            imagePreview.setVisibility(View.VISIBLE);
            videoPreview.setVisibility(View.GONE);
            Glide.with(this).load(uri).into(imagePreview);
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share:
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(getApplicationContext(),
                                BuildConfig.APPLICATION_ID + ".provider", new File(uri.getPath())));
                        shareIntent.setType(fileType);
                        startActivity(Intent.createChooser(shareIntent, "Share to"));
                        return false;
                    case R.id.delete:
                        new File(uri.getPath()).delete();
                        finish();
                        return false;
                }
                return false;
            }
        });
    }


}