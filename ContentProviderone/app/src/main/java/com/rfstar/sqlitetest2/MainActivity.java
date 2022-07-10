package com.rfstar.sqlitetest2;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
public class MainActivity extends AppCompatActivity {
    private OpenHelper openHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openHelper=new OpenHelper(this);
        initData();
    }
    private void initData() {
        SQLiteDatabase sqLiteDatabase=openHelper.getReadableDatabase();
        UserDao userDao=new UserDao(sqLiteDatabase);
        sqLiteDatabase.beginTransaction();
        try
        {
            User user=new User();
            for(int i=0;i<10;i++)
            {
                user.setName("大鸟科创空间"+i);
                user.setAge(6);
                Log.e("=======","add 大鸟科创空间" +userDao.insert(user));

            }
            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e)
        {
            Log.e("user  Transaction",e.toString());
        }finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
        }
    }
}