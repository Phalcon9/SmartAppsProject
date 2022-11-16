package com.example.smartbookapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AttendanceActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
        TextView Course_Name, Course_Venue, Course_Time, Departments;
        Button signAtten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_attendance);

        Course_Name =findViewById (R.id.classSet);
        Course_Venue =findViewById (R.id.classVenue);
        Course_Time = findViewById (R.id.Classtime);
        signAtten = findViewById (R.id.signATTT);
        Departments = findViewById (R.id.ClassDepartments);

        firebaseFirestore = FirebaseFirestore.getInstance ();
        firebaseAuth = FirebaseAuth.getInstance ();

        Intent intent =getIntent ();
        String cnm = intent.getStringExtra ("CourseName");
        String ctm = intent.getStringExtra ("Time");
        String cvm = intent.getStringExtra ("Venue");
        String dpt = intent.getStringExtra ("Department");
        String id = intent.getStringExtra ("id");

        Course_Name.setText (cnm);
        Course_Venue.setText (ctm);
        Course_Time.setText (cvm);
        Departments.setText (dpt);

        String uid = Objects.requireNonNull (firebaseAuth.getCurrentUser ()).getUid ();

        signAtten.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String finish = "attended";
                Map<String, Object> userMap = new HashMap<> ();
                userMap.put ("finish", finish);
                userMap.put ("Course Name", cnm);

                firebaseFirestore.collection ("student/"+uid+"/student's attendance"  )
                        .document (id)
                        .set (userMap)
                        .isSuccessful ();

                Map<String, Object> candidateMap = new HashMap<> ();

                candidateMap.put ("timestamp", FieldValue.serverTimestamp ());
                candidateMap.put ("Students Email", firebaseAuth.getCurrentUser ().getEmail ());

                firebaseFirestore.collection ("Classes/"+id+"/Students Attended" )
                        .document (uid)
                        .set (candidateMap)
                        .addOnCompleteListener (new OnCompleteListener<Void> () {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful ()) {
//                                    startActivity (new Intent (AttendanceActivity.this, ResultActivity.class));
                                    Toast.makeText (AttendanceActivity.this, "Attendance Signed", Toast.LENGTH_SHORT).show ();
                                    finish ();
                                }else {
                                    Toast.makeText (AttendanceActivity.this, " successfully", Toast.LENGTH_SHORT).show ();
                                }
                            }
                        });
            }
        });



    }
}