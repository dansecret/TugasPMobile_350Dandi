package com.example.android_internal_storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_internal_storage.database.UserDatabase;
import com.example.android_internal_storage.database.dao.UserDao;
import com.example.android_internal_storage.database.entity.User;

public class MainActivity extends AppCompatActivity {
    EditText nimLog, passLog;
    Button btnLog, btnReg;

    // sharedPreferences
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIM = "nim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nimLog = findViewById(R.id.nim_login);
        passLog = findViewById(R.id.password_login);
        btnLog = findViewById(R.id.btn_login);
        btnReg = findViewById(R.id.btn_register);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        // cek sharedPreferences, jika sudah ada
        String name = sharedPreferences.getString(KEY_NAME, null);

        if(name != null) {
            // pindah ke home
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nimUser = nimLog.getText().toString();
                String passUser = passLog.getText().toString();

                if(nimUser.isEmpty() || passUser.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Fill All Fields!", Toast.LENGTH_SHORT).show();
                } else {
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = userDao.login(nimUser, passUser);
                            if(user == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                String name_user = user.getName();
                                String nim_user = user.getNim();

                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString(KEY_NAME, name_user);
                                editor.putString(KEY_NIM, nim_user);
                                editor.apply();

                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    }).start();
                }
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}