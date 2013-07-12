/*
 * Copyright (c) 2013, Ivan Mylyanyk
 * License: BSD-2 (see LICENSE)
 * This code was modified with Author permission.
 * Modifications:
 * Get Array length from getLength()
 * */

package com.yasiradnan.Schedule;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.yasiradnan.conference.R;
import com.yasiradnan.utils.JSONReader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class ScheduleMainActivity extends FragmentActivity {

    public static int totalPages;

    private static ScheduleItem[][] data;

    /*
     * Parsing JSON to get the Schedule Information's
     */
    private void getJsonData() {
        try {
            InputStream inStream = this.getResources().openRawResource(R.raw.program);
            JSONArray jsonArray = JSONReader.parseStream(inStream);

            totalPages = jsonArray.length();
            data = new ScheduleItem[totalPages][];

            for (int counter = 0; counter < jsonArray.length(); counter++) {
                JSONObject jsonObject = jsonArray.getJSONObject(counter);
                String getDate = jsonObject.getString("date");
                JSONArray getFirstArray = new JSONArray(jsonObject.getString("events"));

                int itemsCount = getFirstArray.length();

                ArrayList<ScheduleItem> items = new ArrayList<ScheduleItem>();

                for (int i = 0; i < itemsCount; i++) {

                    JSONObject getJSonObj = (JSONObject)getFirstArray.get(i);
                    String time = getJSonObj.getString("time");
                    Log.e("Time Log",time);
                    String type = getJSonObj.getString("type");
                    String title = getJSonObj.getString("title");
                    int typeId = getJSonObj.getInt("type_id");

                    items.add(new ScheduleItem(time, title, typeId, getDate));

                    /* a session entry contains multiple sub events */
                    if (typeId == 0) {

                        JSONArray getEventsArray = new JSONArray(getJSonObj.getString("events"));

                        for (int j = 0; j < getEventsArray.length(); j++) {

                            JSONObject getJSonEventobj = (JSONObject)getEventsArray.get(j);
                            int typeEventId = getJSonEventobj.getInt("type_id");

                            if (typeEventId == 1) {

                                String EventInfo = getJSonEventobj.getString("info");
                                String EventTitle = getJSonEventobj.getString("title");
                                String Eventtime = getJSonEventobj.getString("time");
                                items.add(new ScheduleItem(Eventtime, EventTitle,
                                        EventInfo, typeEventId, getDate));
                            } else {

                                String EventTitle = getJSonEventobj.getString("title");
                                String Eventtime = getJSonEventobj.getString("time");
                                items.add(new ScheduleItem(Eventtime, EventTitle,
                                        typeEventId, getDate));
                            }
                        }
                    }
                }

                data[counter] = items.toArray(new ScheduleItem[0]);
            }

        } catch (Exception e) {
            // TODO: handle exception
            Log.getStackTraceString(e);
        }
    }

  
    /**
     * The pager widget, which handles animation and allows swiping horizontally
     * to access previous and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        getJsonData();
        mPager = (ViewPager)findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                supportInvalidateOptionsMenu();
            }
        });
    }

    /**
     * A simple pager adapter that represents ScreenSlidePageFragment objects,
     * in sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return ScheduleSlideFragment.create(position, mPager, data[position]);
        }

        @Override
        public int getCount() {
            return totalPages;
        }
    }

}
