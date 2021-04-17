package com.example.selfmanager.action;

import android.widget.Toast;
import android.content.Context;

public class Message {
    private Context context;
    private int gravity,x,y,length;
    private String text;
    public Message(Context context,int gravity,int x,int y,String text,int length){
        this.context = context;
        this.gravity = gravity;
        this.x = x;
        this.y = y;
        this.length = length;
        this.text = text;
    }

    public void show(){
        Toast toast = Toast.makeText(this.context,this.text,this.length);
        toast.setGravity(this.gravity,this.x,this.y);
        toast.show();
    }
}
