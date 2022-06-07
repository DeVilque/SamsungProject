package com.example.samsungproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView tvN, tvL;
    Button btnIncrN, btnIncrL, btnDecrN, btnDecrL, btnContinue;
    public static int classN;
    public static char classL;
    public static int DAY_OF_WEEK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar c = Calendar.getInstance();
        DAY_OF_WEEK = c.get(Calendar.DAY_OF_WEEK) - 1;

        SharedPreferences sp = getSharedPreferences("myPrefs", MODE_PRIVATE);
        classN = sp.getInt("N", 7);
        classL = sp.getString("L", "А").toCharArray()[0];

        tvN = findViewById(R.id.tvN);
        tvL = findViewById(R.id.tvL);
        btnIncrN = findViewById(R.id.buttonIncrN);
        btnIncrL = findViewById(R.id.buttonIncrL);
        btnDecrN = findViewById(R.id.buttonDecrN);
        btnDecrL = findViewById(R.id.buttonDecrL);
        btnContinue = findViewById(R.id.btnContinue);

        tvN.setText("" + classN);
        tvL.setText("" + classL);

        btnIncrN.setOnClickListener((v) -> {
            if (7 <= classN && classN < 11) classN++;
            tvN.setText("" + classN);
        });

        btnDecrN.setOnClickListener((v) -> {
            if (7 < classN && classN <= 11) classN--;
            tvN.setText("" + classN);
        });

        btnIncrL.setOnClickListener((v) -> {
            if ('А' <= classL && classL < 'В') classL++;
            tvL.setText("" + classL);
        });

        btnDecrL.setOnClickListener((v) -> {
            if ('А' < classL && classL <= 'В') classL--;
            tvL.setText("" + classL);
        });

        btnContinue.setOnClickListener((v) -> {
            SharedPreferences.Editor e = sp.edit();
            e.putInt("N", classN).apply();
            e.putString("L", "" + classL).apply();

            Intent intent = new Intent(getApplicationContext(), SheduleActivity.class);
            startActivity(intent);
        });
    }
}