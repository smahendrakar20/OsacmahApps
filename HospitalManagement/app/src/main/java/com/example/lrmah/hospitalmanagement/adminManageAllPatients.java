package com.example.lrmah.hospitalmanagement;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lrmah.hospitalmanagement.Data.HospitalContract;
import com.example.lrmah.hospitalmanagement.Data.patientDbHelper;
import com.example.lrmah.hospitalmanagement.Data.HospitalContract.*;

import java.net.URI;
import java.util.List;

import static android.R.attr.data;

public class adminManageAllPatients extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{

    public static final int patientLIST_LOADER=0;
    patientCursorAdapter ad;
    //Cursor cursor;

    ListView lv;

    String[] projection = {
            patientsEntry.column_id,
            patientsEntry.column_username,
            patientsEntry.column_phoneNo,
            patientsEntry.column_in_out_patient,
            patientsEntry.column_name,
            patientsEntry.column_prescription,
            patientsEntry.column_doctorWho,
            patientsEntry.column_password
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_all_patients);

        //cursor = getContentResolver().query(patientsEntry.CONTENT_URI, projection, null, null, null);

        //THIS METHOD INITIALISES THE LOADER AND CALLS THE ONCREATELOADER METHOD
        getSupportLoaderManager().initLoader(patientLIST_LOADER, null, this);

        lv = (ListView) findViewById(R.id.patientListViewXml);
        ad = new patientCursorAdapter(this, null);//patientCursorAdapter is a class i defined which helps to create a adapter
        lv.setAdapter(ad);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),patient_delete_edit.class);
                Uri currentpatientUri= ContentUris.withAppendedId(patientsEntry.CONTENT_URI,id);
                intent.setData(currentpatientUri);
                intent.putExtra("rem","dont");
                startActivity(intent);
            }
        });
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

//THE FOLLOWING THREE LINES ARE USED TO CREATE A LIST VIEW AND TO INITIALISE A CURSOR ADAPTER

        //THIS RETURNS A CURSOR LOADER
        return new CursorLoader(this,patientsEntry.CONTENT_URI, projection,null,null,null);



    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //here we are actually proving a cursor to the adapter which we created above
        ad.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ad.swapCursor(null);
    }
}
