package com.example.selfmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selfmanager.data.Constants;
import com.example.selfmanager.action.Message;

import java.io.*;

public class Schedule extends AppCompatActivity {

    private String date;
    private File schFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Intent intent = getIntent();
        date = intent.getStringExtra("data");
        TextView dateText = (TextView)findViewById(R.id.date);
        dateText.setText(date);
        schFile = new File(Constants.APP_DIRECTORY+"/Schedule",date+".csv");
        if(schFile.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(schFile));
                String text;
                for (int i = 0; i < Constants.HOUR; ++i) {
                    text = bufferedReader.readLine();
                    TextView textView = (TextView) findViewById(getResources().getIdentifier("schedule" + (i + 1), "id", getPackageName()));
                    textView.setText(text.substring(text.indexOf(",") + 1));
                }
            } catch (Exception e) {
                Message msg = new Message(this, Gravity.CENTER, 0, 0, "Fail to read schdule.", Toast.LENGTH_LONG);
                msg.show();
            }
        }
    }

    public void goTaskLists(View view){
        finish();
        Intent intent = new Intent(getApplication(), TaskLists.class);
        intent.putExtra("data",date);
        startActivity(intent);
    }

    public void edit(View view){
        finish();
        Intent intent = new Intent(getApplication(),EditSchedule.class);
        intent.putExtra("data",date);
        startActivity(intent);
    }

    public void goBack(View view){
        finish();
    }
}
