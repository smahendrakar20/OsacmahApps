package com.osm00apps.lrmah.synonymclusterforgre;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.osm00apps.lrmah.synonymclusterforgre.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import helpers.assethelper;
import helpers.myOpenHelper;

public class CompleteListActivity extends AppCompatActivity {
    ListView lv1;
    private assethelper db;
    myOpenHelper helper;
    FloatingActionButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_list);
        lv1=(ListView) findViewById(R.id.completeListId);
        searchButton=(FloatingActionButton)  findViewById(R.id.completeListFloatButtonId);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),SearchActivity.class);
                i.putExtra("List","Complete");
                startActivity(i);
            }
        });

        String[] projection= new String[]{
                "_id",
                "Word",
                "Meaning",
                "SynonymsString"
        };

        db = new assethelper(this);

        //  helper=new myOpenHelper(this);
        //  SQLiteDatabase db=helper.getReadableDatabase();

        try {

            List<String> newList=new ArrayList<String>();
            Cursor c=db.getWords();
            if(c.moveToFirst()) {
                do {
                    String temp = c.getString(c.getColumnIndex("Word"));
                    newList.add(temp);

                } while (c.moveToNext());
            }


            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    newList );

            lv1.setAdapter(arrayAdapter);
            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    String temp=arrayAdapter.getItem(position);
                    Intent i=new Intent(getApplicationContext(),mainClusterActivity.class);
                    Bundle b=new Bundle();
                   // b.putString("Word",temp);
                    //i.putExtras(b);
                    i.putExtra("Word",temp);
                    startActivity(i);
                }
            });

            Toast.makeText(this, "done!", Toast.LENGTH_SHORT).show();

        } catch (Exception ioe) {



        }



    }
}
