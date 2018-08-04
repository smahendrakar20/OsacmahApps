package com.example.lrmah.rajaranichorpolice;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class welcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(welcomeActivity.this,MainActivity.class);
                startActivity(i);
            }
        }, 3000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
