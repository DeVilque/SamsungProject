package com.example.samsungproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SheduleActivity extends AppCompatActivity {

    ListView lvShedule;
    public static ArrayList<HashMap<String, String>> data;
    public static SimpleAdapter adapter;

    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule);

        lvShedule = findViewById(R.id.shedule);

        String l = "a";
        Log.d("mylog", "" + MainActivity.classN + l);
        switch (MainActivity.classL) {
            case 'А':
                l = "a";
                break;
            case 'Б':
                l = "b";
                break;
            case 'В':
                l = "v";
                break;
        }

        SheduleDB sheduleDB = new SheduleDB(getApplicationContext());


        ArrayList<Loader.Lesson> shedule = sheduleDB.getShedule();

        data = new ArrayList<>();

        for (Loader.Lesson lesson : shedule) {
            if (lesson.day_id == MainActivity.DAY_OF_WEEK) {
                HashMap<String, String> item = new HashMap<>();
                item.put("name", lesson.name);
                item.put("start_finish", lesson.start_time + "-" + lesson.finish_time);
                data.add(item);
            }
        }

        adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.item,
                new String[]{"name", "start_finish"}, new int[]{R.id.tvName, R.id.tvStartFinish});

        lvShedule.setAdapter(adapter);


        if (hasConnection(getApplicationContext())) {
            Loader nal = new Loader(getApplicationContext());
            nal.execute("" + MainActivity.classN + l);
        }
    }
}