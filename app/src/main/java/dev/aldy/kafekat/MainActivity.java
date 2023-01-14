package dev.aldy.kafekat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText usernameTxt =  findViewById(R.id.usernameTxt);
        EditText passwordTxt = findViewById(R.id.passwordTxt);
        Button login =findViewById(R.id.button);
        dbHelper = new DataHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        login.setOnClickListener(v -> {
            String namaUser = usernameTxt.getText().toString();
            String passUser = passwordTxt.getText().toString();
            Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username=? AND password=?", new String[] {usernameTxt.getText().toString(), passwordTxt.getText().toString()});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                if (Objects.equals(cursor.getString(3), "true")){
                    Toast.makeText(getApplicationContext(), "SELAMAT DATANG ADMIN", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "SELAMAT DATANG KARYAWAN", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, KaryawanActivity.class);
                    startActivity(intent);
                }

            } else {
                Toast.makeText(getApplicationContext(), "Username atau Password salah!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}