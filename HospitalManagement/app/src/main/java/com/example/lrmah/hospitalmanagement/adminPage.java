package com.example.lrmah.hospitalmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class adminPage extends AppCompatActivity
{
    //Buttons

    Button manageDoctors;
    Button manageAllDoctors;
    Button managePatients;
    Button manageAllPatients;
    Button adminLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        //Initializing buttons
        manageDoctors = (Button) findViewById(R.id.manageDoctorsButton);
        manageAllDoctors = (Button) findViewById(R.id.manageAllDoctorsButton);
        managePatients = (Button) findViewById(R.id.managePatientsButton);
        manageAllPatients = (Button) findViewById(R.id.manageAllPatientsButton);
        adminLogout = (Button) findViewById(R.id.adminPageLogoutButton);

        //defining onClick for all buttons

        manageDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(adminPage.this, adminManageDoctors.class);
                startActivity(i);
            }
        });
        managePatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(adminPage.this, adminManagePatients.class);
                startActivity(i);
            }
        });
        manageAllDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(adminPage.this, adminManageAllDoctors.class);
                startActivity(i);
            }
        });

        manageAllPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(adminPage.this, adminManageAllPatients.class);
                startActivity(i);
            }
        });

        adminLogout.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                AdminLogin.setAdminPassword(null);
                Intent i = new Intent(adminPage.this, AdminLogin.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"You have been logged out",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}



