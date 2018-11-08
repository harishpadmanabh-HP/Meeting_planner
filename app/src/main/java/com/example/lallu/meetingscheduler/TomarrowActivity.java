package com.example.lallu.meetingscheduler;

import android.annotation.TargetApi;
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

public class TomarrowActivity extends AppCompatActivity {
    ListView meetingListview;
    String itemValue;
    public final static String EXTRA_MESSAGE = "MESSAGE";
    DBHelper mydbhelper;
    ArrayAdapter adapt;

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomarrow);
        mydbhelper=new DBHelper(this);
        ArrayList arrayList=mydbhelper.getTommorrowsMeeting();

        adapt=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        meetingListview=(ListView)findViewById(R.id.meetingtomorrow);
        meetingListview.setAdapter(adapt);
        meetingListview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                itemValue=(String)meetingListview.getItemAtPosition(arg2);
                //  Log.e("Selected Item",itemValue);
                // TODO Auto-generated method stub
                //  int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                //   dataBundle.putInt("id", id_To_Search);
                dataBundle.putString("title",itemValue);

                Intent intent = new Intent(getApplicationContext(),ViewMeetings.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });




    }
}
