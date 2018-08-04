package com.osm00apps.lrmah.synonymclusterforgre;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import adapters.MyCursorAdapter;
import data.DatabaseSchemas;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,SearchView.OnQueryTextListener {

    ListView searchedLv;
    String[] projection;
    MyCursorAdapter adapter;
    String toSearchWord="",List;
    SearchView searchView;
    MenuItem item;
    Intent intent;
    String selection;
    String [] selectionArgs;
    String whichList;
    Uri tableUri;
    int flag=0;
    TextView tv12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        tv12=(TextView) findViewById(R.id.noWordsTvid);
        projection = new String[]{
                DatabaseSchemas.CompleteTableSchema._ID,
                DatabaseSchemas.CompleteTableSchema.SYNONYMS_STRING,
                DatabaseSchemas.CompleteTableSchema.Meaning,
                DatabaseSchemas.CompleteTableSchema.Word
        };

        intent=getIntent();
        whichList=intent.getStringExtra("List");
        if(whichList==null)
        {
            whichList="Complete";
        }
        selection="Word like ? and Word!=? and Meaning!=? and Word!=? and Meaning!=?";
        selectionArgs=new String[]{toSearchWord+"%","---","---","----","----"};

        if(savedInstanceState!=null)
        {
            whichList=savedInstanceState.getString("List");
        }

        if(whichList.equals("Complete"))
        {
            projection = new String[]{
                    DatabaseSchemas.CompleteTableSchema._ID,
                    DatabaseSchemas.CompleteTableSchema.SYNONYMS_STRING,
                    DatabaseSchemas.CompleteTableSchema.Meaning,
                    DatabaseSchemas.CompleteTableSchema.Word
            };

            tableUri=DatabaseSchemas.CompleteTableSchema.CONTENT_URI;
            selection="Word like ? and Word!=? and Meaning!=? and Word!=? and Meaning!=?";
            selectionArgs=new String[]{toSearchWord+"%","---","---","----","----"};
        }
        else
        {
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
            tableUri=DatabaseSchemas.SearchedTableSchema.CONTENT_URI;
            if(!whichList.equals("Searched")) {
                selection = "Word like ? and Word!=? and Meaning!=? and Word!=? and Meaning!=? and " + whichList + "=?";
                selectionArgs = selectionArgs = new String[]{toSearchWord + "%", "---", "---", "----", "----", "1"};
                if (whichList.equals(DatabaseSchemas.SearchedTableSchema.Notes)) {
                    selection = "Word like ? and Word!=? and Meaning!=? and Word!=? and Meaning!=? and " + whichList + " is not null and " + whichList + "!=?";
                    selectionArgs = new String[]{toSearchWord + "%", "---", "---", "----", "----", "0"};
                }
            }
        }
        //searchView=(SearchView) item.getActionView();

        searchedLv=(ListView) findViewById(R.id.searchResultsLvId);
        adapter=new MyCursorAdapter(this,null);
        if(whichList.equals("Searched"))
            adapter=new MyCursorAdapter(this,null,"Searched");
        searchedLv.setAdapter(adapter);

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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            if(whichList.equals(DatabaseSchemas.SearchedTableSchema.Notes))
                selectionArgs = new String[]{toSearchWord + "%", "---", "---", "----", "----", "0"};
            else{
                if(whichList.equals("Searched")||whichList.equals("Complete"))
                    selectionArgs = new String[]{toSearchWord + "%", "---", "---", "----", "----"};
                else
                selectionArgs = new String[]{toSearchWord + "%", "---", "---", "----", "----", "1"};
            }

        return new CursorLoader(this,tableUri,projection,selection,selectionArgs, DatabaseSchemas.SearchedTableSchema._ID);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        tv12.setVisibility(View.INVISIBLE);
        searchedLv.setVisibility(View.VISIBLE);

        if(data.getCount()==0)
        {
           tv12.setVisibility(View.VISIBLE);
           searchedLv.setVisibility(View.INVISIBLE);
        }

        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
         item=menu.findItem(R.id.searchWordsItemId);
        searchView=(SearchView) item.getActionView();
        searchView.setQueryHint("Search here ");

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);


        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        //Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            toSearchWord = intent.getStringExtra(SearchManager.QUERY);
            searchView.setQuery(toSearchWord,false);
            if(flag==1) whichList=List;
            flag=0;
            getSupportLoaderManager().restartLoader(1,null,this);
        }
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onQueryTextChange(String s) {
       toSearchWord=s;
       getSupportLoaderManager().restartLoader(1,null,this);
        return false;
    }

}
