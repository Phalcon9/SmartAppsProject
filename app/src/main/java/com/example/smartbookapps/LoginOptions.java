package com.example.smartbookapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginOptions extends AppCompatActivity {

    CardView adminLogin, teacherLogin, studentLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login_options);

        adminLogin = findViewById (R.id.adminLogging);
        teacherLogin = findViewById (R.id.teacherLogging);
        studentLogin = findViewById (R.id.studentLogin);

        adminLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LoginOptions.this, AdminLogin.class);
                startActivity (intent);
            }
        });
        teacherLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LoginOptions.this, TeacherLogin.class);
                startActivity (intent);
            }
        });
        studentLogin.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LoginOptions.this, MainActivity.class);
                startActivity (intent);
            }
        });
    }
}