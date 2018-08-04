package com.example.lrmah.hospitalmanagement;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lrmah.hospitalmanagement.Data.HospitalContract;
import com.example.lrmah.hospitalmanagement.Data.HospitalContract.doctorsEntry;
import com.example.lrmah.hospitalmanagement.Data.doctorDbHelper;

import static android.R.attr.name;
import static android.R.attr.x;
import static com.example.lrmah.hospitalmanagement.AdminLogin.pass;

public class adminManageDoctors extends AppCompatActivity
{
   private String m_Text="";
    doctorDbHelper helper;
    String p,q,r,s,t;
    int b;
    Button addDocButton;
    EditText docUsername;
    EditText patientName;
    EditText fieldOfDoc;
    EditText nameOfDoc;
    EditText password;
    CheckBox available;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_doctors);

        addDocButton=(Button) findViewById(R.id.addDocButtonXml);

        addDocButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//////////THIS IS USED TO POP Up
                    AlertDialog.Builder builder = new AlertDialog.Builder(adminManageDoctors.this);
                    builder.setTitle("Confirm Password");

// Set up the input

                final EditText input = new EditText(adminManageDoctors.this);
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
                                docUsername = (EditText) findViewById(R.id.userNameXml);
                                nameOfDoc=(EditText) findViewById(R.id.nameXml);
                                patientName=(EditText) findViewById(R.id.patientNameXml);
                                fieldOfDoc=(EditText) findViewById(R.id.fieldXml);
                                available=(CheckBox) findViewById(R.id.availabilityCheckBox);
                                password=(EditText) findViewById(R.id.passwordDoc);

                                //fetching values
                                p = docUsername.getText().toString();
                                q= nameOfDoc.getText().toString();
                                r=fieldOfDoc.getText().toString();
                                s= patientName.getText().toString();
                                t=password.getText().toString();
                                if(available.isChecked()){b=1;}else{b=0;};


                                Toast.makeText(getApplicationContext(), "Confirmed", Toast.LENGTH_SHORT).show();
                                insertDoctor(p,q,r,s,b,t);
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

    public void insertDoctor(String UserName,String name,String field,String patient,int available,String pass) {

    //Inserting
        ContentValues values = new ContentValues();
    values.put(doctorsEntry.column_username,UserName);
    values.put(doctorsEntry.column_name, name);
    values.put(doctorsEntry.column_field, field);
    values.put(doctorsEntry.column_available, available);
    values.put(doctorsEntry.column_patient, patient);
        values.put(HospitalContract.patientsEntry.column_password, pass);



        Uri newUri=getContentResolver().insert(doctorsEntry.CONTENT_URI,values);
finish();

}
}

