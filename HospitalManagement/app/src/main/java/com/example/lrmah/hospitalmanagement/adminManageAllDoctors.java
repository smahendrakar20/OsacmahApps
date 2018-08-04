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
import com.example.lrmah.hospitalmanagement.Data.doctorDbHelper;
import com.example.lrmah.hospitalmanagement.Data.HospitalContract.*;

import java.net.URI;
import java.util.List;

import static android.R.attr.data;

public class adminManageAllDoctors extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{

    public static final int DOCTORLIST_LOADER=0;
    doctorCursorAdapter ad;
    //Cursor cursor;

    ListView lv;

    String[] projection = {
            doctorsEntry.column_id,
            doctorsEntry.column_username,
            doctorsEntry.column_available,
            doctorsEntry.column_field,
            doctorsEntry.column_name,
            doctorsEntry.column_patient};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_all_doctors);

        //cursor = getContentResolver().query(doctorsEntry.CONTENT_URI, projection, null, null, null);

        //THIS METHOD INITIALISES THE LOADER AND CALLS THE ONCREATELOADER METHOD
        getSupportLoaderManager().initLoader(DOCTORLIST_LOADER, null, this);

        lv = (ListView) findViewById(R.id.doctorListViewXml);
        ad = new doctorCursorAdapter(this, null);//DoctorCursorAdapter is a class i defined which helps to create a adapter
        lv.setAdapter(ad);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),doctors_delete_edit.class);
                Uri currentDoctorUri= ContentUris.withAppendedId(doctorsEntry.CONTENT_URI,id);
                intent.putExtra("rem","");
                intent.setData(currentDoctorUri);
                startActivity(intent);
            }
        });
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

//THE FOLLOWING THREE LINES ARE USED TO CREATE A LIST VIEW AND TO INITIALISE A CURSOR ADAPTER

       //THIS RETURNS A CURSOR LOADER
        return new CursorLoader(this,doctorsEntry.CONTENT_URI, projection,null,null,null);



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
