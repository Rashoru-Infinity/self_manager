package com.example.selfmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.EditText;

import java.io.*;

import com.example.selfmanager.data.Constants;
import com.example.selfmanager.action.Message;
import com.example.selfmanager.data.TaskListsData;

public class EditSchedule extends AppCompatActivity {

    private File schedule;
    private File taskLists;
    private String date;
    private TaskListsData referLists = new TaskListsData();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
        Intent intent = getIntent();
        date = intent.getStringExtra("data");
        TextView textView = (TextView)findViewById(R.id.date);
        textView.setText(date);
        String name = date+".csv";
        File schDir = new File(Constants.APP_DIRECTORY+"/Schedule");
        schedule = new File(schDir,name);
        if(!schDir.exists()){
            try{
               System.out.println(schDir.mkdirs());
               schedule.createNewFile();
            }catch(Exception e){
                Message msg = new Message(this,Gravity.CENTER,0,0,"Fail to prepare for edit. Please press exit.",Toast.LENGTH_LONG);
                msg.show();
            }
        }else{
            try{
                BufferedReader bufferedReader = new BufferedReader(new FileReader(schedule));
                String text;
                for(int i=0;i<Constants.HOUR;++i) {
                    text = bufferedReader.readLine();
                    text = text.substring(text.indexOf(",")+1);
                    EditText editText = (EditText)findViewById(getResources().getIdentifier("schedule"+(i+1),"id",getPackageName()));
                    editText.setText(text);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        File tskDir = new File(Constants.APP_DIRECTORY+"/TaskLists");
        name = "TaskLists.csv";
        taskLists = new File(tskDir,name);
        if(!tskDir.exists()){
            try{
                System.out.println(tskDir.mkdirs());
                taskLists.createNewFile();
            }catch(Exception e){
                Message msg = new Message(this,Gravity.CENTER,0,0,"Fail to read TaskLists.",Toast.LENGTH_LONG);
                msg.show();
            }
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(taskLists));
            String text;
            while((text = bufferedReader.readLine())!=null){
                int j = text.indexOf(",");
                int k = text.length();
                referLists.taskLists.add(text.substring(j+1,k));
            }
        }catch(Exception e){
            Message msg = new Message(this,Gravity.CENTER,0,0,"Fail to read TaskLists.",Toast.LENGTH_LONG);
            msg.show();
        }
    }

    public void save(View view){
        try {
            FileWriter filewriter = new FileWriter(schedule);
            int strIndex = -1;
            for(int i=0;i<Constants.HOUR;++i){
                EditText editText = (EditText)findViewById(getResources().getIdentifier("schedule"+(i+1),"id",getPackageName()));
                String text = editText.getText().toString();
                TaskListsData lists = new TaskListsData();
                filewriter.write(Integer.toString(i));
                do {
                    try {
                        filewriter.write(",");
                    } catch (Exception e) {
                        Message msg = new Message(this, Gravity.CENTER, 0, 0, "Fail to divide schedule.", Toast.LENGTH_SHORT);
                        msg.show();
                    }

                    int j = text.indexOf(",", strIndex + 1);
                    if (j >= 0)
                        lists.taskLists.add(text.substring(strIndex + 1, j));
                    else
                        lists.taskLists.add(text.substring(strIndex + 1));
                    strIndex = j;
                    String material = null;
                    if (lists.taskLists.get(lists.taskLists.size() - 1).indexOf("=") == 0) {
                        int taskId;
                        try {
                            taskId = Integer.parseInt(lists.taskLists.get(lists.taskLists.size() - 1).substring(1));
                            try {
                                material = referLists.taskLists.get(taskId - 1);
                            } catch (IndexOutOfBoundsException e) {
                                material = lists.taskLists.get(lists.taskLists.size() - 1);
                            }
                        } catch (NumberFormatException e) {
                            material = lists.taskLists.get(lists.taskLists.size() - 1);
                        }
                    } else {
                        material = lists.taskLists.get(lists.taskLists.size() - 1);
                    }
                    try {
                        filewriter.write(material);
                    } catch (Exception e) {
                        Message msg = new Message(this, Gravity.CENTER, 0, 0, "Fail to save schedule.", Toast.LENGTH_SHORT);
                        msg.show();
                    }
                }while(strIndex!=-1);
                try{
                    filewriter.write("\n");
                }catch(Exception e){
                    Message msg = new Message(this,Gravity.CENTER,0,0,"Fail to divide schedule.",Toast.LENGTH_SHORT);
                    msg.show();
                }
            }
            filewriter.close();
            Message msg = new Message(this,Gravity.CENTER,0,0,"Success in saving.",Toast.LENGTH_LONG);
            msg.show();
        }catch(Exception e){
            Message msg = new Message(this,Gravity.CENTER,0,0,"Fail to save.",Toast.LENGTH_LONG);
            msg.show();
        }
    }

    public void reset(View view){
        for(int i=0;i<Constants.HOUR;++i){
            EditText editText = (EditText)findViewById(getResources().getIdentifier("schedule"+(i+1),"id",getPackageName()));
            editText.setText(null);
        }
    }

    public void exit(View view){
        finish();
        Intent intent = new Intent(getApplication(),Schedule.class);
        intent.putExtra("data",date);
        startActivity(intent);
    }
}
