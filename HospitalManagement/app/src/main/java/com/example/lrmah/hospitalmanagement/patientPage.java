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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lrmah.hospitalmanagement.Data.HospitalContract;
import com.example.lrmah.hospitalmanagement.Data.doctorDbHelper;
import com.example.lrmah.hospitalmanagement.Data.patientDbHelper;
import com.example.lrmah.hospitalmanagement.Data.doctorDbHelper;

import org.w3c.dom.Text;

import static com.example.lrmah.hospitalmanagement.Data.HospitalContract.patientsEntry;

public class patientPage extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri uri;
    Button edit;
    TextView userName;
    TextView pass;
    TextView phoneNo;
    TextView nameOfPat;
    TextView inOut;
    TextView doctorWhoAllot;
    TextView problem;
    TextView prescriptionGiven;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_page);

        Intent i = getIntent();
        String username = i.getExtras().getString("unString");

        getURI(username);

         userName=(TextView) findViewById(R.id.userNameXml);
        pass=(TextView) findViewById(R.id.passwordPat);
       phoneNo=(TextView) findViewById(R.id.phoneNoXml);
        nameOfPat=(TextView) findViewById(R.id.nameXml);
         inOut=(TextView) findViewById(R.id.in_outXml);
         doctorWhoAllot=(TextView) findViewById(R.id.doctorWhoXml);
         problem=(TextView) findViewById(R.id.problemXML);
        prescriptionGiven=(TextView) findViewById(R.id.prescriptionXML);
        edit=(Button) findViewById(R.id.editProfile);
        getSupportLoaderManager().initLoader(0, null, this);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),patient_delete_edit.class);
                i.setData(uri);
                i.putExtra("rem","remove");
                startActivity(i);
            }
        });


    }
    public void getURI(String un) {
        int temp = 0;
        Cursor cursor = null;

       patientDbHelper helper = new patientDbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM allPatients WHERE Username=?", new String[]{un + ""});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            temp = cursor.getInt(cursor.getColumnIndex("_id"));
            uri = ContentUris.withAppendedId(HospitalContract.patientsEntry.CONTENT_URI, temp);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                HospitalContract.patientsEntry.column_id,
                HospitalContract.patientsEntry.column_username,
                HospitalContract.patientsEntry.column_phoneNo,
                HospitalContract.patientsEntry.column_problem,
                HospitalContract.patientsEntry.column_password,
                HospitalContract.patientsEntry.column_name,
                HospitalContract.patientsEntry.column_in_out_patient,
                HospitalContract.patientsEntry.column_prescription,
                HospitalContract.patientsEntry.column_doctorWho};

        return new CursorLoader(this, uri, projection, null, null, null);
    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if(cursor.moveToFirst())
        {
            int unColumnIndex = cursor.getColumnIndex(HospitalContract.patientsEntry.column_username);
            int passwordColumnIndex = cursor.getColumnIndex(HospitalContract.patientsEntry.column_password);
            int inOutColumnIndex = cursor.getColumnIndex(HospitalContract.patientsEntry.column_in_out_patient);
            int nameColumnIndex = cursor.getColumnIndex(HospitalContract.patientsEntry.column_name);
            int phoneNoColumnIndex = cursor.getColumnIndex(HospitalContract.patientsEntry.column_phoneNo);
            int prescriptionColumn=cursor.getColumnIndex(HospitalContract.patientsEntry.column_prescription);
            int doctorWhoColumn=cursor.getColumnIndex(HospitalContract.patientsEntry.column_doctorWho);
            int problemColumn=cursor.getColumnIndex(HospitalContract.patientsEntry.column_problem);

            String username = cursor.getString(unColumnIndex);
            String passTemp = cursor.getString(passwordColumnIndex);
            int inOutTemp = cursor.getInt(inOutColumnIndex);
            long number = cursor.getLong(phoneNoColumnIndex);
            String prescription = cursor.getString(prescriptionColumn);
           String name=cursor.getString(nameColumnIndex);
            String problemTemp=cursor.getString(problemColumn);
            String doc=cursor.getString(doctorWhoColumn);

            userName.setText(username);
            pass.setText(passTemp);
            nameOfPat.setText(name);
            doctorWhoAllot.setText(doc);
            problem.setText(problemTemp);
            prescriptionGiven.setText(prescription);

            String inOrOut;
            if (inOutTemp == 1) {
                inOrOut="In patient";
            } else
            {
                inOrOut="Out patient";
            }
            inOut.setText(inOrOut);

            String phoneNoTm=Long.toString(number);
            phoneNo.setText(phoneNoTm);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
