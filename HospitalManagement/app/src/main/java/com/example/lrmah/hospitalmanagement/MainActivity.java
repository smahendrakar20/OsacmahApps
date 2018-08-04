package com.example.lrmah.hospitalmanagement;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for the doctor login button

        Button doctorLoginButton= (Button) findViewById(R.id.doctorLoginButtonXml);
        doctorLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,DoctorLogin.class);
                startActivity(i);
            }
        });

        //For the patient login button

        Button patientLoginButton= (Button) findViewById(R.id.patientLoginButtonXml);
        patientLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,patientLogin.class);
                startActivity(i);
            }
        });

    //for the admin login button

        Button adminLoginButton= (Button) findViewById(R.id.adminLoginButtonXml);
        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,AdminLogin.class);
                startActivity(i);
            }
        });

    }

}
