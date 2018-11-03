package com.example.lallu.meetingscheduler;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
FloatingActionButton fa;
ListView meetingListview;
int selectedRecordPosition=-1;
    public final static String EXTRA_MESSAGE = "MESSAGE";
    DBHelper mydbhelper;
    ArrayAdapter adapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mydbhelper=new DBHelper(this);
        ArrayList arrayList=mydbhelper.getAllCotacts();
         adapt=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);







        meetingListview=(ListView)findViewById(R.id.mylist_meetings);
        meetingListview.setAdapter(adapt);

      // Action of Listview
        meetingListview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(),ViewMeetings.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });



        fa=(FloatingActionButton)findViewById(R.id.floatingActionButton3);
        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Your Text",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),AddMeetingActivity.class));
            }
        });


        meetingListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i> 0) {
                    selectedRecordPosition = i - 1;
                    showDialog();
                }
                return true;
            }
        });


    }

    @Override
    protected void onResume() {

        ArrayList arrayList=mydbhelper.getAllCotacts();
        adapt=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        meetingListview=(ListView)findViewById(R.id.mylist_meetings);
        meetingListview.setAdapter(adapt);
        super.onResume();

    }

    private void showDialog()
    {
        // Before deletion of the long pressed record, need to confirm with the user. So, build the AlartBox first
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // Set the appropriate message into it.
        alertDialogBuilder.setMessage("Are you Really want to delete the selected record ?");

        // Add a positive button and it's action. In our case action would be deletion of the data
        alertDialogBuilder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {
                            mydbhelper.deleteContact(selectedRecordPosition);
                            meetingListview.invalidateViews();

//                           // mydbhelper.delete(blockList.get(selectedRecordPosition));
//                                mydbhelper.deleteContact(meetingListview.get());
//                            // Removing the same from the List to remove from display as well
//                            blockList.remove(selectedRecordPosition);
//                            listview.invalidateViews();

                            // Reset the value of selectedRecordPosition
                            selectedRecordPosition = -1;
                           // populateNoRecordMsg();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        // Add a negative button and it's action. In our case, just hide the dialog box
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        // Now, create the Dialog and show it.
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



}
