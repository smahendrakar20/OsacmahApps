package com.osm00apps.lrmah.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.osm00apps.lrmah.synonymclusterforgre.R;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import data.DatabaseHelper;
import data.DatabaseSchemas;
import helpers.assethelper;

/**
 * A placeholder fragment containing a simple view.
 */
public  class ClustersFragment extends Fragment {

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

    private static final String ARG_SECTION_NUMBER = "section_number";

    public ClustersFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ClustersFragment newInstance(int sectionNumber) {
        ClustersFragment fragment = new ClustersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clusters, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        //get word name here from activity!
        toSearchWord = getIntent().getStringExtra("Word");

        synonymsSearchedList = new ArrayList<String>();


        synonymsSearchedLv = (ListView) rootView.findViewById(R.id.SynonymsWithSearchedLvId);



        synonymsSearchedLv.setOnTouchListener(this);


        getSynonymsFromSearchedWords();

        final ArrayAdapter<String> synSearchedAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, synonymsSearchedList);
        synonymsSearchedLv.setAdapter(synSearchedAdapter);

        return rootView;
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
