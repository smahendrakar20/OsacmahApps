package helpers;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class assethelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "wordsDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public assethelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getWords() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] projection={
                "_id",
                "Word",
                "Meaning",
                "SynonymsString"
        };

        String sqlTables = "wordslistwithoutjson";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, projection, null, null,
                null, null, null);

        c.moveToFirst();
        Log.d("done", "getWords: fsdafdsajsklfa");
        return c;

    }

    public Cursor getWordDetails(String word) {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] projection={
                "_id",
                "Word",
                "Meaning",
                "SynonymsString"
        };

        String selection="Word=?";

                       String sqlTables = "wordslistwithoutjson";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, projection, selection, new String[]{word},
                null, null, null);

        c.moveToFirst();
        Log.d("done", "getWords: fsdafdsajsklfa");
        return c;

    }

}
