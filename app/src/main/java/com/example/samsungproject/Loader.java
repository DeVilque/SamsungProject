package com.example.samsungproject;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Loader extends AsyncTask<String, Integer, Loader.Lesson[]> {

    Context context;

    public Loader(Context c) {
        context = c;
    }

    @Override
    protected Lesson[] doInBackground(String... strings) {
        Retrofit r = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SheduleService ss = r.create(SheduleService.class);
        Call<Lesson[]> call = ss.getShedule(strings[0]);

        try {
            Response<Lesson[]> response = call.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Lesson[] lessons) {
        if (lessons != null) {
            SheduleDB db = new SheduleDB(context);
            db.deleteAll();
            SheduleActivity.data = new ArrayList<>();

            for (Lesson lesson : lessons) {
                db.insert(lesson);

                if (lesson.day_id == MainActivity.DAY_OF_WEEK - 1) {
                    HashMap<String, String> m = new HashMap<>();
                    m.put("name", lesson.name);
                    m.put("start_finish", lesson.start_time + "-" + lesson.finish_time);
                    SheduleActivity.data.add(m);
                }
            }

            synchronized (SheduleActivity.adapter) {
                SheduleActivity.adapter.notifyAll();
            }
        }
    }

    static class Lesson {
        int id, day_id;
        String name, start_time, finish_time;

        public Lesson(int id, String name, String start_time, String finish_time, int day_id) {
            this.id = id;
            this.day_id = day_id;
            this.name = name;
            this.start_time = start_time;
            this.finish_time = finish_time;
        }
    }

    interface SheduleService {
        @GET("/get_shedule.php")
        Call<Loader.Lesson[]> getShedule(@Query("table_name") String tableName);
    }
}
