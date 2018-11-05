package com.example.lallu.meetingscheduler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MeetingInWeek extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_in_week);
        DBHelper dbh=new DBHelper(this);
        dbh.getDataweek("10/11/2018");
    }
}
