package com.example.lrmah.hospitalmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lrmah.hospitalmanagement.Data.doctorDbHelper;
public class DoctorLogin extends AppCompatActivity {
    Intent i;
    String username = "";
    String password = "";
    EditText un, pass;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        login = (Button) findViewById(R.id.loginXml1);
        un = (EditText) findViewById(R.id.usernameDocLoginXml);
        pass = (EditText) findViewById(R.id.passDocLogin);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                username = un.getText().toString().trim();
                password = pass.getText().toString().trim();
                if(checkIfNotEmpty(username,password)) {

                        if (checkPassword(username, password).equals("yes")) {
                            i = new Intent(getApplicationContext(), DoctorPage.class);
                            Toast.makeText(getApplicationContext(), "correctpassword", Toast.LENGTH_SHORT).show();
                            i.putExtra("unString", username);
                            startActivity(i);
                            finish();
                        }
                        else {
                           if(checkPassword(username, password).equals("no"))
                            Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        {
                        Toast.makeText(getApplicationContext(), "Enter both password and username", Toast.LENGTH_SHORT).show();
                    }


                }
        });

    }


    public String checkPassword(String un, String pass) {
        String temp = null;
        Cursor cursor = null;
        try {
            doctorDbHelper helper = new doctorDbHelper(this);
            SQLiteDatabase db = helper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM allDoctors WHERE Username=?", new String[]{un + ""});

            try {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    temp = cursor.getString(cursor.getColumnIndex("Password"));
                }
                if (temp.equals(pass)) {
                    return "yes";
                } else {
                    return "no";
                }
            } catch (NullPointerException e) {
                Toast.makeText(getApplicationContext(), "Wrong credentials", Toast.LENGTH_LONG).show();
                return "exception";
            }
        } finally {
            cursor.close();
        }

    }

    public boolean checkIfNotEmpty(String username, String password)
    {
        if(password.isEmpty() || password.length() == 0 || password.equals("") || password == null
                || username.isEmpty() || username.length() == 0 || username.equals("") || username == null)
        {

            return false;
        }
        else
        {
            return true;
        }
    }


}
