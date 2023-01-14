package dev.aldy.kafekat.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicReference;

import dev.aldy.kafekat.AdminActivity;
import dev.aldy.kafekat.DataHelper;
import dev.aldy.kafekat.R;

public class EditTambah extends AppCompatActivity {
    DataHelper dbcenter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        dbcenter = new DataHelper(this);
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        String nama = getIntent().getStringExtra("nama");
        TextView username = findViewById(R.id.textView1);
        TextView password = findViewById(R.id.textView2);
        Switch isAdmin = findViewById(R.id.switch1);
        AtomicReference<String> admin = new AtomicReference<>("false");
        Button btn = findViewById(R.id.button2);

        if (nama.equals("")){
            // NEW DATA
            btn.setOnClickListener(view -> {
                if (isAdmin.isChecked())
                    admin.set("true");

                db.execSQL("insert into user(username,password,is_admin) values('" +
                        username.getText().toString() +"','" +
                        password.getText().toString()+"','"+admin+"')"
                        );
                Toast.makeText(getApplicationContext(),
                        "Berhasil", Toast.LENGTH_LONG).show();
                AdminActivity.ma.RefreshList();
                finish();
            });

        } else {
            // UPDATE DATA
            cursor = db.rawQuery("SELECT password, is_admin FROM user WHERE username = '"+nama+"'", null);
            cursor.moveToFirst();
            username.setText(nama);
            password.setText(cursor.getString(0));
            isAdmin.setChecked(cursor.getString(1).equals("true"));
            btn.setOnClickListener(view -> {
                if (isAdmin.isChecked()) admin.set("true");
                db.execSQL("update user set username='" +
                        username.getText().toString() +"', password='" +
                        password.getText().toString()+"', is_admin='"+admin+"' where username = '"+nama+"'");
                Toast.makeText(getApplicationContext(),
                        "Berhasil", Toast.LENGTH_LONG).show();
                AdminActivity.ma.RefreshList();
                finish();
            });
        }

    }
}