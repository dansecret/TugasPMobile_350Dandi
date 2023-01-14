package dev.aldy.kafekat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String table1 = "CREATE TABLE user(id integer primary key AUTOINCREMENT, username text, password text, is_admin text);";
        Log.d("Data", "onCreate: " + table1);
        db.execSQL(table1);
        table1 = "INSERT INTO user(username, password, is_admin ) VALUES ('admin','123','true');";
        db.execSQL(table1);
        table1 = "INSERT INTO user(username, password, is_admin ) VALUES ('kar','123','false');";
        db.execSQL(table1);
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
    {
        // TODO Auto-generated method stub
    }
}