package com.rfstar.sqlitetest2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.regex.Matcher;

public class UserProvider extends ContentProvider {
    private OpenHelper openHelper;
    //若不匹配采用UriMatcher.NO_MATCH(-1)返回
    private static final UriMatcher MATCHER=new UriMatcher(UriMatcher.NO_MATCH);

    //匹配码
    private static final int USER=1;
    private static final int USER_ID=2;

    static
    {
       /* matcher.addURI(path, "/tb/#", 1);
        matcher.addURI(path, "/tbs", 2);*/
//匹配返回USER,不匹配返回-1
        MATCHER.addURI("com.rfstar.sqlitetest.userProvider","user",USER);
//匹配返回USER_ID,不匹配返回-1
        MATCHER.addURI("com.rfstar.sqlitetest.userProvider","user/#",USER_ID);
    }
    @Override
    public boolean onCreate()
    {
        openHelper=new OpenHelper(this.getContext());
        return true;
    }

    /**
     * 外部应用向本应用插入数据
     */
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        SQLiteDatabase db=openHelper.getWritableDatabase();
        switch (MATCHER.match(uri))
        {
            case USER:
                long id=db.insert("user","name",values);
                Uri insertUri= ContentUris.withAppendedId(uri,id);
                return insertUri;
            default:
                throw new IllegalArgumentException("this is unknown uri:"+uri);
        }
    }
    /**
     * 外部应用向本应用删除数据
     */
    @Override
    public int delete(Uri uri,String selection,String[] selectionArgs)
    {
        SQLiteDatabase db=openHelper.getWritableDatabase();
        switch (MATCHER.match(uri))
        {
            case USER:
                return db.delete("user",selection,selectionArgs);//删除所有记录
            case USER_ID:
                long id=ContentUris.parseId(uri);//取得跟在Uri后面的数字
                String where="user_id="+id;
                if(null!=selection&&!"".equals(selection.trim()))
                {
                    where+="and"+selection;
                }
                return db.delete("user",where,selectionArgs);
            default:
                throw new IllegalArgumentException("this is unknown uri:"+uri);
        }
    }
    /**
     * 外部应用向本应用更新数据
     */
    @Override
    public int update(Uri uri,ContentValues values,String selection,String[] selectionArgs)
    {
        SQLiteDatabase db=openHelper.getWritableDatabase();
        switch (MATCHER.match(uri))
        {
            case USER:
                return db.update("user",values,selection,selectionArgs);//更新所有记录
            case USER_ID:
                long id=ContentUris.parseId(uri);//取得跟在Uri后面的数字
                String where="user_id="+id;
                if(null!=selection&&!"".equals(selection.trim()))
                {
                    where+="and"+selection;
                }
                return db.update("user",values,where,selectionArgs);
            default:
                throw new IllegalArgumentException("this is unknown uri:"+uri);
        }
    }
    /**
     * 如果是单条记录，应该返回以vnd.android.cursor.item/为首的字符串
     * 如果是多条记录，应该返回以vnd.android.cursor.dir/为首的字符串
     */
    @Override
    public String getType(Uri uri)
    {
        switch(MATCHER.match(uri))
        {
            case USER:
                return "vnd.android.cursor.dir/user";
            case USER_ID:
                return "vnd.android.cursor.item/user";
            default:
                throw new IllegalArgumentException("this is unknown uri:"+uri);
        }
    }
    @Override
    public Cursor query(Uri uri,String[] projection,String selection,String[]selectionArgs,String sortOrder)
    {
        SQLiteDatabase db=openHelper.getReadableDatabase();
        switch (MATCHER.match(uri))
        {
            case USER:
                return db.query("user",projection,selection,selectionArgs,null,null,sortOrder);
            case USER_ID:
                long id=ContentUris.parseId(uri);
                String where="user_id="+id;
                if(null!=selection&&!"".equals(selection.trim()))
                {
                    where+="and"+selection;
                }
                return db.query("user",projection,where,selectionArgs,null,null,sortOrder);
            default:
                throw new IllegalArgumentException("this is unknown uri:"+uri);
        }
    }
}
