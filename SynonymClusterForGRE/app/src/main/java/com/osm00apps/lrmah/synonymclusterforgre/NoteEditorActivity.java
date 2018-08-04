package com.osm00apps.lrmah.synonymclusterforgre;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHelper;
import data.DatabaseSchemas;

public class NoteEditorActivity extends AppCompatActivity {

    EditText notesEt;
    TextView wordTv;
    SQLiteDatabase db;
    DatabaseHelper helper;
    String[] projectionSearchedTable;
    String wordName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        wordName=getIntent().getStringExtra("Word");

       // deleteBt=(Button) findViewById(R.id.deleteButton);
        //saveBt=(Button) findViewById(R.id.saveButton);
        notesEt=(EditText) findViewById(R.id.notesId);
        wordTv=(TextView) findViewById(R.id.wordTextView);

        helper=new DatabaseHelper(this);
        db=helper.getWritableDatabase();
        wordTv.setText(wordName);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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

        Cursor c=db.query(DatabaseSchemas.SearchedTableSchema.TABLE_NAME,projectionSearchedTable,selection,new String[]{wordName},null,null,null);
        if(c.moveToFirst()){
        String notes=c.getString(c.getColumnIndex(DatabaseSchemas.SearchedTableSchema.Notes));
            notesEt.setText(notes);
        }

    }

  //  @Override
  /*  public void onClick(View view) {
        ContentValues values=new ContentValues();
        switch (view.getId())
        {
            case R.id.saveButton:
                values.put(DatabaseSchemas.SearchedTableSchema.Notes,notesEt.getText().toString());
                db.update(DatabaseSchemas.SearchedTableSchema.TABLE_NAME,values,"Word=?",new String[]{wordName});
                Toast.makeText(this,"Note Added",Toast.LENGTH_SHORT).show();
                break;
            case R.id.deleteButton:
                values.put(DatabaseSchemas.SearchedTableSchema.Notes,"No notes here");
                db.update(DatabaseSchemas.SearchedTableSchema.TABLE_NAME,values,"Word=?",new String[]{wordName});
                Toast.makeText(this,"Note DELETED",Toast.LENGTH_SHORT).show();
                break;
                default:
                    Toast.makeText(this,"unable to perform action",Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public void onBackPressed() {
        ContentValues values=new ContentValues();
        String temp=notesEt.getText().toString();
        if(temp.equalsIgnoreCase(""))
            temp="0";
        values.put(DatabaseSchemas.SearchedTableSchema.Notes,temp);
        db.update(DatabaseSchemas.SearchedTableSchema.TABLE_NAME,values,"Word=?",new String[]{wordName});
        super.onBackPressed();
    }
}
