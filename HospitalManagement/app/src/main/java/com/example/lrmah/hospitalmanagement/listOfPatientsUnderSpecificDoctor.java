package com.example.lrmah.hospitalmanagement;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.lrmah.hospitalmanagement.Data.HospitalContract;
import com.example.lrmah.hospitalmanagement.Data.patientDbHelper;

import java.util.List;

public class listOfPatientsUnderSpecificDoctor extends AppCompatActivity {
ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_patients_under_specific_doctor);
    Intent i=getIntent();
        String temp=i.getExtras().getString("list");
        String tempArray[]=temp.split(",");

        lv=(ListView) findViewById(R.id.patientUnderDocListView);
        ListAdapter la=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tempArray);
        lv.setAdapter(la);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedPatient=String.valueOf(parent.getItemAtPosition(position));
                Intent i1=new Intent(getApplicationContext(),givePrescription.class);
                i1.setData(getUriPatient(selectedPatient));
                startActivity(i1);
            }

        });

    }
    public Uri getUriPatient(String name) {
        int temp = 0;
        Cursor cursor = null;

        patientDbHelper helper = new patientDbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM allPatients WHERE NameOfPatient=?", new String[]{name + ""});

        if (cursor.getCount()>0)
        {cursor.moveToFirst();
            temp = cursor.getInt(cursor.getColumnIndex("_id"));
            cursor.close();}
        return ContentUris.withAppendedId(HospitalContract.patientsEntry.CONTENT_URI, temp);

    }
}
