package com.example.selfmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import android.widget.EditText;

import java.io.*;

import com.example.selfmanager.action.WriteTaskLists;
import com.example.selfmanager.data.*;
import com.example.selfmanager.action.Message;

public class EditTaskLists extends AppCompatActivity {

    private File file;
    private String date;
    private TaskListsData taskListsData = new TaskListsData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_lists);
        Intent intent = getIntent();
        date = intent.getStringExtra("data");
        String name = "TaskLists.csv";
        File dir = new File(Constants.APP_DIRECTORY+"/TaskLists");
        file = new File(dir,name);
        if(!dir.exists()){
            try{
                dir.mkdirs();
                file.createNewFile();
            }catch(Exception e){
                Message msg = new Message(this,Gravity.CENTER,0,0,"Fail to prepare for edit. Please press exit.",Toast.LENGTH_LONG);
                msg.show();
            }
        }
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String text;
            for(int i=0;;++i){
                text = bufferedReader.readLine();
                if(text==null)
                    break;
                EditText editText = (EditText)findViewById(getResources().getIdentifier("task"+Integer.toString(i+1),"id",getPackageName()));
                editText.setText(text.substring(text.indexOf(",")+1,text.length()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void save(View view){
        for(int i = 1;i <= 20;++i){
            taskListsData.taskLists.add(((EditText) findViewById(getResources().getIdentifier("task" + i, "id", getPackageName()))).getText().toString());
        }
        WriteTaskLists wt = new WriteTaskLists();
        wt.writeTasks(taskListsData,getApplication());
    }

    public void reset(View view){
        for(int i=0;i<20;++i){
            EditText editText = (EditText)findViewById(getResources().getIdentifier("task"+Integer.toString(i+1),"id",getPackageName()));
            editText.setText(null);
        }
    }

    public void exit(View view){
        finish();
        Intent intent = new Intent(getApplication(),TaskLists.class);
        intent.putExtra("data",date);
        startActivity(intent);
    }
}
