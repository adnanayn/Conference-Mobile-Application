/*
 * Copyright (c) 2013, Ivan Mylyanyk
 * License: BSD-2 (see LICENSE)
 * 
 * This code was modified with Author permission.
 * Modifications:
 * Added 3 ArrayList
 * Defined Pages = 3
 * Added JSON parsing function
 * Implement switch statement for shwoing page specific list
 * 
 * */

package com.yasiradnan.Schedule;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.yasiradnan.conference.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ScheduleSlideFragment extends Fragment {

    final static String ARG_DATA = "data";

    private ScheduleItem[] ScheduleInformation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.schedule, container, false);

        final ListView list = (ListView)rootView.findViewById(R.id.list);
        BinderData bindingData = new BinderData(this.getActivity(), ScheduleInformation);
        list.setAdapter(bindingData);

        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ScheduleItem item = ScheduleInformation[position];

                if (item.getItemType() == 0 || item.getItemType() == 3 || item.getItemType() == 2)
                    return;

                Intent intent = new Intent(ScheduleSlideFragment.this.getActivity(),
                        ContentExtended.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("content", item.getContent());
                startActivity(intent);
            }
        });

        return rootView;
    }

    public static Fragment create(ScheduleItem[] data) {

        Fragment fragment = new ScheduleSlideFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, data);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("schedule", "onCreate()");
        super.onCreate(savedInstanceState);
        Serializable serializable = getArguments().getSerializable(ARG_DATA);
        ScheduleInformation = (ScheduleItem[]) serializable;
    }

}
