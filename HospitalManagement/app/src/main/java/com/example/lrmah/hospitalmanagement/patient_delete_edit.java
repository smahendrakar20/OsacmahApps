package com.example.lrmah.hospitalmanagement;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lrmah.hospitalmanagement.Data.doctorDbHelper;
import com.example.lrmah.hospitalmanagement.Data.patientDbHelper;

import com.example.lrmah.hospitalmanagement.Data.HospitalContract.patientsEntry;

import com.example.lrmah.hospitalmanagement.Data.HospitalContract;
import com.example.lrmah.hospitalmanagement.Data.HospitalContract.doctorsEntry;

import org.w3c.dom.Text;

import static android.R.attr.x;

public class patient_delete_edit extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
    Button savePatient;
    Button delPatient;
    private Uri uri;
int pos;

    EditText problem;
    EditText patientUsername;
    EditText patientName;
    EditText type;
    EditText password;
    EditText phoneNo;
    Spinner sp;
    Cursor cursor1;
    TextView temp1,temp2;

    SimpleCursorAdapter simp;
    String p,q,r,s,t,pb;
    String newPatientList;
    int b;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_delete_edit);
        Intent i=getIntent();
        uri= i.getData();

        String temp="remove";

        sp=(Spinner) findViewById(R.id.spinnerPat);
        simp = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                null,
                new String[] {HospitalContract.doctorsEntry.column_name},
                new int[] {android.R.id.text1},
                0);
        simp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(simp);




        getSupportLoaderManager().initLoader(0, null, this);

        savePatient=(Button) findViewById(R.id.savePatient);
        delPatient=(Button) findViewById((R.id.removePatient));

        sp=(Spinner) findViewById(R.id.spinnerPat);
        patientUsername = (EditText) findViewById(R.id.userNameXml);
        patientName=(EditText) findViewById(R.id.patientNameXml);
        password=(EditText) findViewById(R.id.passwordPatient);
        phoneNo=(EditText) findViewById(R.id.phoneNoXml);
        type=(EditText) findViewById(R.id.patientType);
        problem=(EditText) findViewById(R.id.problemXml);
        temp1=(TextView) findViewById(R.id.allotDocTv);
        temp2=(TextView) findViewById(R.id.TypeTv);

        String temporary=i.getExtras().getString("rem");
        if(temp.equals(temporary))
        {
            delPatient.setVisibility(View.INVISIBLE);
            sp.setVisibility(View.INVISIBLE);
            type.setVisibility(View.INVISIBLE);
            temp1.setVisibility(View.INVISIBLE);
            temp2.setVisibility(View.INVISIBLE);
        }

        delPatient.setOnClickListener(new View.OnClickListener() {
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



        savePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //fetching values
                p = patientUsername.getText().toString();
                q=patientName.getText().toString();
                r=type.getText().toString();
                s=phoneNo.getText().toString();
                t=password.getText().toString();
                pb=problem.getText().toString();

               Cursor cr=(Cursor) sp.getSelectedItem();
                String text1="";
                try{text1=cr.getString(cr.getColumnIndex(doctorsEntry.column_name));}
                catch(NullPointerException e){}

                updatePatient(p,q,r,s,t,text1,pb);
            }
        });

    }





    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                patientsEntry.column_id,
                patientsEntry.column_username,
                patientsEntry.column_password,
                patientsEntry.column_doctorWho,
                patientsEntry.column_name,
                patientsEntry.column_in_out_patient,
                patientsEntry.column_phoneNo,
                patientsEntry.column_problem,
        };

        return new CursorLoader(this, uri, projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
    {
        if (cursor == null || cursor.getCount() < 1) {
            return;

        }
        String[] projection1={
                HospitalContract.doctorsEntry.column_id,
                HospitalContract.doctorsEntry.column_name
        };
        cursor1=getContentResolver().query(HospitalContract.doctorsEntry.CONTENT_URI,projection1,null,null,null);
        simp.swapCursor(cursor1);
        if(cursor.moveToFirst())
        {
            int unColumnIndex=cursor.getColumnIndex(patientsEntry.column_username);
            int nameColumnIndex=cursor.getColumnIndex(patientsEntry.column_name);
            int doctorWhoColumnIndex=cursor.getColumnIndex(patientsEntry.column_doctorWho);
            int typeColumnIndex=cursor.getColumnIndex(patientsEntry.column_in_out_patient);
            int phoneNoColumnIndex=cursor.getColumnIndex(patientsEntry.column_phoneNo);
            int passwordColumnIndex=cursor.getColumnIndex(patientsEntry.column_password);
            int problemColumnIndex=cursor.getColumnIndex(patientsEntry.column_problem);

            String username=cursor.getString(unColumnIndex);
            long phoneNoValue=cursor.getLong(phoneNoColumnIndex);
            int typeVal=cursor.getInt(typeColumnIndex);
            String name=cursor.getString(nameColumnIndex);
            String passwordVal=cursor.getString(passwordColumnIndex);
            String doctorWho=cursor.getString(doctorWhoColumnIndex);
            String problemTemp=cursor.getString(problemColumnIndex);


            String x=Integer.toString(typeVal);
            String y=Long.toString(phoneNoValue);

            patientUsername.setText(username);
            patientName.setText(name);
            phoneNo.setText(y);
            password.setText(passwordVal);
            type.setText(x);
            problem.setText(problemTemp);



        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        patientName.setText("");
        patientUsername.setText("");
        phoneNo.setText("");
        password.setText("");
        type.setText("");
        problem.setText("");
        cursor1.close();

    }

    public void updatePatient(String UserName,String name,String type,String phoneNo,String pass,String doctorSelected,String problem)
    {

        String tempDoctorWho;
        String[] patientsProjection1={
               patientsEntry.column_id,
                patientsEntry.column_doctorWho
        };
        try{
        Cursor tempCursor=getContentResolver().query(getUriPatient(name),patientsProjection1,null,null,null);
        if( tempCursor != null && tempCursor.moveToFirst()) {
            tempDoctorWho = tempCursor.getString(tempCursor.getColumnIndex(patientsEntry.column_doctorWho));
            if (!doctorSelected.equals(tempDoctorWho))
            {
                String[] doctorsProjection1={
                        HospitalContract.doctorsEntry.column_id,
                        doctorsEntry.column_patient
                };
                Uri tempDocUri=getUriDoctor(tempDoctorWho);
                Cursor tempCursor2=getContentResolver().query(tempDocUri,doctorsProjection1,null,null,null);
                if( tempCursor2 != null && tempCursor2.moveToFirst())
            {
                String prevPatientListTemp = tempCursor2.getString(tempCursor2.getColumnIndex(doctorsEntry.column_patient));
                String newPatientListTemp = prevPatientListTemp.replace(name,"");
                ContentValues tempValues = new ContentValues();
                tempValues.put(doctorsEntry.column_patient, newPatientListTemp);
                int rowsAffected2 = getContentResolver().update(getUriDoctor(tempDoctorWho), tempValues, null, null);
                if (rowsAffected2 == 0) {
                    // If no rows were affected, then there was an error with the update.
                    Toast.makeText(this,"Not removed from previous doctor",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the update was successful and we can display a toast.
                    Toast.makeText(this, "Removed from previous doctor",
                            Toast.LENGTH_SHORT).show();
                }
                tempCursor2.close();
            }
            }
        }



        tempDoctorWho = tempCursor.getString(tempCursor.getColumnIndex(patientsEntry.column_doctorWho));
        if (!doctorSelected.equals(tempDoctorWho)) {
            String[] doctorsProjection1 = {
                    HospitalContract.doctorsEntry.column_id,
                    doctorsEntry.column_patient
            };

            Cursor tempCursor1 = getContentResolver().query(getUriDoctor(doctorSelected), doctorsProjection1, null, null, null);
            if (tempCursor1 != null && tempCursor1.moveToFirst()) {
                String prevPatientList = tempCursor1.getString(tempCursor1.getColumnIndex(doctorsEntry.column_patient));
                if (prevPatientList.equals("")) {
                    newPatientList = prevPatientList + name;
                } else {
                    newPatientList = prevPatientList + "," + name;
                }

                ContentValues tempValues = new ContentValues();
                tempValues.put(doctorsEntry.column_patient, newPatientList);
                int rowsAffected2 = getContentResolver().update(getUriDoctor(doctorSelected), tempValues, null, null);
                if (rowsAffected2 == 0) {
                    // If no rows were affected, then there was an error with the update.
                    Toast.makeText(this, "String update failed",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the update was successful and we can display a toast.
                    Toast.makeText(this, "String update success" + newPatientList,
                            Toast.LENGTH_SHORT).show();
                }
                tempCursor1.close();
            }
        }}catch (Exception e){}

        ContentValues values = new ContentValues();
        values.put(HospitalContract.patientsEntry.column_username, UserName);
        values.put(HospitalContract.patientsEntry.column_name, name);
        values.put(HospitalContract.patientsEntry.column_in_out_patient, Integer.parseInt(type));
        values.put(HospitalContract.patientsEntry.column_phoneNo, Long.parseLong(phoneNo));
        values.put(HospitalContract.patientsEntry.column_password, pass);
        values.put(patientsEntry.column_doctorWho, doctorSelected);
        values.put(patientsEntry.column_problem,problem);

        int rowsAffected = getContentResolver().update(uri, values, null, null);
        if (rowsAffected == 0) {
            // If no rows were affected, then there was an error with the update.
            Toast.makeText(this, "update failed",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the update was successful and we can display a toast.
            Toast.makeText(this, "update success",
                    Toast.LENGTH_SHORT).show();
        }
        finish();
        }
    public Uri getUriDoctor(String name) {
        int temp = 0;
        Cursor cursor = null;

        doctorDbHelper helper = new doctorDbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM allDoctors WHERE DoctorName=?", new String[]{name + ""});

        if (cursor.getCount()>0)
        {cursor.moveToFirst();
        temp = cursor.getInt(cursor.getColumnIndex("_id"));
        cursor.close();}
            return ContentUris.withAppendedId(doctorsEntry.CONTENT_URI, temp);

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
        return ContentUris.withAppendedId(patientsEntry.CONTENT_URI, temp);

    }


}
