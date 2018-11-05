package com.example.lallu.meetingscheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class UpdateActivity extends AppCompatActivity {
    Calendar myCalendar=Calendar.getInstance();
    private Uri uriContact;
    private String contactID,Contactnum,contactName1;
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    EditText update_title,update_agenda,update_Schedate,update_start,update_end,update_location,update_phn;
String bundletitle,bundleagenda,bundledate,bundleschedate,bundlestarttime,bundleendtime,bundlelocation,bundlephn;
DBHelper dbupdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        update_title=(EditText)findViewById(R.id.title_edt);
        update_agenda=(EditText)findViewById(R.id.agenda_edt);
        update_Schedate=(EditText)findViewById(R.id.meeting_date_edittext);
        update_start=(EditText)findViewById(R.id.start_time_edt);
        update_end=(EditText)findViewById(R.id.End_time_edt);
        update_phn=(EditText)findViewById(R.id.phone_number_edt);
        update_location=(EditText)findViewById(R.id.location_edt);
        dbupdate=new DBHelper(this);

        Bundle obj=getIntent().getExtras();
        bundletitle=obj.getString("key_title");
        bundleagenda=obj.getString("key_agenda");
        bundledate=obj.getString("key_date");
        bundleschedate=obj.getString("key_scheduled");
        bundlestarttime=obj.getString("key_start");
        bundleendtime=obj.getString("Key_end");
        bundlephn=obj.getString("key_phn");
        bundlelocation=obj.getString("key_loc");

        update_title.setText(bundletitle);
        update_title.setEnabled(false);
        update_agenda.setText(bundleagenda);
//        update_agenda.setEnabled(false);
        update_Schedate.setText(bundleschedate);
//        update_Schedate.setEnabled(false);
        update_start.setText(bundlestarttime);
//        update_start.setEnabled(false);
        update_end.setText(bundleendtime);
//        update_end.setEnabled(false);
        update_location.setText(bundlelocation);
//        update_location.setEnabled(false);
        update_phn.setText(bundlephn);
//        update_phn.setEnabled(false);
    }

    public void UpdateData(View view) {
        bundletitle=update_title.getText().toString();
        bundleagenda=update_agenda.getText().toString();
        bundleschedate=update_Schedate.getText().toString();
        bundlestarttime=update_start.getText().toString();
        bundleendtime=update_end.getText().toString();
        bundlephn=update_phn.getText().toString();
        bundlelocation=update_location.getText().toString();
        Toast.makeText(getApplicationContext(),bundletitle,Toast.LENGTH_SHORT).show();
       if (dbupdate.updatemeeting(bundledate, bundletitle,bundleagenda,bundleschedate, bundlestarttime, bundleendtime,bundlephn,bundlelocation)
               ==true){
           Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
           finish();
       }
        // Add date picker
        // To choose the meeting Date
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        update_Schedate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(UpdateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }

    public void SelectFromContacts(View view) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            retrieveContactName();
            retrieveContactNumber();
            //   blockListView.setAdapter(dap);
        }
    }

    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Contactnum=contactNumber.replaceAll("\\s+","");
            if(Contactnum.length()==10){
//                countryedt.setText("91");
//                phnedt.setText(numBlock);
//
            }
            else if(Contactnum.length()>10){
                // countryedt.setText("");
                update_phn.setText(Contactnum);}
            else{
                Toast.makeText(getApplicationContext(),"No Number Found",Toast.LENGTH_LONG).show();
            }
        }

        cursorPhone.close();

        Log.d(TAG, "Contact Phone Number: " + contactNumber);
    }

    private void retrieveContactName() {



        // querying contact data store
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName1 = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))+":";
//            if(enterNum.getText().length()>2){
//                blockList_contactName.add(contactName);}
//            else{
//                Toast.makeText(getApplicationContext(),"No Number Found",Toast.LENGTH_LONG).show();
//            }
        }

        cursor.close();

        Log.d(TAG, "Contact Name: " + contactName1);
        Log.e("Contactname:",contactName1);

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        update_Schedate.setText(sdf.format(myCalendar.getTime()));
    }

}
