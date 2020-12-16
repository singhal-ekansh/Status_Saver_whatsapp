package com.app.statusSaver;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;

public class wa_image_video extends AppCompatActivity {

    RecyclerView recyclerView;
    savedStatusAdapter galleryAdapter;
    File[] files;
    ArrayList<Object> filesList = new ArrayList<>();
    Intent intent;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wa_image_video);

        intent = getIntent();
        title = intent.getStringExtra("title");

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle(title);
        toolbar.setHomeButtonEnabled(true);
        toolbar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.galleryRecycler);


    }

    private void setImageOrVideo() {
        filesList.clear();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4, GridLayoutManager.VERTICAL, false));
        getFiles();
        galleryAdapter = new savedStatusAdapter(filesList, getApplicationContext());
        recyclerView.setAdapter(galleryAdapter);
    }

    private void getFiles() {
        StatusModel data;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.FOLDER_NAME + "Media/" + title;
        File targetDir = new File(targetPath);
        if (targetDir.exists()) {

            files = targetDir.listFiles();

            for (File file : files) {
                data = new StatusModel(file.getAbsolutePath(), file.getName(), Uri.fromFile(file));

                if (!data.getUri().toString().endsWith(".nomedia") && !data.getUri().toString().endsWith("Sent") && !data.getUri().toString().endsWith("Private"))
                    filesList.add(data);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setImageOrVideo();
    }
}