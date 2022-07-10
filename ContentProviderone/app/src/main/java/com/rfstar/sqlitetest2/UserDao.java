package com.rfstar.sqlitetest2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private SQLiteDatabase database;
    public UserDao(SQLiteDatabase sqLiteDatabase) {
        this.database = sqLiteDatabase;
    }
    public boolean insert(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", user.getName());
        contentValues.put("age", user.getAge());
        long insertResult = database.insert("user", null, contentValues);
        if (insertResult == -1) {
            return false;
        }

        return true;
    }

    public boolean delete(User user) {
        int deleteResult = database.delete("user", "user_id+?", new String[]{user.getUserId() + ""});
        if (deleteResult == 0){
            return false;
        }
        return true;
    }
    public boolean update(User user)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",user.getName());
        contentValues.put("age",user.getAge());
        int updateResult=database.update("user",contentValues,"user_id=?",new String[]{user.getUserId()+""});
        if(updateResult==0)
        {
            return false;
        }
        return true;
    }
    public User queryOne(User user)
    {
        Cursor cursor=database.query("user",null,"name=?",new String[]
                {user.getName()},null,null,null);
        while (cursor.moveToNext())
        {
            user.setUserId(cursor.getInt(0));
            user.setAge(cursor.getInt(2));
        }
        return user;
    }

    public List<User> queryAll()
    {
        List<User> userList=new ArrayList<>();
        Cursor cursor=database.query("user",null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            User user=new User();
            user.setUserId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setAge(cursor.getInt(2));
            userList.add(user);
        }
        return userList;
    }
}