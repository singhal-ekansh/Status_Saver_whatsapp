package com.app.statusSaver.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.statusSaver.Constants;
import com.app.statusSaver.R;
import com.app.statusSaver.StatusModel;
import com.app.statusSaver.savedStatusAdapter;

import java.io.File;
import java.util.ArrayList;

public class savedStatus extends Fragment {

    RecyclerView savedRecyclerView;
    savedStatusAdapter adapter;
    File[] files;
    ArrayList<Object> filesList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_status, container, false);
        savedRecyclerView = view.findViewById(R.id.savedRecycler);

        return view;
    }

    private void setSavedStatuses() {
        filesList.clear();
        savedRecyclerView.setHasFixedSize(true);
        savedRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        getFiles();
        adapter = new savedStatusAdapter(filesList, getContext());
        savedRecyclerView.setAdapter(adapter);
    }

    private void getFiles() {
        StatusModel data;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME;
        File targetDir = new File(targetPath);
        if (targetDir.exists()) {

            files = targetDir.listFiles();

            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                data = new StatusModel(file.getAbsolutePath(), file.getName(), Uri.fromFile(file));

                if (!data.getUri().toString().endsWith(".nomedia"))
                    filesList.add(data);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setSavedStatuses();
    }
}