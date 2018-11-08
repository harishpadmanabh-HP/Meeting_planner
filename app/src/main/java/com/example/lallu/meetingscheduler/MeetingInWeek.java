package com.example.lallu.meetingscheduler;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MeetingInWeek extends AppCompatActivity {
    String str;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_in_week);
        DBHelper dbh=new DBHelper(this);
        final ListView list=(ListView)findViewById(R.id.meetinglist);
        ArrayAdapter adapt;
        ArrayList arrayList=dbh.getAllMeetings();

        adapt=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);


        list.setAdapter(adapt);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                str=(String)list.getItemAtPosition(arg2);
                //  Log.e("Selected Item",itemValue);
                // TODO Auto-generated method stub
                //  int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                //   dataBundle.putInt("id", id_To_Search);
                dataBundle.putString("title",str);

                Intent intent = new Intent(getApplicationContext(),ViewMeetings.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

    }
}
