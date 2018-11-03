package com.example.lallu.meetingscheduler;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class ViewMeetings extends AppCompatActivity {
    DBHelper dbselct;
    EditText edittitle;
    int Value;
    int id_To_Update = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meetings);
        edittitle=(EditText)findViewById(R.id.title_edt);
        dbselct = new DBHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Value = extras.getInt("id");
            Log.e("Bundle",""+Value);
            if(Value>0) {
                //means this is the view part not the add contact part.
                Cursor rs = dbselct.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();
                String nam = rs.getString(rs.getColumnIndex(DBHelper.KEY_TITLE));
                Log.e("title",nam);
                if (!rs.isClosed())  {
                    rs.close();
                }
            }
        }
    }
}
