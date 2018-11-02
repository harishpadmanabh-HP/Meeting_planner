package com.example.lallu.meetingscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TAG="RESULT";
    public static final String DATABASE_NAME="MyMeetings.db";
    public static final String TABLE_NAME="SchedledMeetings";
    public static final String KEY_ID="Meeting_Id";
    public static final String KEY_DATE="Meeting_Date";
    public static final String KEY_TITLE="Meeting_title";
    public static final String KEY_AGENDA="Meeting_Agenda";
    public static final String KEY_SCHEDULED_AT="Meeting_Scheduled";
    public static final String KEY_TIME_START="Start_Time";
    public static final String KEY_TIME_ENDS="End_Time";
    public static final String KEY_CONTACTS="Contacts";
    public static final String KEY_LOCATION="Meeting_Location";
    private HashMap MeetingData;




    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
        Log.e(TAG,"dbcreated");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table SchedledMeetings " +
                        "(Meeting_Id integer primary key AUTOINCREMENT,Meeting_Date text, Meeting_title text" +
                        ",Meeting_Agenda text,Meeting_Scheduled text, Start_Time text,End_Time text,Contacts text,Meeting_Location text)"
        );
        Log.e(TAG,"Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query;
        query = "DROP TABLE IF EXISTS SchedledMeetings";
        db.execSQL(query);
        onCreate(db);
        Log.e(TAG,"Table Exist");



    }
    public boolean insertMeetings(String meetdate,String meetintitle,String meetagenda,String scheduleat,String starttime,String endtime,String phn,String location){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put("Meeting_Date",meetdate);
        contentValues.put("Meeting_title",meetintitle);
        contentValues.put("Meeting_Agenda",meetagenda);
        contentValues.put("Meeting_Scheduled",scheduleat);
        contentValues.put("Start_Time",starttime);
        contentValues.put("End_Time",endtime);
        contentValues.put("Contacts",phn);
        contentValues.put("Meeting_Location",location);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        Log.e(TAG,"Inserted");
        return true;


    }
    public Cursor getData(int addeddate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from SchedledMeetings where Meeting_Date="+addeddate+"", null );
        return res;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }


//    public boolean updatemeeting (Integer id, String meetingtitle, String meetingdetails, String date, String time,String location) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("meetingtitle", meetingtitle);
//        contentValues.put("meetingdetails", meetingdetails);
//        contentValues.put("date", date);
//        contentValues.put("time", time);
//        contentValues.put("location", location);
//        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
//        return true;
//    }



    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("SchedledMeetings",
                "Meeting_Id= ? ",
                new String[] { Integer.toString(id) });
    }


    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from SchedledMeetings", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(KEY_TITLE)));
            res.moveToNext();
        }
        return array_list;
    }






}
