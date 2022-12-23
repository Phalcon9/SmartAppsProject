package com.example.smartbookapps.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.smartbookapps.ClassTeacherActivity;
import com.example.smartbookapps.R;
import com.example.smartbookapps.SetClassTeacher;
import com.example.smartbookapps.logins.LoginOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TeacherPanel extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    TextView usersName, name, matric, email, phone;
    LinearLayout logOut;
    CardView setClass,  classList, studentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_teacher_panel);

        usersName = findViewById (R.id.title);
        logOut = findViewById (R.id.log_outT);
        setClass= findViewById (R.id.classViews);

        name =findViewById (R.id.studeName1);
        matric = findViewById (R.id.studMatric1);
        email = findViewById (R.id.studEmail1);
        phone = findViewById (R.id.studPhone1);


        classList = findViewById (R.id.setClasss);

        mAuth = FirebaseAuth.getInstance ();
        fStore = FirebaseFirestore.getInstance ();

        logOut.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                mAuth.signOut ();
                signOutUser();
            }
        });

        classList.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (TeacherPanel.this, ClassTeacherActivity.class);
                startActivity (intent);
            }
        });

        setClass.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (TeacherPanel.this, SetClassTeacher.class);
                startActivity (intent);
            }
        });



    }
    @Override
    protected void onStart() {
        super.onStart ();
        FirebaseUser user = FirebaseAuth.getInstance ().getCurrentUser ();
        String currentid = user.getUid ();
        DocumentReference reference;
        FirebaseFirestore firestore = FirebaseFirestore.getInstance ();

        reference = firestore.collection ("teacher").document (currentid);

        reference.get ().addOnCompleteListener (new OnCompleteListener<DocumentSnapshot> () {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult ().exists ()){
                    String nameR = task.getResult ().getString ("Name");
                    String matricR = task.getResult ().getString ("employeeNum");
//                    String emailR = task.getResult ().getString ("Emial");
                    String phoneR = task.getResult ().getString ("phoneNum");

                    name.setText ("Name "+ nameR);
                    matric.setText ("Employee ID "+matricR);
                    email.setText ("Email: "+user.getEmail ());
                    phone.setText ("Phone Number"+phoneR);
                }
            }
        });

    }

    private void signOutUser() {
        Intent intent = new Intent (TeacherPanel.this, LoginOptions.class);
        startActivity (intent);
        finish ();
    }
}