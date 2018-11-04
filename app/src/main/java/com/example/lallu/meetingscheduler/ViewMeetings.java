package com.example.lallu.meetingscheduler;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewMeetings extends AppCompatActivity {
    DBHelper dbselct;
  //  EditText edittitle;
   String Value;
    int id_To_Update = 0;
    TextView titletv,datetv,agendatv,meetingadatetv,starttv,endtv,phntv,loctv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meetings);
        //edittitle=(EditText)findViewById(R.id.title_edt);

        titletv=(TextView)findViewById(R.id.table_title);
        datetv=(TextView)findViewById(R.id.table_date);
        agendatv=(TextView)findViewById(R.id.table_agenda);
        meetingadatetv=(TextView)findViewById(R.id.table_meetdate);
        starttv=(TextView)findViewById(R.id.table_starttime);
        endtv=(TextView)findViewById(R.id.table_endtime);
        phntv=(TextView)findViewById(R.id.table_phn);
        loctv=(TextView)findViewById(R.id.table_location);
        dbselct = new DBHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Value = extras.getString("title");
            Log.e("Bundle",""+Value);
            if(Value!=null) {
                //means this is the view part not the add contact part.
                Cursor rs = dbselct.getData(Value);
               // id_To_Update = Value;
                rs.moveToFirst();
                int id=rs.getColumnIndex(DBHelper.KEY_ID);
                Log.e("KEy",""+id);
                String meeting_tittle = rs.getString(rs.getColumnIndex(DBHelper.KEY_TITLE));
                String meeting_Date=rs.getString(rs.getColumnIndex(DBHelper.KEY_DATE));
                String meeting_Agennda=rs.getString(rs.getColumnIndex(DBHelper.KEY_AGENDA));
                String meeting_scheduledDate=rs.getString(rs.getColumnIndex(DBHelper.KEY_SCHEDULED_AT));
                String meeting_start=rs.getString(rs.getColumnIndex(DBHelper.KEY_TIME_START));
                String meeting_ends=rs.getString(rs.getColumnIndex(DBHelper.KEY_TIME_ENDS));
                String phone_ends=rs.getString(rs.getColumnIndex(DBHelper.KEY_CONTACTS));
                String location=rs.getString(rs.getColumnIndex(DBHelper.KEY_LOCATION));



                /// Set to th e data
                titletv.setText(meeting_tittle);
                datetv.setText(meeting_Date);
                agendatv.setText(meeting_Agennda);
                meetingadatetv.setText(meeting_scheduledDate);
                starttv.setText(meeting_start);
                endtv.setText(meeting_ends);
                phntv.setText(phone_ends);
                loctv.setText(location);



                Log.e("title",meeting_tittle+meeting_Date+meeting_Agennda+meeting_scheduledDate+meeting_start+meeting_ends+phone_ends+location);
                if (!rs.isClosed())  {
                    rs.close();
                }
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.Edit_Contact:
              //  startActivity(new Intent());
                Toast.makeText(getApplicationContext(),"You Selected Edit option",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
