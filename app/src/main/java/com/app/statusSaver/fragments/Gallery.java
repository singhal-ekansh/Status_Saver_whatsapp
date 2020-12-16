package com.app.statusSaver.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.statusSaver.R;
import com.app.statusSaver.wa_image_video;

import java.util.ArrayList;

public class Gallery extends Fragment {

    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> folderNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        listView = view.findViewById(R.id.foldersList);
        folderNames = new ArrayList<>();
        folderNames.add("WhatsApp Images");
        folderNames.add("WhatsApp Videos");
        folderNames.add("WhatsApp Profile photos");
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, folderNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), wa_image_video.class);
                if (position == 0)
                    intent.putExtra("title", "WhatsApp Images");
                else if (position == 1)
                    intent.putExtra("title", "WhatsApp Video");
                else if(position==2)
                    intent.putExtra("title", "WhatsApp Profile photos");
                startActivity(intent);
            }
        });
        return view;
    }
}