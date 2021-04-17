package com.example.selfmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarview;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = Calendar.getInstance();
        calendarview = findViewById(R.id.calendarView);
        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int date) {
                calendar.set(year,month,date);
            }
        });
    }

    public void goSchedule(View view){
        Date currTime = calendar.getTime();
        String date = (1900+currTime.getYear())+"_"+(1+currTime.getMonth())+"_"+(currTime.getDate());
        Intent intent = new Intent(getApplication(),Schedule.class);
        intent.putExtra("data",date);
        startActivity(intent);
    }

    public void goTaskLists(View view){
        Date currTime = calendar.getTime();
        String date = (1900+currTime.getYear())+"_"+(1+currTime.getMonth())+"_"+(currTime.getDate());
        Intent intent = new Intent(getApplication(), TaskLists.class);
        intent.putExtra("data",date);
        startActivity(intent);
    }
}
