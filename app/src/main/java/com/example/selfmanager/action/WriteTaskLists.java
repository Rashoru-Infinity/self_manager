package com.example.selfmanager.action;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.io.*;

import com.example.selfmanager.data.Constants;
import com.example.selfmanager.data.TaskListsData;

public class WriteTaskLists {
    private String directory = Constants.APP_DIRECTORY+"/TaskLists";
    private String fileName = "TaskLists.csv";
    private File file;

    public void writeTasks(TaskListsData taskListsData,Context context){
        file = new File(directory,fileName);
        if(!file.exists()){
            try{
                file.mkdirs();
                file.createNewFile();
            }catch(Exception e){
                Message msg = new Message(context, Gravity.CENTER,0,0,"Fail to save.", Toast.LENGTH_LONG);
                msg.show();
            }
        }else{
            try{
                file.delete();
                file.createNewFile();
            }catch(Exception e){
                Message msg = new Message(context, Gravity.CENTER,0,0,"Fail to initialize TaskLists.", Toast.LENGTH_LONG);
                msg.show();
            }
        }
        try{
            FileWriter filewriter = new FileWriter(file,true);
            for(int i = 0;i< taskListsData.taskLists.size();++i){
                filewriter.write((i+1)+",");
                filewriter.write(taskListsData.taskLists.get(i));
                filewriter.write("\n");
            }
            filewriter.close();
            Message msg = new Message(context,Gravity.CENTER,0,0,"Success in saving tasklists.", Toast.LENGTH_LONG);
            msg.show();
        }catch(Exception e){
            Message msg = new Message(context,Gravity.CENTER,0,0,"Fail to write TaskLists.",Toast.LENGTH_LONG);
            msg.show();
        }
    }
}
