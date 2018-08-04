package com.osm00apps.lrmah.synonymclusterforgre;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import data.DatabaseHelper;
import data.DatabaseSchemas;
import helpers.assethelper;

public class mainClusterActivity extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener{

    String toSearchWord;
    String[] projectionSearchedTable;
    assethelper completeListHelper;
    String meaning, synonyms, synonymsSearchedWords, notes,toSearchWordAdj,toSearchWordNoun,toSearchWordVerb,toSearchWordAdv;
    TextView notesTv, wordTv;
    CheckBox learnedCkB, markedCkB, searchedCkb;
    int learned, marked, numOfTimesSearched;
    FloatingActionButton addNotesBt;
    ListView synonymsLv, meaningLv,synonymsSearchedLv;
    List<String> synonymsList, meaningsList,synonymsSearchedList;
    Cursor c;
    DatabaseHelper helper;
    SQLiteDatabase db;
    TextView noWordsTv;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster);
        toSearchWord = getIntent().getStringExtra("Word");

        synonymsSearchedList = new ArrayList<String>();
        synonymsList = new ArrayList<String>();
        meaningsList = new ArrayList<String>();


        noWordsTv=(TextView) findViewById(R.id.noWordsTvid);
        notesTv = (TextView) findViewById(R.id.notesTvId);
        wordTv = (TextView) findViewById(R.id.wordTvId);
        meaningLv = (ListView) findViewById(R.id.meaningLvId);
        synonymsLv = (ListView) findViewById(R.id.SynonymsLvId);
        synonymsSearchedLv = (ListView) findViewById(R.id.SynonymsWithSearchedLvId);
        learnedCkB = (CheckBox) findViewById(R.id.learnedCheckBoxId);
        markedCkB = (CheckBox) findViewById(R.id.markedCheckBoxId);
        searchedCkb = (CheckBox) findViewById(R.id.searchedCheckBoxId);
        addNotesBt = (FloatingActionButton) findViewById(R.id.addNotesId);

        wordTv.setMovementMethod(new ScrollingMovementMethod());
        notesTv.setMovementMethod(new ScrollingMovementMethod());
        wordTv.setMaxLines(20);

        synonymsLv.setOnTouchListener(this);
        notesTv.setOnTouchListener(this);
        synonymsSearchedLv.setOnTouchListener(this);
        meaningLv.setOnTouchListener(this);
        learnedCkB.setOnClickListener(this);
        markedCkB.setOnClickListener(this);
        addNotesBt.setOnClickListener(this);

        wordTv.setTextColor(Color.parseColor("#abb2bc"));

        //to get data from complete list table
        completeListHelper = new assethelper(this);

        //projectionSearchedTable to help with searched table
        projectionSearchedTable = new String[]{
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


        String selection = DatabaseSchemas.SearchedTableSchema.Word + "=?";

        helper = new DatabaseHelper(this);
        db = helper.getWritableDatabase();
        c = db.query(DatabaseSchemas.SearchedTableSchema.TABLE_NAME, projectionSearchedTable, selection, new String[]{toSearchWord}, null, null, null);

      //searching if already word is seen
        if (c.getCount() == 0) {
            //new Word!!! add to searched list
            searchedCkb.setChecked(false);
            getValuesFromCompleteTable();
            insertIntoSearchedList();

        } else {
            //already searched word!!!
            searchedCkb.setChecked(true);
            getValuesFromSearchedList();
        }

        //getting cluster
        getSynonymsFromSearchedWords();

        final ArrayAdapter<String> synSearchedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, synonymsSearchedList);
        synonymsSearchedLv.setAdapter(synSearchedAdapter);

        final ArrayAdapter<String> synAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, synonymsList);
        synonymsLv.setAdapter(synAdapter);

        final ArrayAdapter<String> meaningAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, meaningsList);
        meaningLv.setAdapter(meaningAdapter);


        notesTv.setText(notes);
        wordTv.setText(toSearchWord);

    }



    //this goes in main activ
    void insertIntoSearchedList(){
        ContentValues values=new ContentValues();
        values.put(DatabaseSchemas.SearchedTableSchema.SYNONYMS_STRING,synonyms);
        values.put(DatabaseSchemas.SearchedTableSchema.Word,toSearchWord);
        values.put(DatabaseSchemas.SearchedTableSchema.MEANING,meaning);
        values.put(DatabaseSchemas.SearchedTableSchema.NumberOfTimesSearched,1);
        long x=db.insert(DatabaseSchemas.SearchedTableSchema.TABLE_NAME,null,values);
        if(x==-1)
            Toast.makeText(this,"not inserted in searched table",Toast.LENGTH_LONG).show();

    }

    void getValuesFromSearchedList()
    {
       if(c.moveToFirst()) do {
           synonyms = c.getString(c.getColumnIndex(DatabaseSchemas.SearchedTableSchema.SYNONYMS_STRING));
           meaning = c.getString(c.getColumnIndex(DatabaseSchemas.SearchedTableSchema.MEANING));
           learned = c.getInt(c.getColumnIndex(DatabaseSchemas.SearchedTableSchema.LEARNED));
           marked = c.getInt(c.getColumnIndex(DatabaseSchemas.SearchedTableSchema.Marked));
           numOfTimesSearched = c.getInt(c.getColumnIndex(DatabaseSchemas.SearchedTableSchema.NumberOfTimesSearched));
           notes = c.getString(c.getColumnIndex(DatabaseSchemas.SearchedTableSchema.Notes));
           //c.getInt(c.getColumnIndex(DatabaseSchemas.SearchedTableSchema.HAS_NOTES));
           //c.getString(c.getColumnIndex(DatabaseSchemas.SearchedTableSchema.Word));
       }while(c.moveToNext());

       if(learned==1)
       {
           wordTv.setTextColor(ContextCompat.getColor(this,R.color.myGreen));
           learnedCkB.setChecked(true);
       }
       if(marked==1)
       {
           wordTv.setTextColor(ContextCompat.getColor(this,R.color.myRed));
           markedCkB.setChecked(true);
       }

        populateLists(synonyms,meaning);
    }


    void getValuesFromCompleteTable()
    {
        notes="No Notes Yet. Click Below to Add";
        synonymsSearchedWords="No words here!";
        Cursor c2=completeListHelper.getWordDetails(toSearchWord);
        if(c2.moveToFirst())
        {
            do
            {
                meaning=c2.getString(c2.getColumnIndex("Meaning"));
                synonyms=c2.getString(c2.getColumnIndex("SynonymsString"));
            } while(c2.moveToNext());

        }
        populateLists(synonyms,meaning);
    }
    void populateLists(String synonyms,String meaning)
    {
        StringTokenizer meaningTok=new StringTokenizer(meaning,";");
        while(meaningTok.hasMoreTokens())
        {
            String temp1=meaningTok.nextToken();
            if(temp1.startsWith(" ADJ.")){
                getTokens("ADJ.",temp1);
            }
            if(temp1.startsWith(" ADV.")){
                getTokens("ADV.",temp1);
            }
            if(temp1.startsWith(" V.")){
                getTokens("V.",temp1);
            }
            if(temp1.startsWith(" N.")){
                getTokens("N.",temp1);
            }
            meaningsList.add(temp1);
        }

        StringTokenizer synonymsTok=new StringTokenizer(synonyms,",");
        while(synonymsTok.hasMoreTokens())
        {
            String temp2=synonymsTok.nextToken();
            synonymsList.add(temp2);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){
        switch (view.getId())
        {
       /*    case R.id.SynonymsLvId:
                view.getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case R.id.SynonymsWithSearchedLvId:
                if(synonymsSearchedList.isEmpty()){break;}
                else{
                view.getParent().requestDisallowInterceptTouchEvent(true);}
                break;
            case R.id.meaningLvId:
                view.getParent().requestDisallowInterceptTouchEvent(true);
                break;
                */
            case R.id.notesTvId:
                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                }


        }
        return false;
    }

    @Override
    public void onClick(View view) {
        boolean temp=false;
        String where="Word=?";
        int value=0;
        String[] whereArg=new String[]{toSearchWord};
        ContentValues values=new ContentValues();
        wordTv.setTextColor(Color.parseColor("#abb2bc"));

        switch (view.getId())
        {
            case R.id.markedCheckBoxId:
                temp=markedCkB.isChecked();
                if(temp){value=1;
                wordTv.setTextColor(ContextCompat.getColor(this,R.color.myRed));}
                else{ value=0;}
                values.put(DatabaseSchemas.SearchedTableSchema.Marked,value);
                db.update(DatabaseSchemas.SearchedTableSchema.TABLE_NAME,values,where,whereArg);
                break;
            case R.id.learnedCheckBoxId: temp=learnedCkB.isChecked();
                if(temp){value=1;
                    wordTv.setTextColor(ContextCompat.getColor(this,R.color.myGreen));}
                else{ value=0;}
                values.put(DatabaseSchemas.SearchedTableSchema.LEARNED,value);
                db.update(DatabaseSchemas.SearchedTableSchema.TABLE_NAME,values,where,whereArg);
                break;
            case R.id.addNotesId:
                Intent i=new Intent(this,NoteEditorActivity.class);
                i.putExtra("Word",toSearchWord);
                startActivity(i);
                break;
            default:
                Toast.makeText(this,"Something wrong with click",Toast.LENGTH_SHORT).show();
        }
    }

    void getSynonymsFromSearchedWords()
    {
        int flag;
        Cursor cursor= db.query(DatabaseSchemas.SearchedTableSchema.TABLE_NAME, projectionSearchedTable, null, null, null, null, null);
        if(cursor.moveToFirst())
            do{
            flag=0;
            String word1=cursor.getString(cursor.getColumnIndex(DatabaseSchemas.SearchedTableSchema.Word));
            String meaning1=cursor.getString(cursor.getColumnIndex(DatabaseSchemas.SearchedTableSchema.MEANING));
                StringTokenizer meaningTok=new StringTokenizer(meaning1,";");
                while(meaningTok.hasMoreTokens())
                {
                    String temp1=meaningTok.nextToken();
                    if(temp1.equalsIgnoreCase(toSearchWord)){flag=1;}
                    if(temp1.equalsIgnoreCase(toSearchWordAdj)){flag=1;}
                    if(temp1.equalsIgnoreCase(toSearchWordVerb)){flag=1;}
                    if(temp1.equalsIgnoreCase(toSearchWordNoun)){flag=1;}
                    if(temp1.equalsIgnoreCase(toSearchWordAdv)){flag=1;}
                }




           String synonyms1= cursor.getString(cursor.getColumnIndex(DatabaseSchemas.SearchedTableSchema.SYNONYMS_STRING));
                StringTokenizer synonymsTok=new StringTokenizer(synonyms1,",");
                while(synonymsTok.hasMoreTokens())
                {
                    String temp2=synonymsTok.nextToken();
                    if(temp2.equalsIgnoreCase(toSearchWord)){flag=1;}
                    if(temp2.equalsIgnoreCase(toSearchWordAdj)){flag=1;}
                    if(temp2.equalsIgnoreCase(toSearchWordVerb)){flag=1;}
                    if(temp2.equalsIgnoreCase(toSearchWordNoun)){flag=1;}
                    if(temp2.equalsIgnoreCase(toSearchWordAdv)){flag=1;}
                }
                if(flag==1){synonymsSearchedList.add(word1);}

        }while (cursor.moveToNext());
        if(synonymsSearchedList.isEmpty())
        {
            noWordsTv.setVisibility(View.VISIBLE);
            synonymsSearchedLv.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Cursor c1 = db.query(DatabaseSchemas.SearchedTableSchema.TABLE_NAME, projectionSearchedTable, "Word=?", new String[]{toSearchWord}, null, null, null);

        if(c1.moveToFirst()){
            String notes=c1.getString(c.getColumnIndex(DatabaseSchemas.SearchedTableSchema.Notes));
            if(notes.equals("0"))
            {
                notesTv.setText("No notes yet! Click below to add.");
            }
            else {
                notesTv.setText(notes);
            }
        }

    }

   void getTokens(String type,String meaning)
    {
      StringTokenizer temp=null;
      switch (type){
          case "ADJ.":
              temp=new StringTokenizer(meaning.substring(6),":");
              toSearchWordAdj=temp.nextToken();
              break;
          case "N.":
              temp=new StringTokenizer(meaning.substring(4),":");
              toSearchWordNoun=temp.nextToken();
              break;
          case "ADV.":
              temp= new StringTokenizer(meaning.substring(6),":");
              toSearchWordAdv=temp.nextToken();
              break;
          case "V.":
              temp= new StringTokenizer(meaning.substring(4),":");
              toSearchWordVerb=temp.nextToken();
              break;
              default:
                  break;
      }
    }
}
