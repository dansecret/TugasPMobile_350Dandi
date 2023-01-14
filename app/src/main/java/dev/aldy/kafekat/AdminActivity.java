package dev.aldy.kafekat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import dev.aldy.kafekat.Listview.User;
import dev.aldy.kafekat.Listview.UserAdapter;
import dev.aldy.kafekat.crud.EditTambah;

public class AdminActivity extends AppCompatActivity {

    DataHelper dbcenter;
    String[] daftar;
    ListView ListView01;
    protected Cursor cursor;
    public static AdminActivity ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ImageView btn = findViewById(R.id.imageView4);
        btn.setOnClickListener(arg0 -> {
            Intent inte = new Intent(AdminActivity.this, MapsActivity.class);
            startActivity(inte);
        });

        ImageView btn2 = findViewById(R.id.imageView3);

        btn2.setOnClickListener(arg0 -> {
            Intent inte = new Intent(AdminActivity.this, EditTambah.class);
            inte.putExtra("nama","");
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
            ListView01.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
                final String selection = daftar[arg2];
                final CharSequence[] dialogitem = {"Edit",
                        "Hapus"};
                AlertDialog.Builder builder = new
                        AlertDialog.Builder(AdminActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, (dialog, item) -> {
                    switch (item) {
                        case 0:
                                Intent i = new Intent(getApplicationContext(), EditTambah.class);
                                i.putExtra("nama", selection);
                                startActivity(i);
                            break;
                        case 1:
                            db.execSQL("DELETE FROM USER WHERE username  = '"+selection+"'");
                            RefreshList();
                            break;
                    }
                });
                builder.create().show();
            });

            ((ArrayAdapter) ListView01.getAdapter()).notifyDataSetInvalidated();
        }
    }
}