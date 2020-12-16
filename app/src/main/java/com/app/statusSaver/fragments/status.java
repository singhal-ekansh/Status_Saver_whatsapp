package com.app.statusSaver.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.statusSaver.Constants;
import com.app.statusSaver.R;
import com.app.statusSaver.StatusAdapter;
import com.app.statusSaver.StatusModel;

import java.io.File;
import java.util.ArrayList;


public class status extends Fragment {

    RecyclerView statusRecyclerView;
    StatusAdapter adapter;
    File[] files;
    ArrayList<Object> filesList = new ArrayList<>();
    TextView noStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_status, container, false);
        statusRecyclerView = view.findViewById(R.id.statusRecycler);
        noStatus = view.findViewById(R.id.noStatusText);
        return view;
    }

    private void setStatuses() {
        filesList.clear();
        statusRecyclerView.setHasFixedSize(true);
        statusRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getFiles();
        if(filesList.size()==0){
            noStatus.setVisibility(View.VISIBLE);
            statusRecyclerView.setVisibility(View.INVISIBLE);
        }else{
            noStatus.setVisibility(View.GONE);
            statusRecyclerView.setVisibility(View.VISIBLE);
        }
        adapter = new StatusAdapter(filesList, getContext());
        statusRecyclerView.setAdapter(adapter);
    }

    private void getFiles() {

        StatusModel data;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.FOLDER_NAME + "Media/.Statuses";
        File targetDir = new File(targetPath);

        files = targetDir.listFiles();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            data = new StatusModel(file.getAbsolutePath(), file.getName(), Uri.fromFile(file));

            if (!data.getUri().toString().endsWith(".nomedia"))
                filesList.add(data);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setStatuses();
    }
}