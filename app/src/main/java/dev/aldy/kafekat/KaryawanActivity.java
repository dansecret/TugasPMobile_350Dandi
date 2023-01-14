package dev.aldy.kafekat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import dev.aldy.kafekat.Listview.User;
import dev.aldy.kafekat.Listview.UserAdapter;
import dev.aldy.kafekat.crud.EditTambah;

public class KaryawanActivity extends AppCompatActivity {

    DataHelper dbcenter;
    String[] daftar;
    ListView ListView01;
    Menu menu;
    protected Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karyawan);
        ImageView btn = findViewById(R.id.imageView4);
        btn.setOnClickListener(arg0 -> {
            Intent inte = new Intent(KaryawanActivity.this, MapsActivity.class);
            startActivity(inte);
        });
        dbcenter = new DataHelper(this);
        RefreshList();
    }
    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM user", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();

        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1);

            ListView01 = findViewById(R.id.listView1);
            ArrayList<User> arrayList = new ArrayList<>();
            for (int x = 0; x < cursor.getCount(); x++) {
                arrayList.add(new User(daftar[x]));
            }
            UserAdapter userAdapter = new UserAdapter(this, R.layout.list_row, arrayList);
            ListView01.setAdapter(userAdapter);
            ListView01.setSelected(true);

            ((ArrayAdapter) ListView01.getAdapter()).notifyDataSetInvalidated();
        }
    }
}