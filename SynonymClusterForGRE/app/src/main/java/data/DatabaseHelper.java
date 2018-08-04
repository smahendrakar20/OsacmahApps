package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="wordsDatabase";
    public final static int DATABASE_VERSION=1;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(DatabaseSchemas.SearchedTableSchema.SQL_CREATE_SEARCHED_TABLE);
        sqLiteDatabase.execSQL(DatabaseSchemas.NotesTableSchema.SQL_CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
