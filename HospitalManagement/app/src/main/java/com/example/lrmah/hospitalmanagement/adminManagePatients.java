package com.example.lrmah.hospitalmanagement;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lrmah.hospitalmanagement.Data.HospitalContract;
import com.example.lrmah.hospitalmanagement.Data.HospitalContract.patientsEntry;
import com.example.lrmah.hospitalmanagement.Data.doctorDbHelper;

import static android.R.attr.name;
import static android.R.attr.type;
import static android.R.attr.x;
import static android.R.id.text1;
import static com.example.lrmah.hospitalmanagement.AdminLogin.pass;
import com.example.lrmah.hospitalmanagement.Data.HospitalContract.doctorsEntry;

public class adminManagePatients extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
    private String m_Text="";

    String p,q,s,t,pb;
    String r;

    Button addPatientButton;

    EditText problem;
    EditText patientUsername;
    EditText patientName;
    EditText phoneNo;
    EditText  type;
    EditText password;
    Spinner sp;

    String newPatientList;

    SimpleCursorAdapter simp;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_patients);

        sp=(Spinner) findViewById(R.id.spinnerPat);
        simp = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                null,
                new String[] {HospitalContract.doctorsEntry.column_name},
                new int[] {text1},
                0);
        simp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(simp);

        getSupportLoaderManager().initLoader(0, null, this);

        addPatientButton=(Button) findViewById(R.id.addPatientButtonXml);

        addPatientButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//////////THIS IS USED TO POP Up
                AlertDialog.Builder builder = new AlertDialog.Builder(adminManagePatients.this);
                builder.setTitle("Confirm Password");

// Set up the input

                final EditText input = new EditText(adminManagePatients.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

// Set up the buttons

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    //onClick method of the popUp dialogBox(its inside onclick of add button dont get confused)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //storing the value from the popUp in m_text
                        m_Text = input.getText().toString();
                        /////Inserting in the databasse

                        if (m_Text.equals("q"))
                        {
                            //initialising editetxs
                            problem=(EditText) findViewById(R.id.problemXml);
                            patientUsername = (EditText) findViewById(R.id.userNameXml);
                            patientName=(EditText) findViewById(R.id.patientNameXml);
                            phoneNo=(EditText) findViewById(R.id.phoneNo);
                            password=(EditText) findViewById(R.id.passwordPatient);
                            type=(EditText) findViewById(R.id.patientType);
                            sp=(Spinner) findViewById(R.id.spinnerPat);
                            //fetching values

                            p = patientUsername.getText().toString();
                            q=patientName.getText().toString();
                            r=type.getText().toString();
                            s=phoneNo.getText().toString();
                            t=password.getText().toString();
                            pb=problem.getText().toString();
                            Cursor cr=(Cursor) sp.getSelectedItem();
                            String text1="";

                            try{ text1=cr.getString(cr.getColumnIndex(doctorsEntry.column_name));}
                            catch(NullPointerException e)
                            {}



                            Toast.makeText(getApplicationContext(), "Confirmed", Toast.LENGTH_SHORT).show();
                            insertPatient(p,q,r,s,t,text1,pb);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });
    }

    public void insertPatient(String UserName,String name,String type,String phoneNo,String pass,String doctorSelected,String problem) {

        String[] doctorsProjection1 = {
                HospitalContract.doctorsEntry.column_id,
                HospitalContract.doctorsEntry.column_patient
        };

        Cursor tempCursor1 = getContentResolver().query(getUriDoctor(doctorSelected), doctorsProjection1, null, null, null);
        if (tempCursor1 != null && tempCursor1.moveToFirst()) {
            String prevPatientList = tempCursor1.getString(tempCursor1.getColumnIndex(HospitalContract.doctorsEntry.column_patient));
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
                Toast.makeText(this, "String update success " + newPatientList,
                        Toast.LENGTH_SHORT).show();
            }
            tempCursor1.close();
        }

        ContentValues values = new ContentValues();
        values.put(HospitalContract.patientsEntry.column_username, UserName);
        values.put(HospitalContract.patientsEntry.column_name, name);
        values.put(HospitalContract.patientsEntry.column_in_out_patient, Integer.parseInt(type));
        values.put(HospitalContract.patientsEntry.column_phoneNo, Long.parseLong(phoneNo));
        values.put(HospitalContract.patientsEntry.column_password, pass);
        values.put(patientsEntry.column_doctorWho, doctorSelected);
        values.put(patientsEntry.column_problem, problem);


        Uri newUri = getContentResolver().insert(patientsEntry.CONTENT_URI, values);
       finish();


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {



        String[] projection=  {
                        HospitalContract.doctorsEntry.column_id,
                        HospitalContract.doctorsEntry.column_name
                };

        return new CursorLoader(this,HospitalContract.doctorsEntry.CONTENT_URI, projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

simp.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        simp.swapCursor(null);

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
}