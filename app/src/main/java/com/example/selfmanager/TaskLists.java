package com.example.selfmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

import com.example.selfmanager.data.Constants;

import java.io.*;
import java.util.ArrayList;

public class TaskLists extends AppCompatActivity {

    private String date;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Intent intent = getIntent();
        date = intent.getStringExtra("data");
        ArrayList<String> taskLists = new ArrayList<>();
        try{
            file = new File(Constants.APP_DIRECTORY+"/TaskLists","TaskLists.csv");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                int j = text.indexOf(",");
                int k = text.length();
                taskLists.add(text.substring(j+1,k));
            }
            bufferedReader.close();
            for(int i = 0;i < 20;++i) {
                TextView textView = (TextView) findViewById(getResources().getIdentifier("task" + Integer.toString(i+1), "id", getPackageName()));
                textView.setText(taskLists.get(i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void edit(View view){
        finish();
        Intent intent = new Intent(getApplication(),EditTaskLists.class);
        intent.putExtra("data",date);
        startActivity(intent);
    }

    public void goSchedule(View view){
        finish();
        Intent intent = new Intent(getApplication(),Schedule.class);
        intent.putExtra("data",date);
        startActivity(intent);
    }

    public void goBack(View view){ finish(); }
}