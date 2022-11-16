package com.example.smartbookapps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SetClassTeacher extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    TextView coursename, coursevenue, coursetime, department;
    Button createClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_set_class_teacher);

        coursename = findViewById (R.id.className);
        coursevenue = findViewById (R.id.venue);
        coursetime = findViewById (R.id.time);
        department = findViewById (R.id.department1);

        createClass = findViewById (R.id.createClass);
        mAuth = FirebaseAuth.getInstance ();
        fStore =FirebaseFirestore.getInstance ();

        createClass.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String name = coursename.getText ().toString ().trim ();
                String venue  = coursevenue.getText ().toString ().trim ();
                String time = coursetime.getText ().toString ().trim ();
                String departmentC = department.getText ().toString ().trim ();

                if (!TextUtils.isEmpty (name) && !TextUtils.isEmpty (venue) &&!TextUtils.isEmpty (time) &&!TextUtils.isEmpty (departmentC) ){
//                    Toast.makeText (CreateCandidate.this, " ", Toast.LENGTH_SHORT).show ();
                }else {
                    Toast.makeText (SetClassTeacher.this, "Enter Details", Toast.LENGTH_SHORT).show ();
                }
                DocumentReference documentReference = fStore.collection ("Classes" ).document ();
                Map<String, Object> candidate = new HashMap<> ();
                candidate.put ("CourseName", name);
                candidate.put ("Venue" , venue);
                candidate.put ("Time", time );
                candidate.put ("Department", departmentC);

                documentReference.set (candidate).addOnSuccessListener (new OnSuccessListener<Void> () {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText (SetClassTeacher.this, "Class "+ name +" Created", Toast.LENGTH_SHORT).show ();

                    }
                });
            }
        });




    }

}