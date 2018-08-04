package com.osm00apps.lrmah.synonymclusterforgre;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapters.MyCursorAdapter;
import data.DatabaseSchemas;

public class ListOfSearchedWordsActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    ListView searchedLv;
    String[] projection;
    MyCursorAdapter adapter;
    FloatingActionButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_searched_words);
        searchedLv=(ListView) findViewById(R.id.searchedListViewId);
      adapter=new MyCursorAdapter(this,null,"Searched");
        searchedLv.setAdapter(adapter);
        searchButton=(FloatingActionButton)  findViewById(R.id.searchedListFloatButtonId);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),SearchActivity.class);
                i.putExtra("List","Searched");
                startActivity(i);
            }
        });

        projection = new String[]{
                DatabaseSchemas.SearchedTableSchema.SYNONYMS_STRING,
                DatabaseSchemas.SearchedTableSchema.MEANING,
                DatabaseSchemas.SearchedTableSchema.HAS_NOTES,
                DatabaseSchemas.SearchedTableSchema.LEARNED,
                DatabaseSchemas.SearchedTableSchema.Marked,
                DatabaseSchemas.SearchedTableSchema.Word,
                DatabaseSchemas.SearchedTableSchema._ID,
                DatabaseSchemas.SearchedTableSchema.NumberOfTimesSearched,
                DatabaseSchemas.SearchedTableSchema.Notes
        };
        getSupportLoaderManager().initLoader(1,null, this);

        searchedLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected=((TextView) view.findViewById(R.id.name)).getText().toString();
                Intent intent=new Intent(getApplicationContext(),mainClusterActivity.class);
                intent.putExtra("Word",selected);
                startActivity(intent);

            }
        });
    }


    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, DatabaseSchemas.SearchedTableSchema.CONTENT_URI,projection,null,null, DatabaseSchemas.SearchedTableSchema._ID);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    protected void onResume() {
        projection = new String[]{
                DatabaseSchemas.SearchedTableSchema.SYNONYMS_STRING,
                DatabaseSchemas.SearchedTableSchema.MEANING,
                DatabaseSchemas.SearchedTableSchema.HAS_NOTES,
                DatabaseSchemas.SearchedTableSchema.LEARNED,
                DatabaseSchemas.SearchedTableSchema.Marked,
                DatabaseSchemas.SearchedTableSchema.Word,
                DatabaseSchemas.SearchedTableSchema._ID,
                DatabaseSchemas.SearchedTableSchema.NumberOfTimesSearched,
                DatabaseSchemas.SearchedTableSchema.Notes
        };
        getSupportLoaderManager().restartLoader(1,null,this);
        super.onResume();
        }

}
