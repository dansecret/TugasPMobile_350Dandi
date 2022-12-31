package com.example.android_internal_storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    TextView nameView, nimView;
    Button btnLogout;

    // sharedPreferences
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "login_pref";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIM = "nim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nameView = findViewById(R.id.name_user);
        nimView = findViewById(R.id.nim_user);
        btnLogout = findViewById(R.id.btn_logout);

        // ambil sharedValue
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String name_user = sharedPreferences.getString(KEY_NAME, null);
        String nim_user = sharedPreferences.getString(KEY_NIM, null);

        // cek sharedValue
        if (name_user != null || nim_user != null) {
            nameView.setText("User Name - " + name_user);
            nimView.setText("Nim User - " + nim_user);
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // hapus key value shared
                editor.clear();
                editor.commit();

                Toast.makeText(getApplicationContext(), "Logout Berhasil!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}