package data;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

public final class DatabaseSchemas {

    private DatabaseSchemas(){}

    public static String CONTENT_AUTHORITY="com.osm00apps.lrmah.synonymclusterforgre";
    public static final Uri BASE_CONTENT_URI= Uri.parse("content://com.osm00apps.lrmah.synonymclusterforgre");

    public static final class SearchedTableSchema implements BaseColumns
    {
        public final static String TABLE_NAME="searchedWordsTable";
        public final static String _ID=BaseColumns._ID;
        public final static String Word="Word";
        public final static String NumberOfTimesSearched="Count";
        public final static String Marked="Marked";
        public final static String LEARNED="Learned";
        public final static String HAS_NOTES="HasNotes";
        public final static String MEANING="Meaning";
        public final static String SYNONYMS_STRING="SynonymsString";
        public final static String Notes="Notes";


        public static Uri CONTENT_URI= Uri.withAppendedPath(BASE_CONTENT_URI,SearchedTableSchema.TABLE_NAME);


        public static final String SQL_CREATE_SEARCHED_TABLE= "CREATE TABLE " + SearchedTableSchema.TABLE_NAME+ "(" +
                SearchedTableSchema._ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                SearchedTableSchema.Word + " TEXT DEFAULT NULL," +
                SearchedTableSchema.MEANING + " TEXT DEFAULT NULL,"+
                SearchedTableSchema.SYNONYMS_STRING + " TEXT DEFAULT NULL,"+
                SearchedTableSchema.NumberOfTimesSearched + " INTEGER DEFAULT 0," +
                SearchedTableSchema.Marked + " INTEGER DEFAULT 0,"+
                SearchedTableSchema.LEARNED + " INTEGER DEFAULT 0,"+
                SearchedTableSchema.HAS_NOTES + " INTEGER DEFAULT 0,"+
                SearchedTableSchema.Notes + " TEXT DEFAULT 0"+ ");" ;



    }

    public static final class CompleteTableSchema implements BaseColumns
    {

        public final static String TABLE_NAME="wordslistwithoutjson";
        public final static String _ID=BaseColumns._ID;
        public final static String Word="Word";
        public final static String Meaning="Meaning";
        public final static String SYNONYMS_STRING="SynonymsString";

        public static Uri CONTENT_URI= Uri.withAppendedPath(BASE_CONTENT_URI,CompleteTableSchema.TABLE_NAME);

    }

  /*
    public static final class MarkedTableSchema implements BaseColumns
    {
        public static final String SQL_CREATE_MARKED_TABLE= "CREATE TABLE " + MarkedTableSchema.TABLE_NAME+ "(" +
                MarkedTableSchema._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MarkedTableSchema.Word + " TEXT NOT NULL," +
                MarkedTableSchema.x + " INTEGER DEFAULT 1," +
                MarkedTableSchema.y + " INTEGER DEFAULT 1," +
                MarkedTableSchema.z + " INTEGER DEFAULT 1" + ");" ;

        public final static String TABLE_NAME="MarkedWordsTable";
        public final static String _ID=BaseColumns._ID;
        public final static String Word="Word";
        public final static String x="fd";
        public final static String y="da";
        public final static String z="f";

    }*/

    public static final class NotesTableSchema implements BaseColumns
    {
        public static Uri CONTENT_URI= Uri.withAppendedPath(BASE_CONTENT_URI,NotesTableSchema.TABLE_NAME);

        public final static String TABLE_NAME="NotesWordsTable";
        public final static String _ID=BaseColumns._ID;
        public final static String Word="Word";
        public final static String Notes="Notes";
        public final static String MEANING="Meaning";
        public final static String y="temp";
        public final static String z="temp2";

        public static final String SQL_CREATE_NOTES_TABLE= "CREATE TABLE " + NotesTableSchema.TABLE_NAME+ "(" +
                NotesTableSchema._ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                NotesTableSchema.Word + " TEXT NOT NULL," +
                NotesTableSchema.MEANING+ " TEXT DEFAULT 1," +
                NotesTableSchema.Notes + " TEXT NOT NULL," +
                NotesTableSchema.y + " INTEGER DEFAULT 1," +
                NotesTableSchema.z+ " INTEGER DEFAULT 1" +
                 ");" ;
        }

}
