package com.example.smartbookapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManageTeacher extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_teacher);

        CardView addTeacher = findViewById (R.id.addTeacher);
        CardView editTeacher = findViewById (R.id.editTeacher);

        addTeacher.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ManageTeacher.this, TeacherReg.class);
                startActivity (intent);
            }
        });

        editTeacher.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ManageTeacher.this, ViewTeacherDetails.class);
                startActivity (intent);
            }
        });

    }
}