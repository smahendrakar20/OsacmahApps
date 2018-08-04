package com.example.lrmah.hospitalmanagement;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lrmah.hospitalmanagement.Data.HospitalContract;
import com.example.lrmah.hospitalmanagement.Data.HospitalContract.patientsEntry;

import org.w3c.dom.Text;

public class givePrescription extends AppCompatActivity {
EditText prescription;
    Button send;
    TextView nameTV,issueTV,num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_prescription);
        Intent i=getIntent();
        final Uri uriPat=i.getData();

        nameTV=(TextView) findViewById(R.id.nameOfPat);
        issueTV=(TextView) findViewById(R.id.problemOFPatient);
        prescription=(EditText) findViewById(R.id.prescriptionXML);
        send=(Button) findViewById(R.id.sendButton);
        num=(TextView) findViewById(R.id.patientNoXML);

        String[] patientsProjection1 = {
                HospitalContract.patientsEntry.column_id,
                HospitalContract.patientsEntry.column_problem,
                HospitalContract.patientsEntry.column_name,
                HospitalContract.patientsEntry.column_prescription,
                patientsEntry.column_phoneNo,
        };
        final Cursor tempCursor1 = getContentResolver().query(uriPat, patientsProjection1, null, null, null);
        if (tempCursor1 != null && tempCursor1.moveToFirst()) {

            int a=tempCursor1.getColumnIndex(patientsEntry.column_prescription);
            int b=tempCursor1.getColumnIndex(patientsEntry.column_name);
            int c=tempCursor1.getColumnIndex(patientsEntry.column_problem);
            int d=tempCursor1.getColumnIndex(patientsEntry.column_phoneNo);

            String name=tempCursor1.getString(b);
            String problem=tempCursor1.getString(c);
            String prescriptionTemp=tempCursor1.getString(a);
            String phoneNoTemp=tempCursor1.getString(d);

            nameTV.setText(name);
            issueTV.setText(problem);
            prescription.setText(prescriptionTemp);
            num.setText(phoneNoTemp);

        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prescriptionTemp=prescription.getText().toString();
                ContentValues v1=new ContentValues();
                v1.put(patientsEntry.column_prescription,prescriptionTemp);
                int rowsAffected=getContentResolver().update(uriPat,v1,null,null);
                if (rowsAffected == 0) {
                    // If no rows were affected, then there was an error with the update.
                    Toast.makeText(getApplicationContext(),"update failed",Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the update was successful and we can display a toast.
                    Toast.makeText(getApplicationContext(), "update success", Toast.LENGTH_SHORT).show();
                }
                tempCursor1.close();
            finish();
            }
        });
    }
}
