package com.osm00apps.lrmah.synonymclusterforgre;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import adapters.MyCursorAdapter;
import data.DatabaseSchemas;

public class ListOfLearnedWordsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    ListView learnedLv;
    String[] projection;
    MyCursorAdapter adapter;
    String selection;
    String[] selectionArgs;
    FloatingActionButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_learned_words);

        searchButton=(FloatingActionButton)  findViewById(R.id.learnedListFloatButtonId);
        learnedLv=(ListView) findViewById(R.id.learnedListViewId);
        adapter=new MyCursorAdapter(this,null);
        learnedLv.setAdapter(adapter);
        selection= DatabaseSchemas.SearchedTableSchema.LEARNED+"=?";
        selectionArgs=new String[]{"1"};

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),SearchActivity.class);
                i.putExtra("List",DatabaseSchemas.SearchedTableSchema.LEARNED);
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

        learnedLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, DatabaseSchemas.SearchedTableSchema.CONTENT_URI,projection,selection,selectionArgs,null);
        }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
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

