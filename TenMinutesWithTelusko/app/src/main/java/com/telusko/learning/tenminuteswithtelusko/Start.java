package com.telusko.learning.tenminuteswithtelusko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Start extends AppCompatActivity {

    TextView tv1,tv2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        tv1 = (TextView)findViewById(R.id.tvlog);
        tv2 = (TextView)findViewById(R.id.tv2);


        tv1.setOnClickListener(view -> {


            Intent i = new Intent(Start.this, Login.class);
            startActivity(i);


        });

        tv2.setOnClickListener(view -> {


            Intent i = new Intent(Start.this, Register.class);
            startActivity(i);


        });



    }
}
