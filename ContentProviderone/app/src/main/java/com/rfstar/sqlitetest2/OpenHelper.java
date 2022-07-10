package com.rfstar.sqlitetest2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {
    private static final String name="test.db";
    private static final int version=1;

    public OpenHelper(Context context)
    {
        super(context,name,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+
                "user (person_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name varchar(32),age INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(newVersion>oldVersion)
        {
            sqLiteDatabase.execSQL("ALTER TABLE user ADD phone VARCHAR(11)");
        }

    }
}
