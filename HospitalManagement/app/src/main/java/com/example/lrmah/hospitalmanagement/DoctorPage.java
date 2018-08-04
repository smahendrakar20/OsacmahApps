package com.example.lrmah.hospitalmanagement;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;


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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.id;
import static com.example.lrmah.hospitalmanagement.AdminLogin.pass;
import com.example.lrmah.hospitalmanagement.Data.HospitalContract.doctorsEntry;
import com.example.lrmah.hospitalmanagement.Data.doctorDbHelper;
import com.example.lrmah.hospitalmanagement.R;

import java.net.URI;

public class DoctorPage extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri uri;
    Button edit;
    Button checkPatients;
    TextView docUsername;
    TextView fieldOfDoc;
    TextView nameOfDoc;
    TextView pass;
    CheckBox available;
    String patientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_page);
        Intent i = getIntent();
        String username = i.getExtras().getString("unString");

        getURI(username);
        pass = (TextView) findViewById(R.id.passwordDoc);
        docUsername = (TextView) findViewById(R.id.userNameXml);
        nameOfDoc = (TextView) findViewById(R.id.nameXml);
        fieldOfDoc = (TextView) findViewById(R.id.fieldXml);
        available = (CheckBox) findViewById(R.id.availabilityCheckBox);
        edit=(Button) findViewById(R.id.editProfile);
        checkPatients=(Button) findViewById(R.id.checkPatientButton);
        getSupportLoaderManager().initLoader(0, null, this);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),doctors_delete_edit.class);
                i.setData(uri);
                i.putExtra("rem","remove");
                startActivity(i);
            }
        });
checkPatients.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(getApplicationContext(),listOfPatientsUnderSpecificDoctor.class);
        i.putExtra("list",patientList);
        startActivity(i);

    }
});

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

        return new CursorLoader(this, uri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            int unColumnIndex = cursor.getColumnIndex(doctorsEntry.column_username);
            int availableColumnIndex = cursor.getColumnIndex(doctorsEntry.column_available);
            int fieldColumnIndex = cursor.getColumnIndex(doctorsEntry.column_field);
            int nameColumnIndex = cursor.getColumnIndex(doctorsEntry.column_name);
            int passwordColumnIndex = cursor.getColumnIndex(doctorsEntry.column_password);
            int patientListColumn=cursor.getColumnIndex(doctorsEntry.column_patient);

            String username = cursor.getString(unColumnIndex);
            String field = cursor.getString(fieldColumnIndex);
            int availableValue = cursor.getInt(availableColumnIndex);
            String name = cursor.getString(nameColumnIndex);
            String passwordTemp = cursor.getString(passwordColumnIndex);
            patientList=cursor.getString(patientListColumn);

            docUsername.setText(username);
            nameOfDoc.setText(name);
            fieldOfDoc.setText(field);
            pass.setText(passwordTemp);

            if (availableValue == 1) {
                available.setChecked(true);
            } else
                {
                available.setChecked(false);
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void getURI(String un) {
        int temp = 0;
        Cursor cursor = null;

            doctorDbHelper helper = new doctorDbHelper(this);
            SQLiteDatabase db = helper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM allDoctors WHERE Username=?", new String[]{un + ""});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                temp = cursor.getInt(cursor.getColumnIndex("_id"));
                uri = ContentUris.withAppendedId(doctorsEntry.CONTENT_URI, temp);
            }
    }

}

