package com.osm00apps.lrmah.synonymclusterforgre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ErrorDisplayActivity extends AppCompatActivity {

    Button bt1;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_display);
        bt1=(Button) findViewById(R.id.tryAgainBtId);
        Intent i=getIntent();
       String displayMessage= i.getStringExtra("displayMessage");
       tv1=(TextView) findViewById(R.id.tv1);
       tv1.setText(displayMessage);
       bt1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i=new Intent(getApplicationContext(),MainActivity.class);
               startActivity(i);
           }
       });
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
