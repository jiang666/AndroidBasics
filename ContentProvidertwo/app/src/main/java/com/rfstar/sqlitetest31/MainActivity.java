package com.rfstar.sqlitetest31;

import android.os.Bundle;

import android.net.Uri;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://blog.csdn.net/PPYY3344/article/details/120061601
 * https://blog.csdn.net/qq_33750826/article/details/53112511
 */

public class MainActivity extends Activity {
    private String twoPath = "com.rfstar.sqlitetest.userProvider";
    private Uri twoUri = Uri.parse("content://" + twoPath + "/user");
    private String path = "com.teacher.contentprovider";
    // ccontent://com.teacher.contentprovider/tb
    private Uri uri = Uri.parse("content://" + path + "/tb");
    private ContentResolver resolver;
    private List<Map<String,Object>> mapList;
    private ListView listView;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = getContentResolver();
        initData();
        initView();
    }
    private void initView()
    {
        listView=(ListView)findViewById(R.id.user_list);
        simpleAdapter=new SimpleAdapter(
                this,mapList,R.layout.item,new String[]{"name","age"},
                new int[]{R.id.user_name,R.id.user_age});
        listView.setAdapter(simpleAdapter);

    }
    private void initData()
    {
        mapList=new ArrayList<>();
        Cursor cursor= resolver.query(twoUri, new String[] { "name" }, null,
                null, null);
        while (cursor.moveToNext()) {
            int sss = cursor.getColumnIndex("name");
            String name=cursor.getString(sss);
            Map<String,Object> map=new HashMap<>();
            map.put("name",name);
            map.put("age",6);
            mapList.add(map);
        }

    }

    public void btn_insert(View v) {
        ContentValues values = new ContentValues();
        values.put("name", "强仔");
        Uri uri2 = resolver.insert(uri, values);
        long id = ContentUris.parseId(uri2);
        if (id > 0) {
            Toast.makeText(this, "添加成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "添加失败", Toast.LENGTH_LONG).show();
        }
    }

    public void btn_update(View v) {
        ContentValues values = new ContentValues();
        values.put("name", "强孙孙");
        // update tb set name="强孙孙" where id=1
        int index = resolver.update(uri, values, "id=?", new String[] { "1" });
        if (index > 0) {
            Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "修改失败", Toast.LENGTH_LONG).show();
        }
    }

    public void btn_select(View v) {
        // content:// contentprovider的域名/表名
        // select name from tb where id=1
        Cursor cursor= resolver.query(uri, new String[] { "name" }, null,
                null, null);
        while (cursor.moveToNext()) {
            int sss = cursor.getColumnIndex("name");
            String name=cursor.getString(sss);
            Toast.makeText(this, name, Toast.LENGTH_LONG).show();
        }
    }

    public void btn_delete(View v) {
        // delete from tb where id=2
        int index = resolver.delete(uri, "id=?", new String[] { "2" });
        if (index > 0) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_LONG).show();
        }
    }

    public void btn_select_test(View v) {
        // content:// contentprovider的域名/表名
        // select name from tb where id=1
        Cursor cursor= resolver.query(twoUri, new String[] { "name" }, null,
                null, null);
        while (cursor.moveToNext()) {
            int sss = cursor.getColumnIndex("name");
            String name=cursor.getString(sss);
            Log.e("=========","name = " + name);
        }
    }
    public void btn_delete_test(View v) {
        // delete from tb where id=2
        int index = resolver.delete(twoUri, "name=?", new String[] { "大鸟科创空间5" });
        if (index > 0) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_LONG).show();
        }

    }
}

