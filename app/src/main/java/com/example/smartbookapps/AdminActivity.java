package com.example.smartbookapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    TextView usersName;
    LinearLayout logOut;
    CardView studentCard, teacherCard, votingCard,confirmAtten, teachAttendance ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_admin);
        usersName = findViewById (R.id.title);
        logOut = findViewById (R.id.log_out);

        studentCard = findViewById (R.id.studentCard);
        teacherCard =findViewById (R.id.teacherCard);
        votingCard = findViewById (R.id.votingCard);
        confirmAtten = findViewById (R.id.confirmCard);
        teachAttendance = findViewById (R.id.confirmTeacher);
        mAuth = FirebaseAuth.getInstance ();
        fStore = FirebaseFirestore.getInstance ();

        logOut.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                mAuth.signOut ();
                signOutUser();
            }
        });

        teachAttendance.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminActivity.this, AttendTeachActivity.class);
                startActivity (intent);
            }
        });
        studentCard.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminActivity.this, ManageStudent.class);
                startActivity (intent);
            }
        });
        teacherCard.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminActivity.this, ManageTeacher.class);
                startActivity (intent);
            }
        });
        votingCard.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminActivity.this, ManageVoting.class);
                startActivity (intent);
            }
        });
        confirmAtten.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AdminActivity.this, ClassSet.class);
                startActivity (intent);
            }
        });
    }

    private void signOutUser() {
        Intent intent = new Intent (AdminActivity.this, LoginOptions.class);
        startActivity (intent);
        finish ();
    }

    @Override
    protected void onStart() {
        super.onStart ();

        FirebaseUser mFirebaseUser = mAuth.getCurrentUser ();
        if (mFirebaseUser !=null){
            usersName.setText ("Welcome" +mFirebaseUser.getEmail ());


        }

    }

}
