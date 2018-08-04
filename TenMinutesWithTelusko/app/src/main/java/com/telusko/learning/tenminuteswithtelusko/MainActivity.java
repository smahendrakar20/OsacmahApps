package com.telusko.learning.tenminuteswithtelusko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import  com.telusko.learning.tenminuteswithtelusko.DataHelpers.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    Button readBtId,watchBtId;
    String link,description;

    FirebaseDatabase database ;
    DatabaseReference myRef ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Video");
        getVideoLink();

        readBtId = (Button) findViewById(R.id.readBtId);
        watchBtId = (Button) findViewById(R.id.watchBtId);

        readBtId.setOnClickListener(view -> {


            Intent i = new Intent(MainActivity.this, Course.class);
            startActivity(i);


        });

        watchBtId.setOnClickListener(view -> {
            getVideoLink();
            Intent i=new Intent(getApplicationContext(),VideoActivity.class);
            i.putExtra("Link",link);
            i.putExtra("Description",description);
            startActivity(i);

        });
    }


    public void getVideoLink()
    {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot tempSnapShot: dataSnapshot.getChildren())
                {
                    VideoLink temp=tempSnapShot.getValue(VideoLink.class);
                    link=temp.getLink();
                    description=temp.getDescription();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);



       getMenuInflater().inflate(R.menu.main_menu, menu);

        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);


       if(item.getItemId() == R.id.i3){

          sendToStart();



      }

        return true;
    }

    private void sendToStart() {

        Intent i = new Intent(MainActivity.this, Start.class);
        startActivity(i);

    }
}