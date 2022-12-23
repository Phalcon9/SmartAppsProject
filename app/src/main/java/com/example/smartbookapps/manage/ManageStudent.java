package com.example.smartbookapps.manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.smartbookapps.R;
import com.example.smartbookapps.viewdatails.ViewStudentDetails;
import com.example.smartbookapps.register.StudentReg;

public class ManageStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_manage_student);

        CardView addStudent = findViewById (R.id.addStudent);
        CardView editStudent = findViewById (R.id.editStudent);

        addStudent.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ManageStudent.this, StudentReg.class);
                startActivity (intent);
            }
        });
        editStudent.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (ManageStudent.this, ViewStudentDetails.class);
                startActivity (intent);
            }
        });
    }
}