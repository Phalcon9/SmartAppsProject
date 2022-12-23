package com.example.smartbookapps.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartbookapps.R;
import com.example.smartbookapps.ViewCandidates;
import com.example.smartbookapps.ViewClass;
import com.example.smartbookapps.logins.LoginOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentPanel extends AppCompatActivity {

    FirebaseAuth mAuth;

    TextView usersName, name, matric, email, phone;
    LinearLayout logOut;
    CardView viewClasses, vote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_student_panel);

        usersName = findViewById (R.id.title1);
        logOut = findViewById (R.id.log_out);
        viewClasses = findViewById (R.id.viewClass);
        vote = findViewById (R.id.votingCard1);

        name =findViewById (R.id.studeName);
        matric = findViewById (R.id.studMatric);
        email = findViewById (R.id.studEmail);
        phone = findViewById (R.id.studPhone);

//        votingResultss.setOnClickListener (new View.OnClickListener () {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent (StudentPanel.this, ResultActivity.class );
//                startActivity (intent);
//            }
//        });

        viewClasses.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (StudentPanel.this, ViewClass.class);
                startActivity (intent);
            }
        });

        vote.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (StudentPanel.this, ViewCandidates.class);
                startActivity (intent);
            }
        });

        logOut.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                mAuth.signOut ();
                signOutUser();
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

        reference = firestore.collection ("student").document (currentid);

        reference.get ().addOnCompleteListener (new OnCompleteListener<DocumentSnapshot> () {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult ().exists ()){
                    String nameR = task.getResult ().getString ("fName");
                    String matricR = task.getResult ().getString ("matricNum");
//                    String emailR = task.getResult ().getString ("Emial");
                    String phoneR = task.getResult ().getString ("phoneNum");

                    name.setText ("Name "+ nameR);
                    matric.setText ("Matriculation Number "+matricR);
                    email.setText ("Email: "+user.getEmail ());
                    phone.setText ("Phone Number"+phoneR);
                }
            }
        });
//        if (mFirebaseUser !=null){
//            usersName.setText ("Welcome" +mFirebaseUser.getEmail ());
//        }

    }
    private void signOutUser() {
        Intent intent = new Intent (StudentPanel.this, LoginOptions.class);
        startActivity (intent);
        finish ();
    }

}