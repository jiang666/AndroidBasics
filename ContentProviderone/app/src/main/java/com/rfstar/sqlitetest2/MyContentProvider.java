package com.rfstar.sqlitetest2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    private MySqliteOpenHelper helper;
    private SQLiteDatabase db;

    private String path = "com.teacher.contentprovider";
    // content://com.teacher.contentprovider/tb
    private Uri uri = Uri.parse("content://" + path + "/tb/#");

    private Uri uri2 = Uri.parse("content://" + path + "/tbs");

    // 表示当contentrsolver传递的uri没有匹配时，返回值为-1；
    UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {
        helper = new MySqliteOpenHelper(getContext(), "tb.db", null, 1);
        db = helper.getReadableDatabase();

        matcher.addURI(path, "/tb/#", 1);
        matcher.addURI(path, "/tbs", 2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // 状态码
        int i = matcher.match(uri);
        switch (i) {
            case 1:

                break;
            case 2:

                break;
            default:
                break;
        }
        Cursor cursor = db.query("tb", projection, selection, selectionArgs,
                null, null, sortOrder);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = db.insert("tb", null, values);
        Uri uri2 = ContentUris.withAppendedId(uri, id);
        return uri2;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        int index = db.delete("tb", selection, selectionArgs);
        return index;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int index = db.update("tb", values, selection, selectionArgs);
        return index;
    }

}

