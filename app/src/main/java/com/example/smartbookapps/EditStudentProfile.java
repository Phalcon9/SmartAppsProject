package com.example.smartbookapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditStudentProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;
    String studentID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_edit_student_profile);
        final EditText fullname = findViewById (R.id.fullname);
        final EditText category = findViewById (R.id.categorgy);
        final EditText matric = findViewById (R.id.matricNumber);
        final EditText phone = findViewById (R.id.phone);

        Intent data = getIntent ();
        String fullName = data.getStringExtra ("fName");
        String category1 = data.getStringExtra ("category");
        String matricN = data.getStringExtra ("matricNum");
        String phoneNum = data.getStringExtra ("phoneNum");

        fullname.setText (fullName);
        category.setText (category1);
        matric.setText (matricN);
        phone.setText (phoneNum);


    }
}