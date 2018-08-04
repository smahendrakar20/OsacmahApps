package com.example.lrmah.hospitalmanagement;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lrmah.hospitalmanagement.Data.HospitalContract.doctorsEntry;

import com.example.lrmah.hospitalmanagement.Data.HospitalContract;

import static android.R.transition.move;

public class doctors_delete_edit extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    Button saveDoc;
    Button delDoc;
    private Uri uri;
    EditText docUsername;
    EditText patientName;
    EditText fieldOfDoc;
    EditText nameOfDoc;
    EditText pass;
    CheckBox available;

    String p,q,r,s,t;
    int b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_delete_edit);
        Intent i=getIntent();

        uri= i.getData();

        String temp="remove";

        saveDoc=(Button) findViewById(R.id.saveDocButtonXml);
        delDoc=(Button) findViewById((R.id.delDocButtonXml));

        pass=(EditText) findViewById(R.id.passwordXml);
        docUsername = (EditText) findViewById(R.id.userNameXml);
        nameOfDoc=(EditText) findViewById(R.id.nameXml);
        patientName=(EditText) findViewById(R.id.patientNameXml);
        fieldOfDoc=(EditText) findViewById(R.id.fieldXml);
        available=(CheckBox) findViewById(R.id.availabilityCheckBox);
String temp1=i.getExtras().getString("rem");
        if(temp.equals(temp1))
        {
          delDoc.setVisibility(View.INVISIBLE);
        }

        delDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rowsDeleted=getContentResolver().delete(uri,null,null);
                if (rowsDeleted == 0) {
                    // If no rows were affected, then there was an error with the update.
                    Toast.makeText(getApplicationContext(),"delete failed",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the update was successful and we can display a toast.
                    Toast.makeText(getApplicationContext(), "delete success",
                            Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });



        saveDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = docUsername.getText().toString();
                q= nameOfDoc.getText().toString();
                r=fieldOfDoc.getText().toString();
                s= patientName.getText().toString();
                t=pass.getText().toString();
                if(available.isChecked()){b=1;}else{b=0;};

                updateDoctor(p,q,r,s,t,b);
            }
        });
        getSupportLoaderManager().initLoader(0,null,this);
    }





    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                doctorsEntry.column_id,

                doctorsEntry.column_username,
                doctorsEntry.column_available,
                doctorsEntry.column_field,
                doctorsEntry.column_name,
                doctorsEntry.column_patient,
                doctorsEntry.column_password};

        return new CursorLoader(this, uri, projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if( cursor.moveToFirst())
        {
            Log.e("TAG","couldnt move to first");
            int unColumnIndex=cursor.getColumnIndex(doctorsEntry.column_username);
            int availableColumnIndex=cursor.getColumnIndex(doctorsEntry.column_available);
            int fieldColumnIndex=cursor.getColumnIndex(doctorsEntry.column_field);
            int nameColumnIndex=cursor.getColumnIndex(doctorsEntry.column_name);
            int patientColumnIndex=cursor.getColumnIndex(doctorsEntry.column_patient);
            int passwordColumnIndex=cursor.getColumnIndex(doctorsEntry.column_password);

            String username=cursor.getString(unColumnIndex);
            String field=cursor.getString(fieldColumnIndex);
            int availableValue=cursor.getInt(availableColumnIndex);
            String name=cursor.getString(nameColumnIndex);
            String patient=cursor.getString(patientColumnIndex);
            String passwordTemp=cursor.getString(passwordColumnIndex);

            docUsername.setText(username);
            nameOfDoc.setText(name);
            patientName.setText(patient);
            fieldOfDoc.setText(field);
            pass.setText(passwordTemp);

            if(availableValue==1)
            {available.setChecked(true);}
            else{available.setChecked(false);}
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        docUsername.setText("");
        nameOfDoc.setText("");
        patientName.setText("");
        fieldOfDoc.setText("");
        pass.setText("");
    }
    public void updateDoctor(String UserName,String name,String field,String patient,String password,int available) {

        //Inserting

        ContentValues values = new ContentValues();
        values.put(doctorsEntry.column_username,UserName);
        values.put(doctorsEntry.column_name, name);
        values.put(doctorsEntry.column_field, field);
        values.put(doctorsEntry.column_available, available);
        values.put(doctorsEntry.column_patient, patient);
        values.put(doctorsEntry.column_password,password);


        int rowsAffected=getContentResolver().update(uri,values,null,null);
        if (rowsAffected == 0) {
            // If no rows were affected, then there was an error with the update.
            Toast.makeText(this,"update failed",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the update was successful and we can display a toast.
            Toast.makeText(this, "update success",
                    Toast.LENGTH_SHORT).show();
        }
        finish();

    }


}
