package data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import helpers.assethelper;

public class WordsDatabaseContentProvider extends ContentProvider {

    public static final int WORD_SEARCHED_TABLE=100;
    public static final int WORD_ID_SEARCHED_TABLE=101;
    public static final int WORD_COMPLETE_TABLE=200;
    public static final int WORD_ID_COMPLETE_TABLE=201;

    public static final UriMatcher sUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(DatabaseSchemas.CONTENT_AUTHORITY,DatabaseSchemas.CompleteTableSchema.TABLE_NAME,WORD_COMPLETE_TABLE);
        sUriMatcher.addURI(DatabaseSchemas.CONTENT_AUTHORITY,DatabaseSchemas.CompleteTableSchema.TABLE_NAME+"/#",WORD_ID_COMPLETE_TABLE);
        sUriMatcher.addURI(DatabaseSchemas.CONTENT_AUTHORITY,DatabaseSchemas.SearchedTableSchema.TABLE_NAME,WORD_SEARCHED_TABLE);
        sUriMatcher.addURI(DatabaseSchemas.CONTENT_AUTHORITY,DatabaseSchemas.SearchedTableSchema.TABLE_NAME+"/#",WORD_ID_SEARCHED_TABLE);
    }
    private DatabaseHelper mDbHelper;
    private assethelper mAssetHelper;
    @Override
    public boolean onCreate() {
        mDbHelper=new DatabaseHelper(getContext());
        mAssetHelper=new assethelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db=mDbHelper.getReadableDatabase();
        SQLiteDatabase db1=mAssetHelper.getReadableDatabase();
        Cursor cursor=null;

        int match=sUriMatcher.match(uri);
        switch (match){
            case WORD_COMPLETE_TABLE:
                cursor=db1.query(DatabaseSchemas.CompleteTableSchema.TABLE_NAME,projection,selection,selectionArgs,null,null, DatabaseSchemas.CompleteTableSchema._ID);
                break;
            case WORD_ID_COMPLETE_TABLE:
                selection=DatabaseSchemas.CompleteTableSchema._ID + "=?";
                selectionArgs= new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor=db1.query(DatabaseSchemas.CompleteTableSchema.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;

            case WORD_SEARCHED_TABLE:
                cursor=db.query(DatabaseSchemas.SearchedTableSchema.TABLE_NAME,projection,selection,selectionArgs,null,null, DatabaseSchemas.SearchedTableSchema._ID);
                break;

            case WORD_ID_SEARCHED_TABLE:
                selection=DatabaseSchemas.SearchedTableSchema._ID + "=?";
                selectionArgs= new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor=db.query(DatabaseSchemas.SearchedTableSchema.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;

                default: break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
