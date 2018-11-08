package com.example.lallu.meetingscheduler;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllMeetings extends AppCompatActivity {
    ArrayList<String> meetinghead;
    private int storagepermissioncode=1;

    ArrayList<String> meetingdate;
    ListView list;
    DBHelper mydbhelper;
    FloatingActionButton addmeetingfab;
    com.getbase.floatingactionbutton.FloatingActionButton weekfab,tomorrowfab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_meetings);
        mydbhelper=new DBHelper(this);

        addmeetingfab=findViewById(R.id.fab_add_meeting);
        addmeetingfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //.......
                if(ContextCompat.checkSelfPermission(AllMeetings.this,
                        Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(AllMeetings.this, "ALREADY GRANTED PERMISSION", Toast.LENGTH_SHORT).show();
                }
                else{
                    requeststroagepermission();
                   // startActivity(new Intent(AllMeetings.this,AddMeetingActivity.class));

                }//.......
                startActivity(new Intent(AllMeetings.this,AddMeetingActivity.class));
            }
        });

        weekfab=findViewById(R.id.fab_week);
        weekfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllMeetings.this,MeetingInWeek.class));
            }
        });
        tomorrowfab=findViewById(R.id.fab_tomorrow);
        tomorrowfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllMeetings.this,TomarrowActivity.class));
            }
        });
        meetinghead=mydbhelper.getAllCotacts();
        meetingdate=mydbhelper.getAllDates();
//        meetinghead.add("aaaaaa");
//        meetinghead.add("bbbbbb");
//        meetinghead.add("ccccccc");
//        meetingdate.add("12/04/2018");
//        meetingdate.add("34/67/2018");
//        meetingdate.add("78/09/1234");
        list=(ListView)findViewById(R.id.hosplist);
        adapter ad=new adapter();
        list.setAdapter(ad);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String itm=((TextView)view.findViewById(R.id.txtview1)).getText().toString();

                Toast.makeText(getApplicationContext(), itm,Toast.LENGTH_SHORT).show();
                // View v1 = adapterView.getChildAt(position);

            }
        });

    }
    private void requeststroagepermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)){
            new AlertDialog.Builder(this)
                    .setTitle("PERMISSION NEEDED")
                    .setMessage("This is needed for Recording voice")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(AllMeetings.this, new String[] {Manifest.permission.RECORD_AUDIO},storagepermissioncode);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_CONTACTS},storagepermissioncode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==storagepermissioncode){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "DENIED PERMISSION", Toast.LENGTH_SHORT).show();
        }
    }
    class adapter extends BaseAdapter{
        LayoutInflater inflater;

        @Override
        public int getCount() {
            return meetingdate.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            inflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.viewmeetcustomlistviewdesign,null);
            ViewHolder holder=new ViewHolder();
            holder.Hna=(TextView)view.findViewById(R.id.txtview1);
            holder.Hna.setText(meetinghead.get(i));
            holder.Pla=(TextView)view.findViewById(R.id.txtview2);
            holder.Pla.setText(meetingdate.get(i));
            return view;
        }
    }
    class ViewHolder{
        TextView Hna;
        TextView Pla;



    }
}
