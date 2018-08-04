package com.telusko.learning.tenminuteswithtelusko;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    TextView tv1;
    TextInputLayout et1,et2;

    ProgressDialog prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


  //      mAuth = FirebaseAuth.getInstance();


        tv1 = (TextView)findViewById(R.id.tv1);
        et1 = (TextInputLayout)findViewById(R.id.et1);
        et2 = (TextInputLayout)findViewById(R.id.et2);




        prog = new ProgressDialog(this);


        tv1.setOnClickListener(view -> {




                login();




        });


    }

    private void sendToStart() {

        Intent i  = new Intent(Login.this,MainActivity.class);
        startActivity(i);

    }

    private void login() {



        Intent i  = new Intent(Login.this,MainActivity.class);
        startActivity(i);



    }


}
