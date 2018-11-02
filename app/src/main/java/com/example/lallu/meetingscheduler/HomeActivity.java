package com.example.lallu.meetingscheduler;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
FloatingActionButton fa;
ListView meetingListview;
    public final static String EXTRA_MESSAGE = "MESSAGE";
    DBHelper mydbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mydbhelper=new DBHelper(this);
        ArrayList arrayList=mydbhelper.getAllCotacts();
        ArrayAdapter adapt=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);







        meetingListview=(ListView)findViewById(R.id.mylist_meetings);
        meetingListview.setAdapter(adapt);



        fa=(FloatingActionButton)findViewById(R.id.floatingActionButton3);
        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Your Text",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),AddMeetingActivity.class));
            }
        });
    }
}
